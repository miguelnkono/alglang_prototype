package org.example;

import java.util.Arrays;

public class Checker implements Expr.Visitor<Typable> {
    /**
     * Public entry point for type checking an expression
     *
     * @param __expr__ The expression to type check
     * @return The same expression tree with type annotations added to each node
     * @throws TypeException if type checking fails
     */
    public ExprFinal check(Expr __expr__) {
        try {
            return __build_final_expr__(__expr__);
        } catch (TypeException e) {
            throw e;
        } catch (Exception e) {
            throw new TypeException("Échec de la vérification des types: " + e.getMessage());
        }
    }

    /**
     * Helper method to build ExprFinal tree from Expr tree with type annotations
     */
    private ExprFinal __build_final_expr__(Expr __expr__) {
        return switch (__expr__) {
            case Expr.Binary binary -> __visit_binary__(binary);
            case Expr.Grouping grouping -> __visit_grouping__(grouping);
            case Expr.Unary unary -> __visit_unary__(unary);
            case Expr.Literal literal -> __visit_literal__(literal);
            default -> throw new TypeException("Type d'expression non reconnu: " + __expr__.getClass().getName());
        };
    }

    /**
     * Build a typed Binary expression
     */
    private ExprFinal.Binary __visit_binary__(Expr.Binary __binary__) {
        ExprFinal __left__ = __build_final_expr__(__binary__.__left__);
        ExprFinal __right__ = __build_final_expr__(__binary__.__right__);

        Typable __left_type__ = __left__.getType();
        Typable __right_type__ = __right__.getType();

        Typable __result_type__ = __determine_binary_type__(__binary__.__operator__, __left_type__, __right_type__);

        ExprFinal.Binary __final_binary__ = new ExprFinal.Binary(__binary__.__left__,
                __binary__.__operator__, __binary__.__right__);

        __final_binary__.setType(__result_type__);
        return __final_binary__;
    }

    /**
     * Build a typed Grouping expression
     */
    private ExprFinal.Grouping __visit_grouping__(Expr.Grouping __grouping__) {
        ExprFinal __inner__ = __build_final_expr__(__grouping__.__expression__);
        Typable __inner_type__ = __inner__.getType();

        ExprFinal.Grouping __final_grouping__ = new ExprFinal.Grouping(__grouping__.__expression__);

        __final_grouping__.setType(__inner_type__);
        return __final_grouping__;
    }

    /**
     * Build a typed Unary expression
     */
    private ExprFinal.Unary __visit_unary__(Expr.Unary __unary__) {
        ExprFinal __right__ = __build_final_expr__(__unary__.__right__);
        Typable __right_type__ = __right__.getType();

        Typable __result_type__ = __determine_unary_type__(__unary__.__operator__, __right_type__);

        ExprFinal.Unary __final_unary__ = new ExprFinal.Unary(__unary__.__operator__, __unary__.__right__);

        __final_unary__.setType(__result_type__);
        return __final_unary__;
    }

    /**
     * Build a typed Literal expression
     */
    private ExprFinal.Literal __visit_literal__(Expr.Literal __literal__) {
        if (__literal__.__value__ instanceof AtomicValue<?> __atomic_value__) {
            AtomicTypes __atomic_type__ = __atomic_value__.get__atomic_type__();

            Typable __type__ = TypeFactory.getAtomicType(__atomic_type__);

            ExprFinal.Literal __final_literal__ = new ExprFinal.Literal(__literal__.__value__);

            __final_literal__.setType(__type__);
            return __final_literal__;
        }

        throw new TypeException("Valeur littérale de type non supporté. " +
                "Seules les valeurs atomiques (entier, réel, chaîne, caractère, booléen) sont autorisées.");
    }

    /**
     * Determine the type of binary operation
     */
    private Typable __determine_binary_type__(Token __operator__, Typable __left__, Typable __right__) {
        switch (__operator__.__token_type__()) {
            // Arithmetic operators
            case PLUS, MINUS, STAR, SLASH:
                if (__left__.equals(TypeFactory.INTEGER) && __right__.equals(TypeFactory.INTEGER)) {
                    return TypeFactory.INTEGER;
                } else if (__left__.equals(TypeFactory.FLOATING) && __right__.equals(TypeFactory.FLOATING)) {
                    return TypeFactory.FLOATING;
                } else if (__operator__.__token_type__() == TokenType.PLUS &&
                        __left__.equals(TypeFactory.STRING) && __right__.equals(TypeFactory.STRING)) {
                    return TypeFactory.STRING;
                } else if (__operator__.__token_type__() == TokenType.PLUS &&
                        (__left__.equals(TypeFactory.INTEGER) || __right__.equals(TypeFactory.FLOATING))
                                && __right__.equals(TypeFactory.STRING)) {
                    return TypeFactory.STRING;
                }  else if (__left__.equals(TypeFactory.INTEGER)
                                && __right__.equals(TypeFactory.FLOATING)) {
                    return TypeFactory.FLOATING;
                }   else if (__left__.equals(TypeFactory.FLOATING)
                                && __right__.equals(TypeFactory.INTEGER)) {
                    return TypeFactory.FLOATING;
                } else if (__operator__.__token_type__() == TokenType.PLUS &&
                        (__left__.equals(TypeFactory.STRING)
                                && (__right__.equals(TypeFactory.INTEGER) || __right__.equals(TypeFactory.FLOATING)))) {
                    return TypeFactory.STRING;
                }
                else {
                    String operatorName = __get_operator_name__(__operator__.__token_type__());
                    throw new TypeException(String.format("Opérateur '%s' incompatible avec les types %s et %s. "
                            + "Les opérandes doivent être deux nombres ou deux chaînes de caractères.", operatorName,
                            __left__, __right__), __operator__);
                }

                // Comparison operators
            case GREATER, GREATER_EQUAL, LESS, LESS_EQUAL:
                if ((__left__.equals(TypeFactory.INTEGER) || __left__.equals(TypeFactory.FLOATING))
                        && (__right__.equals(TypeFactory.INTEGER) || __right__.equals(TypeFactory.FLOATING))) {
                    return TypeFactory.BOOLEAN;
                } else {
                    String operatorName = __get_operator_name__(__operator__.__token_type__());
                    throw new TypeException(String.format("Opérateur de comparaison '%s' incompatible avec les types %s et %s. "
                            + "Les opérandes doivent être des nombres.", operatorName, __left__, __right__), __operator__);
                }

                // Equality operators
            case EQUAL_EQUAL, BANG_EQUAL:
                if (__left__.equals(__right__)) {
                    return TypeFactory.BOOLEAN;
                } else {
                    String operatorName = __get_operator_name__(__operator__.__token_type__());
                    throw new TypeException(String.format("Opérateur d'égalité '%s' incompatible avec les types %s et %s. "
                            + "Les opérandes doivent être du même type.", operatorName, __left__, __right__), __operator__);
                }

            default:
                throw new TypeException(String.format("Opérateur binaire non supporté: %s",
                        __operator__.__token_type__()), __operator__);
        }
    }

    /**
     * Determine the type of a unary operation
     */
    private Typable __determine_unary_type__(Token __operator__, Typable __right__) {
        switch (__operator__.__token_type__()) {
            case MINUS:
                if (__right__.equals(TypeFactory.INTEGER) || __right__.equals(TypeFactory.FLOATING)) {
                    return __right__;
                } else {
                    throw new TypeException(String.format("Opérateur unaire '-' incompatible avec le type %s. "
                            + "L'opérande doit être un nombre (entier ou réel).", __right__), __operator__);
                }

            case BANG:
                if (__right__.equals(TypeFactory.BOOLEAN)) {
                    return TypeFactory.BOOLEAN;
                } else {
                    throw new TypeException(String.format("Opérateur logique '!' incompatible avec le type %s. "
                            + "L'opérande doit être un booléen.", __right__), __operator__);
                }

            default:
                throw new TypeException(String.format("Opérateur unaire non supporté: %s",
                        __operator__.__token_type__()), __operator__);
        }
    }

    /**
     * Public entry point for type checking with error recovery
     *
     * @param __expr__            The expression to type check
     * @param __return_on_error__ The expression to return if type checking fails
     * @return The type-annotated expression tree or returnOnError if checking fails
     */
    public ExprFinal check(Expr __expr__, ExprFinal __return_on_error__) {
        try {
            return check(__expr__);
        } catch (Exception e) {
            if (__return_on_error__ != null) {
                return __return_on_error__;
            }
            throw new TypeException("Échec de la vérification des types: " + e.getMessage());
        }
    }

    @Override
    public Typable visitBinaryExpr(Expr.Binary expression) {
        Typable leftType = expression.__left__.accept(this);
        Typable rightType = expression.__right__.accept(this);

        return __determine_binary_type__(expression.__operator__, leftType, rightType);
    }

    @Override
    public Typable visitGroupingExpr(Expr.Grouping expression) {
        return expression.__expression__.accept(this);
    }

    @Override
    public Typable visitUnaryExpr(Expr.Unary expression) {
        Typable rightType = expression.__right__.accept(this);
        return __determine_unary_type__(expression.__operator__, rightType);
    }

    @Override
    public Typable visitLiteralExpr(Expr.Literal expression) {
        if (expression.__value__ instanceof AtomicValue<?> atomicValue) {
            AtomicTypes atomicType = atomicValue.get__atomic_type__();
            return TypeFactory.getAtomicType(atomicType);
        }
        throw new TypeException("Valeur littérale de type non supporté. " +
                "Seules les valeurs atomiques (entier, réel, chaîne, caractère, booléen) sont autorisées.");
    }

    /**
     * Helper method to get French operator names
     */
    private String __get_operator_name__(TokenType tokenType) {
        return switch (tokenType) {
            case PLUS -> "+";
            case MINUS -> "-";
            case STAR -> "*";
            case SLASH -> "/";
            case GREATER -> ">";
            case GREATER_EQUAL -> ">=";
            case LESS -> "<";
            case LESS_EQUAL -> "<=";
            case EQUAL_EQUAL -> "==";
            case BANG_EQUAL -> "<>";
            case BANG -> "!";
            default -> tokenType.toString().toLowerCase();
        };
    }

    /**
     * Validates that an expression has a specific expected type
     */
    public void validateType(Expr expr, Typable expectedType) {
        ExprFinal typedExpr = check(expr);
        Typable actualType = typedExpr.getType();
        if (!actualType.equals(expectedType)) {
            throw new TypeException(String.format("Type attendu: %s, mais obtenu: %s", expectedType, actualType));
        }
    }

    /**
     * Validates that an expression has one of the expected types
     */
    public void validateType(Expr expr, Typable... expectedTypables) {
        ExprFinal typedExpr = check(expr);
        Typable actualType = typedExpr.getType();
        for (Typable expectedTypable : expectedTypables) {
            if (actualType.equals(expectedTypable)) {
                return;
            }
        }
        throw new TypeException(String.format("Type %s non autorisé. Typables attendus: %s",
                actualType, Arrays.toString(expectedTypables)));
    }
}
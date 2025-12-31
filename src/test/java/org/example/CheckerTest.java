package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CheckerTest {
    private final Checker __checker__ = new Checker();

    private static Stream<Arguments> __provide_arithmetic_operators__() {
        return Stream.of(Arguments.of(TokenType.PLUS),
                Arguments.of(TokenType.MINUS), Arguments.of(TokenType.STAR), Arguments.of(TokenType.SLASH));
    }

    private static Stream<Arguments> __provide_comparison_operators__() {
        return Stream.of(Arguments.of(TokenType.GREATER),
                Arguments.of(TokenType.GREATER_EQUAL), Arguments.of(TokenType.LESS), Arguments.of(TokenType.LESS_EQUAL));
    }

    private static Stream<Arguments> __provide_equality_operators__() {
        return Stream.of(Arguments.of(TokenType.EQUAL_EQUAL), Arguments.of(TokenType.BANG_EQUAL));
    }

    @Test
    void __test_binary_integer_arithmetic__() {
        // Test: 10 + 20
        Expr.Literal __ten__ = new Expr.Literal(new AtomicValue<>(10, AtomicTypes.INTEGER));
        Expr.Literal __twenty__ = new Expr.Literal(new AtomicValue<>(20, AtomicTypes.INTEGER));
        Token __plus__ = new Token(TokenType.PLUS, "+", null, 1);

        Expr.Binary __addition__ = new Expr.Binary(__ten__, __plus__, __twenty__);
        ExprFinal __typed_expr__ = __checker__.check(__addition__);

        assertEquals(TypeFactory.INTEGER, __typed_expr__.getType());
        assertEquals(TypeFactory.INTEGER, ((ExprFinal.Binary) __typed_expr__).__left__.accept(__checker__));
        assertEquals(TypeFactory.INTEGER, ((ExprFinal.Binary) __typed_expr__).__right__.accept(__checker__));
    }

    @Test
    void __test_binary_floating_arithmetic__() {
        // Test: 3.14 * 2.0
        Expr.Literal __pi__ = new Expr.Literal(new AtomicValue<>(3.14, AtomicTypes.DOUBLE));
        Expr.Literal __two__ = new Expr.Literal(new AtomicValue<>(2.0, AtomicTypes.DOUBLE));
        Token __star__ = new Token(TokenType.STAR, "*", null, 1);

        Expr.Binary __multiplication__ = new Expr.Binary(__pi__, __star__, __two__);
        ExprFinal __typed_expr__ = __checker__.check(__multiplication__);

        assertEquals(TypeFactory.FLOATING, __typed_expr__.getType());
    }

    @Test
    void __test_binary_string_concatenation__() {
        // Test: "Hello" + "World"
        Expr.Literal __hello__ = new Expr.Literal(new AtomicValue<>("Hello", AtomicTypes.STRING));
        Expr.Literal __world__ = new Expr.Literal(new AtomicValue<>("World", AtomicTypes.STRING));
        Token __plus__ = new Token(TokenType.PLUS, "+", null, 1);

        Expr.Binary __concatenation__ = new Expr.Binary(__hello__, __plus__, __world__);
        ExprFinal __typed_expr__ = __checker__.check(__concatenation__);

        assertEquals(TypeFactory.STRING, __typed_expr__.getType());
    }

    @Test
    void __test_binary_mixed_type_arithmetic_should_fail__() {
        // Test: 10 + "hello"
        Expr.Literal __ten__ = new Expr.Literal(new AtomicValue<>(10, AtomicTypes.INTEGER));
        Expr.Literal __hello__ = new Expr.Literal(new AtomicValue<>("hello", AtomicTypes.STRING));
        Token __plus__ = new Token(TokenType.PLUS, "+", null, 1);

        Expr.Binary __invalid__ = new Expr.Binary(__ten__, __plus__, __hello__);

        assertDoesNotThrow(() -> __checker__.check(__invalid__));
    }

    @Test
    void __test_binary_comparison_operators__() {
        // Test: 10 > 5
        Expr.Literal __ten__ = new Expr.Literal(new AtomicValue<>(10, AtomicTypes.INTEGER));
        Expr.Literal __five__ = new Expr.Literal(new AtomicValue<>(5, AtomicTypes.INTEGER));
        Token __greater__ = new Token(TokenType.GREATER, ">", null, 1);

        Expr.Binary __comparison__ = new Expr.Binary(__ten__, __greater__, __five__);
        ExprFinal __typed_expr__ = __checker__.check(__comparison__);

        assertEquals(TypeFactory.BOOLEAN, __typed_expr__.getType());
    }

    @Test
    void __test_binary_float_comparison__() {
        // Test: 3.14 <= 2.71
        Expr.Literal __pi__ = new Expr.Literal(new AtomicValue<>(3.14, AtomicTypes.DOUBLE));
        Expr.Literal __e__ = new Expr.Literal(new AtomicValue<>(2.71, AtomicTypes.DOUBLE));
        Token __less_equal__ = new Token(TokenType.LESS_EQUAL, "<=", null, 1);

        Expr.Binary __comparison__ = new Expr.Binary(__pi__, __less_equal__, __e__);
        ExprFinal __typed_expr__ = __checker__.check(__comparison__);

        assertEquals(TypeFactory.BOOLEAN, __typed_expr__.getType());
    }

    @Test
    void __test_binary_equality_operators__() {
        // Test: "test" == "test"
        Expr.Literal __str1__ = new Expr.Literal(new AtomicValue<>("test", AtomicTypes.STRING));
        Expr.Literal __str2__ = new Expr.Literal(new AtomicValue<>("test", AtomicTypes.STRING));
        Token __equal_equal__ = new Token(TokenType.EQUAL_EQUAL, "==", null, 1);

        Expr.Binary __equality__ = new Expr.Binary(__str1__, __equal_equal__, __str2__);
        ExprFinal __typed_expr__ = __checker__.check(__equality__);

        assertEquals(TypeFactory.BOOLEAN, __typed_expr__.getType());
    }

    @Test
    void __test_binary_not_equal_operator__() {
        // Test: true != false
        Expr.Literal __true__ = new Expr.Literal(new AtomicValue<>(true, AtomicTypes.BOOLEAN));
        Expr.Literal __false__ = new Expr.Literal(new AtomicValue<>(false, AtomicTypes.BOOLEAN));
        Token __bang_equal__ = new Token(TokenType.BANG_EQUAL, "!=", null, 1);

        Expr.Binary __not_equal__ = new Expr.Binary(__true__, __bang_equal__, __false__);
        ExprFinal __typed_expr__ = __checker__.check(__not_equal__);

        assertEquals(TypeFactory.BOOLEAN, __typed_expr__.getType());
    }

    @Test
    void __test_grouping_expression__() {
        // Test: (10 + 20)
        Expr.Literal __ten__ = new Expr.Literal(new AtomicValue<>(10, AtomicTypes.INTEGER));
        Expr.Literal __twenty__ = new Expr.Literal(new AtomicValue<>(20, AtomicTypes.INTEGER));
        Token __plus__ = new Token(TokenType.PLUS, "+", null, 1);

        Expr.Binary __addition__ = new Expr.Binary(__ten__, __plus__, __twenty__);
        Expr.Grouping __grouping__ = new Expr.Grouping(__addition__);
        ExprFinal __typed_expr__ = __checker__.check(__grouping__);

        assertEquals(TypeFactory.INTEGER, __typed_expr__.getType());
        assertInstanceOf(ExprFinal.Grouping.class, __typed_expr__);
    }

    @Test
    void __test_nested_grouping_expression__() {
        // Test: ((10 + 20) * 2)
        Expr.Literal __ten__ = new Expr.Literal(new AtomicValue<>(10, AtomicTypes.INTEGER));
        Expr.Literal __twenty__ = new Expr.Literal(new AtomicValue<>(20, AtomicTypes.INTEGER));
        Expr.Literal __two__ = new Expr.Literal(new AtomicValue<>(2, AtomicTypes.INTEGER));
        Token __plus__ = new Token(TokenType.PLUS, "+", null, 1);
        Token __star__ = new Token(TokenType.STAR, "*", null, 1);

        Expr.Binary __addition__ = new Expr.Binary(__ten__, __plus__, __twenty__);
        Expr.Grouping __inner_group__ = new Expr.Grouping(__addition__);
        Expr.Binary __multiplication__ = new Expr.Binary(__inner_group__, __star__, __two__);
        Expr.Grouping __outer_group__ = new Expr.Grouping(__multiplication__);

        ExprFinal __typed_expr__ = __checker__.check(__outer_group__);

        assertEquals(TypeFactory.INTEGER, __typed_expr__.getType());
    }

    @Test
    void __test_unary_minus_operator__() {
        // Test: -10
        Expr.Literal __ten__ = new Expr.Literal(new AtomicValue<>(10, AtomicTypes.INTEGER));
        Token __minus__ = new Token(TokenType.MINUS, "-", null, 1);

        Expr.Unary __negation__ = new Expr.Unary(__minus__, __ten__);
        ExprFinal __typed_expr__ = __checker__.check(__negation__);

        assertEquals(TypeFactory.INTEGER, __typed_expr__.getType());
    }

    @Test
    void __test_unary_minus_on_float__() {
        // Test: -3.14
        Expr.Literal __pi__ = new Expr.Literal(new AtomicValue<>(3.14, AtomicTypes.DOUBLE));
        Token __minus__ = new Token(TokenType.MINUS, "-", null, 1);

        Expr.Unary __negation__ = new Expr.Unary(__minus__, __pi__);
        ExprFinal __typed_expr__ = __checker__.check(__negation__);

        assertEquals(TypeFactory.FLOATING, __typed_expr__.getType());
    }

    @Test
    void __test_unary_bang_operator__() {
        // Test: !true
        Expr.Literal __true__ = new Expr.Literal(new AtomicValue<>(true, AtomicTypes.BOOLEAN));
        Token __bang__ = new Token(TokenType.BANG, "!", null, 1);

        Expr.Unary __logical_not__ = new Expr.Unary(__bang__, __true__);
        ExprFinal __typed_expr__ = __checker__.check(__logical_not__);

        assertEquals(TypeFactory.BOOLEAN, __typed_expr__.getType());
    }

    @Test
    void __test_unary_minus_on_non_number_should_fail__() {
        // Test: -"hello" (should fail)
        Expr.Literal __hello__ = new Expr.Literal(new AtomicValue<>("hello", AtomicTypes.STRING));
        Token __minus__ = new Token(TokenType.MINUS, "-", null, 1);

        Expr.Unary __invalid__ = new Expr.Unary(__minus__, __hello__);

        assertThrows(TypeException.class, () -> __checker__.check(__invalid__));
    }

    @Test
    void __test_unary_bang_on_non_boolean_should_fail__() {
        // Test: !10 (should fail)
        Expr.Literal __ten__ = new Expr.Literal(new AtomicValue<>(10, AtomicTypes.INTEGER));
        Token __bang__ = new Token(TokenType.BANG, "!", null, 1);

        Expr.Unary __invalid__ = new Expr.Unary(__bang__, __ten__);

        assertThrows(TypeException.class, () -> __checker__.check(__invalid__));
    }

    @Test
    void __test_literal_integer__() {
        // Test: 42
        Expr.Literal __forty_two__ = new Expr.Literal(new AtomicValue<>(42, AtomicTypes.INTEGER));
        ExprFinal __typed_expr__ = __checker__.check(__forty_two__);

        assertEquals(TypeFactory.INTEGER, __typed_expr__.getType());
        assertInstanceOf(ExprFinal.Literal.class, __typed_expr__);
    }

    @Test
    void __test_literal_float__() {
        // Test: 3.14
        Expr.Literal __pi__ = new Expr.Literal(new AtomicValue<>(3.14, AtomicTypes.DOUBLE));
        ExprFinal __typed_expr__ = __checker__.check(__pi__);

        assertEquals(TypeFactory.FLOATING, __typed_expr__.getType());
    }

    @Test
    void __test_literal_string__() {
        // Test: "hello"
        Expr.Literal __hello__ = new Expr.Literal(new AtomicValue<>("hello", AtomicTypes.STRING));
        ExprFinal __typed_expr__ = __checker__.check(__hello__);

        assertEquals(TypeFactory.STRING, __typed_expr__.getType());
    }

    @Test
    void __test_literal_boolean_true__() {
        // Test: true
        Expr.Literal __true__ = new Expr.Literal(new AtomicValue<>(true, AtomicTypes.BOOLEAN));
        ExprFinal __typed_expr__ = __checker__.check(__true__);

        assertEquals(TypeFactory.BOOLEAN, __typed_expr__.getType());
    }

    @Test
    void __test_literal_boolean_false__() {
        // Test: false
        Expr.Literal __false__ = new Expr.Literal(new AtomicValue<>(false, AtomicTypes.BOOLEAN));
        ExprFinal __typed_expr__ = __checker__.check(__false__);

        assertEquals(TypeFactory.BOOLEAN, __typed_expr__.getType());
    }

    @Test
    void __test_literal_character__() {
        // Test: 'A'
        Expr.Literal __char_a__ = new Expr.Literal(new AtomicValue<>('A', AtomicTypes.CHAR));
        ExprFinal __typed_expr__ = __checker__.check(__char_a__);

        assertEquals(TypeFactory.CHAR, __typed_expr__.getType());
    }

    @Test
    void __test_complex_expression__() {
        // Test: -(10 + 20) * 3.14 < 100
        Expr.Literal __ten__ = new Expr.Literal(new AtomicValue<>(10, AtomicTypes.INTEGER));
        Expr.Literal __twenty__ = new Expr.Literal(new AtomicValue<>(20, AtomicTypes.INTEGER));
        Expr.Literal __pi__ = new Expr.Literal(new AtomicValue<>(3.14, AtomicTypes.DOUBLE));
        Expr.Literal __hundred__ = new Expr.Literal(new AtomicValue<>(100, AtomicTypes.INTEGER));

        Token __plus__ = new Token(TokenType.PLUS, "+", null, 1);
        Token __minus__ = new Token(TokenType.MINUS, "-", null, 1);
        Token __star__ = new Token(TokenType.STAR, "*", null, 1);
        Token __less__ = new Token(TokenType.LESS, "<", null, 1);

        Expr.Binary __addition__ = new Expr.Binary(__ten__, __plus__, __twenty__);
        Expr.Unary __negation__ = new Expr.Unary(__minus__, __addition__);
        Expr.Binary __multiplication__ = new Expr.Binary(__negation__, __star__, __pi__);
        Expr.Binary __comparison__ = new Expr.Binary(__multiplication__, __less__, __hundred__);

        ExprFinal __typed_expr__ = __checker__.check(__comparison__);

        assertEquals(TypeFactory.BOOLEAN, __typed_expr__.getType());
    }

    @Test
    void __test_validate_type_success__() {
        // Test: validateType on integer expression
        Expr.Literal __ten__ = new Expr.Literal(new AtomicValue<>(10, AtomicTypes.INTEGER));

        // Should not throw
        __checker__.validateType(__ten__, TypeFactory.INTEGER);
    }

    @Test
    void __test_validate_type_failure__() {
        // Test: validateType expecting wrong type
        Expr.Literal __ten__ = new Expr.Literal(new AtomicValue<>(10, AtomicTypes.INTEGER));

        assertThrows(TypeException.class, () -> __checker__.validateType(__ten__, TypeFactory.STRING));
    }

    @Test
    void __test_validate_type_multiple_options__() {
        // Test: validateType with multiple allowed types
        Expr.Literal __ten__ = new Expr.Literal(new AtomicValue<>(10, AtomicTypes.INTEGER));

        // Should not throw (integer is one of the allowed types)
        __checker__.validateType(__ten__, TypeFactory.INTEGER, TypeFactory.FLOATING, TypeFactory.BOOLEAN);
    }

    @Test
    void __test_validate_type_multiple_options_failure__() {
        // Test: validateType with wrong type not in allowed list
        Expr.Literal __ten__ = new Expr.Literal(new AtomicValue<>(10, AtomicTypes.INTEGER));

        assertThrows(TypeException.class, () -> __checker__.validateType(__ten__, TypeFactory.STRING, TypeFactory.CHAR, TypeFactory.BOOLEAN));
    }

    @Test
    void __test_error_recovery__() {
        // Test: check with fallback expression
        Expr.Literal __invalid__ = null; // Invalid expression
        ExprFinal __fallback__ = new ExprFinal.Literal(new AtomicValue<>(0, AtomicTypes.INTEGER));
        __fallback__.setType(TypeFactory.INTEGER);

        // Since __invalid__ is null, it should return the fallback
        assertThrows(TypeException.class, () -> __checker__.check(__invalid__/*, __fallback__*/));
//        System.out.println(__checker__.check(__invalid__, __fallback__));
    }

    @Test
    void __test_unknown_operator_should_fail__() {
        // Test: Using an unsupported binary operator
        Expr.Literal __ten__ = new Expr.Literal(new AtomicValue<>(10, AtomicTypes.INTEGER));
        Expr.Literal __twenty__ = new Expr.Literal(new AtomicValue<>(20, AtomicTypes.INTEGER));
        Token __unknown__ = new Token(TokenType.ASSIGN, "<-", null, 1);

        Expr.Binary __invalid__ = new Expr.Binary(__ten__, __unknown__, __twenty__);

        assertThrows(TypeException.class, () -> __checker__.check(__invalid__));
    }

    @Test
    void __test_deeply_nested_expression__() {
        // Test: ((((1 + 2) * 3) - 4) / 5)
        Expr.Literal __one__ = new Expr.Literal(new AtomicValue<>(1, AtomicTypes.INTEGER));
        Expr.Literal __two__ = new Expr.Literal(new AtomicValue<>(2, AtomicTypes.INTEGER));
        Expr.Literal __three__ = new Expr.Literal(new AtomicValue<>(3, AtomicTypes.INTEGER));
        Expr.Literal __four__ = new Expr.Literal(new AtomicValue<>(4, AtomicTypes.INTEGER));
        Expr.Literal __five__ = new Expr.Literal(new AtomicValue<>(5, AtomicTypes.INTEGER));

        Token __plus__ = new Token(TokenType.PLUS, "+", null, 1);
        Token __star__ = new Token(TokenType.STAR, "*", null, 1);
        Token __minus__ = new Token(TokenType.MINUS, "-", null, 1);
        Token __slash__ = new Token(TokenType.SLASH, "/", null, 1);

        Expr.Binary __add__ = new Expr.Binary(__one__, __plus__, __two__);
        Expr.Grouping __group1__ = new Expr.Grouping(__add__);
        Expr.Binary __mult__ = new Expr.Binary(__group1__, __star__, __three__);
        Expr.Grouping __group2__ = new Expr.Grouping(__mult__);
        Expr.Binary __sub__ = new Expr.Binary(__group2__, __minus__, __four__);
        Expr.Grouping __group3__ = new Expr.Grouping(__sub__);
        Expr.Binary __div__ = new Expr.Binary(__group3__, __slash__, __five__);

        ExprFinal __typed_expr__ = __checker__.check(__div__);

        assertEquals(TypeFactory.INTEGER, __typed_expr__.getType());
    }

    @ParameterizedTest
    @MethodSource("__provide_arithmetic_operators__")
    void __test_all_arithmetic_operators__(TokenType operator) {
        // Test each arithmetic operator with integers
        Expr.Literal __five__ = new Expr.Literal(new AtomicValue<>(5, AtomicTypes.INTEGER));
        Expr.Literal __three__ = new Expr.Literal(new AtomicValue<>(3, AtomicTypes.INTEGER));
        Token __token__ = new Token(operator, operator.name(), null, 1);

        Expr.Binary __expr__ = new Expr.Binary(__five__, __token__, __three__);
        ExprFinal __typed_expr__ = __checker__.check(__expr__);

        // All arithmetic operations on integers produce integers
        assertEquals(TypeFactory.INTEGER, __typed_expr__.getType());
    }

    @ParameterizedTest
    @MethodSource("__provide_comparison_operators__")
    void __test_all_comparison_operators__(TokenType operator) {
        // Test each comparison operator
        Expr.Literal __ten__ = new Expr.Literal(new AtomicValue<>(10, AtomicTypes.INTEGER));
        Expr.Literal __five__ = new Expr.Literal(new AtomicValue<>(5, AtomicTypes.INTEGER));
        Token __token__ = new Token(operator, operator.name(), null, 1);

        Expr.Binary __expr__ = new Expr.Binary(__ten__, __token__, __five__);
        ExprFinal __typed_expr__ = __checker__.check(__expr__);

        // All comparison operations produce booleans
        assertEquals(TypeFactory.BOOLEAN, __typed_expr__.getType());
    }

    @ParameterizedTest
    @MethodSource("__provide_equality_operators__")
    void __test_all_equality_operators__(TokenType operator) {
        // Test each equality operator
        Expr.Literal __true__ = new Expr.Literal(new AtomicValue<>(true, AtomicTypes.BOOLEAN));
        Expr.Literal __false__ = new Expr.Literal(new AtomicValue<>(false, AtomicTypes.BOOLEAN));
        Token __token__ = new Token(operator, operator.name(), null, 1);

        Expr.Binary __expr__ = new Expr.Binary(__true__, __token__, __false__);
        ExprFinal __typed_expr__ = __checker__.check(__expr__);

        // All equality operations produce booleans
        assertEquals(TypeFactory.BOOLEAN, __typed_expr__.getType());
    }

    @Test
    void __test_expression_tree_structure_preserved__() {
        // Test that the expression tree structure is preserved after type checking
        // (1 + 2) * 3
        Expr.Literal __one__ = new Expr.Literal(new AtomicValue<>(1, AtomicTypes.INTEGER));
        Expr.Literal __two__ = new Expr.Literal(new AtomicValue<>(2, AtomicTypes.INTEGER));
        Expr.Literal __three__ = new Expr.Literal(new AtomicValue<>(3, AtomicTypes.INTEGER));

        Token __plus__ = new Token(TokenType.PLUS, "+", null, 1);
        Token __star__ = new Token(TokenType.STAR, "*", null, 1);

        Expr.Binary __addition__ = new Expr.Binary(__one__, __plus__, __two__);
        Expr.Binary __multiplication__ = new Expr.Binary(__addition__, __star__, __three__);

        ExprFinal __typed_expr__ = __checker__.check(__multiplication__);

        // Verify the tree structure is preserved
        assertInstanceOf(ExprFinal.Binary.class, __typed_expr__);
        ExprFinal.Binary __typed_mult__ = (ExprFinal.Binary) __typed_expr__;

        assertInstanceOf(ExprFinal.Binary.class, __build_final_expr__(__typed_mult__.__left__));
        assertInstanceOf(ExprFinal.Literal.class, __build_final_expr__(__typed_mult__.__right__));
    }

    // Helper method for the test above
    private ExprFinal __build_final_expr__(Expr __expr__) {
        // Simple helper that mimics part of Checker's logic for testing
        if (__expr__ instanceof Expr.Binary) {
            return new ExprFinal.Binary(((Expr.Binary) __expr__).__left__, ((Expr.Binary) __expr__).__operator__, ((Expr.Binary) __expr__).__right__);
        } else if (__expr__ instanceof Expr.Literal) {
            return new ExprFinal.Literal(((Expr.Literal) __expr__).__value__);
        }
        return null;
    }
}
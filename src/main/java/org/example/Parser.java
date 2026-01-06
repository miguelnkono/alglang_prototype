package org.example;

import java.util.ArrayList;
import java.util.List;

import static org.example.TokenType.*;

public class Parser {
    private static class ParseError extends RuntimeException {}

    private final List<Token> __tokens__;
    private int __current__ = 0;

    public Parser(List<Token> __tokens__) {
        this.__tokens__ = __tokens__;
    }

    // entry point for the parser pipeline.
    public List<Stmt> parse() {
        try {
            return program();
        } catch (ParseError parserException) {
            return new ArrayList<>();
        }
    }

    /**
     * Parses a complete program.
     * program -> algorithm_header block
     */
    private List<Stmt> program()
    {
        algorithmHeader();
        return block();
    }

    /**
     * Parses the algorithm header.
     * algorithm_header -> "Algorithme" ":" IDENTIFIER ";"
     */
    private void algorithmHeader()
    {
        consume(ALGORITHM, "Attend 'Algorithme' au début du programme.");
        consume(COLON, "Attend ':' après 'Algorithme'.");
        consume(IDENTIFIER, "Attend un nom d'algorithme après ':'.");
        consume(SEMICOLON, "Attend ';' après le nom de l'algorithme.");
    }

    /**
     * Parses a block of statements.
     * block -> "Debut" ":" statement* "Fin"
     */
    private List<Stmt> block()
    {
        consume(BEGIN, "Attend 'Debut' pour commencer le bloc.");
        consume(COLON, "Attend ':' après 'Debut'.");

        List<Stmt> statements = new ArrayList<>();
        while (!check(END) && !isAtEnd())
        {
            statements.add(statement());
        }

        consume(END, "Attend 'Fin' pour terminer le bloc.");
        return statements;
    }

    /**
     * Parses a single statement.
     * statement -> expression_stmt
     */
    private Stmt statement()
    {
        Stmt stmt = expressionStmt();
        return stmt;
    }

    /**
     * Parses an expression statement.
     * expression_stmt -> expression ";"
     */
    private Stmt expressionStmt()
    {
        Expr expr = expression();
        consume(SEMICOLON, "Attend ';' après l'expression.");
        return new Stmt.Expression(expr);
    }

    /**
     * This function parse an expression node in the ast.
     *
     * @return An expression node.
     */
    private Expr expression()
    {
        return this.equality();
    }

    /**
     * This function parse an equality node in the ast.
     *
     * @return An expression node.
     */
    private Expr equality()
    {
        Expr expression = this.comparison();

        while (this.match(BANG_EQUAL, EQUAL_EQUAL))
        {
            Token token = this.previous();
            Expr right = this.comparison();
            expression = new Expr.Binary(expression, token, right);
        }

        return expression;
    }

    /**
     * This function parse a comparison node in the ast.
     *
     * @return An expression node.
     */
    private Expr comparison()
    {
        Expr expression = this.term();

        while (this.match(GREATER, GREATER_EQUAL, LESS,  LESS_EQUAL))
        {
            Token token = this.previous();
            Expr right = this.term();
            expression = new Expr.Binary(expression, token, right);
        }

        return expression;
    }

    /**
     * This function parses the term node in the ast.
     *
     * @return An expression node.
     */
    private Expr term()
    {
        Expr expression = this.factor();

        while (this.match(MINUS, PLUS))
        {
            Token token = this.previous();
            Expr right = this.factor();
            expression = new Expr.Binary(expression, token, right);
        }

        return expression;
    }

    /**
     * This function parses a factory node in the ast.
     *
     * @return An expression node.
     */
    private Expr factor()
    {
        Expr expression = this.unary();

        while (this.match(SLASH, STAR))
        {
            Token token = this.previous();
            Expr right = this.unary();
            expression = new Expr.Binary(expression, token, right);
        }

        return expression;
    }

    /**
     * This function parses a unary node in the ast.
     *
     * @return An expression node.
     */
    private Expr unary()
    {
        if (this.match(BANG, MINUS))
        {
            Token token = this.previous();
            Expr right = this.unary();
            return new Expr.Unary(token, right);
        }

        return this.primary();
    }

    private Expr primary()
    {
        if (this.match(FALSE)) return new Expr.Literal(new AtomicValue<Boolean>(false, AtomicTypes.BOOLEAN));
        if (this.match(TRUE)) return new Expr.Literal(new AtomicValue<Boolean>(true, AtomicTypes.BOOLEAN));
        if (this.match(NIL)) return new Expr.Literal(new AtomicValue<Void>(null, AtomicTypes.VOID));

        // for numbers and strings only (no variables yet)
        if (this.match(STRING, NUMBER))
        {
            return new Expr.Literal(this.previous().token_literal());
        }

        if (this.match(LEFT_PAREN))
        {
            Expr expression = this.expression();
            consume(RIGHT_PAREN, "Attend ')' après l'expression.");
            return new Expr.Grouping(expression);
        }

        throw error(this.peek(), "Attends d'une expression.");
    }

    /**
     * This helper function is used to check if the current token matches the provided one.
     *
     * @param types the different type we are going to check on.
     * @return a boolean value that represent if the token match.
     */
    private boolean match(TokenType ...types)
    {
        for (TokenType type : types )
        {
            if (this.check(type))
            {
                // we consume the current token and return true.
                this.advance();
                return true;
            }
        }

        return false;
    }

    /**
     * This is function is going to consume a token, check to see if the token correspond to the
     * one passed as parameter, and it is, it will consume that otherwise it will report an error
     * to the user.
     *
     * @param tokenType the token type we want to consume.
     * @param message the message to print to the user if an error occurred.
     */
    private Token consume(TokenType tokenType, String message)
    {
        if (this.check(tokenType)) return advance();
        throw error(this.peek(), message);
    }

    /**
     * This function throws an exception to synchronize the error recovering.
     *
     * @throws ParseError throws a parseError exception.
     */
    private ParseError error(Token token, String message)
    {
        Main.error(token, message);
        return new ParseError();
    }

    /**
     * This function is used to synchronize the error recovery of our interpreter in the compiler
     * phase of the user's code.
     */
    private void synchronize()
    {
        this.advance();

        while (!this.isAtEnd())
        {
            if (this.previous().token_type() == SEMICOLON) return;

            switch (this.peek().token_type())
            {
                case ALGORITHM:
                case BEGIN:
                case END:
                    return;
            }

            this.advance();
        }
    }

    /**
     * This function will check to see if the provided token matches the current token.
     *
     * @param type represent the token for which we want to check the type with.
     * @return a boolean value representing the result of the checking.
     */
    private boolean check(TokenType type)
    {
        if (this.isAtEnd()) return false;
        return this.peek().token_type() == type;
    }

    /**
     * This function consume the current token and return it.
     *
     * @return the token that just get consumed.
     */
    private Token advance()
    {
        if (!this.isAtEnd()) this.__current__++;
        return this.previous();
    }

    /**
     * This helper function will tell us if we are at the end of the tokens list.
     *
     * @return a boolean value indication if we are at the end of the tokens list.
     */
    private boolean isAtEnd()
    {
        return this.peek().token_type() == EOF;
    }

    /**
     * This function will return the current token being processed.
     *
     * @return the current token in the list of all the tokens.
     */
    private Token peek()
    {
        return this.__tokens__.get(this.__current__);
    }

    /**
     * This function will return the previous token in the list of all the tokens.
     *
     * @return the previous token being consumed by the parser.
     */
    private Token previous()
    {
        return this.__tokens__.get(this.__current__ - 1);
    }
}

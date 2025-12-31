package org.example;

public enum TokenType {
    // Single-character tokens.
    LEFT_PAREN, RIGHT_PAREN,
    LEFT_BRACE, RIGHT_BRACE,
    COMMA, DOT,  SEMICOLON,
    COLON,

    // One or two character tokens.
    BANG, BANG_EQUAL,
    EQUAL, EQUAL_EQUAL,
    GREATER, GREATER_EQUAL,
    LESS, LESS_EQUAL, ASSIGN,
    PLUS_PLUS, MINUS, PLUS, MINUS_MINUS,
    STAR_STAR, SLASH_SLASH, SLASH, STAR,

    // Literals.
    IDENTIFIER, STRING, NUMBER,

    // Keywords.
    AND, CLASS, ELSE, FALSE, FUN, FOR, IF, NIL, OR,
    PRINT, RETURN, SUPER, THIS, TRUE, VAR, WHILE,
    ALGORITHM, STRUCTURE, METHOD, BEGIN, END,

    EOF
}

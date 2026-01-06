package org.example;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScannerTest {

    @Test
    void testSingleTokens() {
        var __input_code_single_character__ = ".,;()[]:";
        var __scanner__ = new Scanner(__input_code_single_character__);
        var __tokens__ = __scanner__.scanTokens();

        assertEquals(9, __tokens__.size(), "There should be 9 tokens scanned.");

        assertEquals(TokenType.DOT, __tokens__.get(0).token_type(), "First token should be DOT.");
        assertEquals(".", __tokens__.get(0).token_lexeme(), "First token lexeme should be '.'.");

        assertEquals(TokenType.COMMA, __tokens__.get(1).token_type(), "Second token should be COMMA.");
        assertEquals(",", __tokens__.get(1).token_lexeme(), "Second token lexeme should be ','.");

        assertEquals(TokenType.SEMICOLON, __tokens__.get(2).token_type(), "Third token should be SEMICOLON.");
        assertEquals(";", __tokens__.get(2).token_lexeme(), "Third token lexeme should be ';'.");

        assertEquals(TokenType.LEFT_PAREN, __tokens__.get(3).token_type(), "Fourth token should be LEFT_PAREN.");
        assertEquals("(", __tokens__.get(3).token_lexeme(), "Fourth token lexeme should be '('.");

        assertEquals(TokenType.RIGHT_PAREN, __tokens__.get(4).token_type(), "Fifth token should be RIGHT_PAREN.");
        assertEquals(")", __tokens__.get(4).token_lexeme(), "Fifth token lexeme should be ')'.");

        assertEquals(TokenType.LEFT_BRACE, __tokens__.get(5).token_type(), "Sixth token should be LEFT_BRACE.");
        assertEquals("[", __tokens__.get(5).token_lexeme(), "Sixth token lexeme should be '['.");

        assertEquals(TokenType.RIGHT_BRACE, __tokens__.get(6).token_type(), "Seventh token should be RIGHT_BRACE.");
        assertEquals("]", __tokens__.get(6).token_lexeme(), "Seventh token lexeme should be ']'.");
    }

    // function to test the double characters tokens.
    @Test
    void testDoubleCharacterTokens() {
        var __input_source__ = "< <- <= > >= = == ! <> + ++ - -- * ** / //";
        var __scanner__ = new Scanner(__input_source__);
        var __tokens__ = __scanner__.scanTokens();

        assertEquals(17, __tokens__.size(), "There should be 17 tokens scanned.");

        assertEquals(TokenType.LESS, __tokens__.get(0).token_type(), "First token should be LESS.");
        assertEquals("<", __tokens__.get(0).token_lexeme(), "First token lexeme should be '<'.");

        assertEquals(TokenType.ASSIGN, __tokens__.get(1).token_type(), "Second token should be ASSIGN.");
        assertEquals("<-", __tokens__.get(1).token_lexeme(), "Second token lexeme should be '<-'.");

        assertEquals(TokenType.LESS_EQUAL, __tokens__.get(2).token_type(), "Third token should be LESS_EQUAL.");
        assertEquals("<=", __tokens__.get(2).token_lexeme(), "Third token lexeme should be '<='.");

        assertEquals(TokenType.GREATER, __tokens__.get(3).token_type(), "Fourth token should be GREATER.");
        assertEquals(">", __tokens__.get(3).token_lexeme(), "Fourth token lexeme should be '>'.");

        assertEquals(TokenType.GREATER_EQUAL, __tokens__.get(4).token_type(), "Fifth token should be GREATER_EQUAL.");
        assertEquals(">=", __tokens__.get(4).token_lexeme(), "Fifth token lexeme should be '>='.");

        assertEquals(TokenType.EQUAL, __tokens__.get(5).token_type(), "Sixth token should be EQUAL.");
        assertEquals("=", __tokens__.get(5).token_lexeme(), "Sixth token lexeme should be '='.");

        assertEquals(TokenType.EQUAL_EQUAL, __tokens__.get(6).token_type(), "Seventh token should be EQUAL_EQUAL.");
        assertEquals("==", __tokens__.get(6).token_lexeme(), "Seventh token lexeme should be '=='.");

        assertEquals(TokenType.BANG, __tokens__.get(7).token_type(), "Seventh token should be BANG.");
        assertEquals("!", __tokens__.get(7).token_lexeme(), "Seventh token lexeme should be '!'.");

        assertEquals(TokenType.BANG_EQUAL, __tokens__.get(8).token_type(), "Eighth token should be BANG_EQUAL.");
        assertEquals("<>", __tokens__.get(8).token_lexeme(), "Eighth token lexeme should be '<>'.");

        assertEquals(TokenType.PLUS, __tokens__.get(9).token_type(), "Ninth token should be PLUS.");
        assertEquals("+", __tokens__.get(9).token_lexeme(), "Ninth token lexeme should be '+'.");

        assertEquals(TokenType.PLUS_PLUS, __tokens__.get(10).token_type(), "Tenth token should be PLUS_PLUS.");
        assertEquals("++", __tokens__.get(10).token_lexeme(), "Tenth token lexeme should be '++'.");

        assertEquals(TokenType.MINUS, __tokens__.get(11).token_type(), "Eleventh token should be MINUS.");
        assertEquals("-", __tokens__.get(11).token_lexeme(), "Eleventh token lexeme should be '-'.");

        assertEquals(TokenType.MINUS_MINUS, __tokens__.get(12).token_type(), "Twelfth token should be MINUS_MINUS.");
        assertEquals("--", __tokens__.get(12).token_lexeme(), "Twelfth token lexeme should be '--'.");

        assertEquals(TokenType.STAR, __tokens__.get(13).token_type(), "Thirteenth token should be STAR.");
        assertEquals("*", __tokens__.get(13).token_lexeme(), "Thirteenth token lexeme should be '*'.");

        assertEquals(TokenType.STAR_STAR, __tokens__.get(14).token_type(), "Fourteenth token should be STAR_STAR.");
        assertEquals("**", __tokens__.get(14).token_lexeme(), "Fourteenth token lexeme should be '**'.");

        assertEquals(TokenType.SLASH, __tokens__.get(15).token_type(), "Fifteenth token should be SLASH.");
        assertEquals("/", __tokens__.get(15).token_lexeme(), "Fifteenth token lexeme should be '/'.");

        assertEquals(TokenType.EOF, __tokens__.get(16).token_type(), "Sixteenth token should be EOF.");
        assertEquals("nil", __tokens__.get(16).token_lexeme(), "Sixteenth token lexeme should be 'nil'");

    }

    // function to test string tokens.
    @Test
    void testStringTokens() {
        var __source_code__ = "\"Bonjour à tous!\"";
        var __scanner__ = new Scanner(__source_code__);
        List<Token> __tokens__ = __scanner__.scanTokens();

        assertEquals(2, __tokens__.size(), "Should contain only 2 tokens.");
        assertEquals(TokenType.STRING, __tokens__.getFirst().token_type());
        assertEquals("\"Bonjour à tous!\"", __tokens__.getFirst().token_lexeme());
    }

    // function to test a numbered tokens.
    @Test
    void testNumberTokens() {
        var __source_code__ = "12.3\n 47";
        Scanner __scanner__ = new Scanner(__source_code__);
        List<Token> __tokens__ = __scanner__.scanTokens();

        assertEquals(3, __tokens__.size(), "Should be equals to 2.");
        assertEquals(TokenType.NUMBER, __tokens__.getFirst().token_type());
        assertEquals(new AtomicValue<Double>(12.3, AtomicTypes.DOUBLE), __tokens__.getFirst().token_literal());

        assertEquals(TokenType.NUMBER, __tokens__.get(1).token_type());
        assertEquals(new AtomicValue<Integer>(47, AtomicTypes.INTEGER), __tokens__.get(1).token_literal());
    }

    @Test
    void testIdentifierTokens() {
        var __source_code__ = "Bonjour\"Bonjour\"";
        Scanner __scanner__ = new Scanner(__source_code__);
        List<Token> __tokens__ = __scanner__.scanTokens();

        assertEquals(3, __tokens__.size(), "Should contains just 3 tokens");

        assertEquals(TokenType.IDENTIFIER, __tokens__.get(0).token_type());
        assertEquals(TokenType.STRING, __tokens__.get(1).token_type());
    }
}
package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScannerTest {

    @Test
    void testSingleTokens() {
        var __input_code_single_character__ = ".,;()[]:";
        var __scanner__ = new Scanner(__input_code_single_character__);
        var __tokens__ = __scanner__.scanTokens();

        assertEquals(9, __tokens__.size(), "There should be 9 tokens scanned.");

        assertEquals(TokenType.DOT, __tokens__.get(0).__token_type__(), "First token should be DOT.");
        assertEquals(".", __tokens__.get(0).__token_lexeme__(), "First token lexeme should be '.'.");

        assertEquals(TokenType.COMMA, __tokens__.get(1).__token_type__(), "Second token should be COMMA.");
        assertEquals(",", __tokens__.get(1).__token_lexeme__(), "Second token lexeme should be ','.");

        assertEquals(TokenType.SEMICOLON, __tokens__.get(2).__token_type__(), "Third token should be SEMICOLON.");
        assertEquals(";", __tokens__.get(2).__token_lexeme__(), "Third token lexeme should be ';'.");

        assertEquals(TokenType.LEFT_PAREN, __tokens__.get(3).__token_type__(), "Fourth token should be LEFT_PAREN.");
        assertEquals("(", __tokens__.get(3).__token_lexeme__(), "Fourth token lexeme should be '('.");

        assertEquals(TokenType.RIGHT_PAREN, __tokens__.get(4).__token_type__(), "Fifth token should be RIGHT_PAREN.");
        assertEquals(")", __tokens__.get(4).__token_lexeme__(), "Fifth token lexeme should be ')'.");

        assertEquals(TokenType.LEFT_BRACE, __tokens__.get(5).__token_type__(), "Sixth token should be LEFT_BRACE.");
        assertEquals("[", __tokens__.get(5).__token_lexeme__(), "Sixth token lexeme should be '['.");

        assertEquals(TokenType.RIGHT_BRACE, __tokens__.get(6).__token_type__(), "Seventh token should be RIGHT_BRACE.");
        assertEquals("]", __tokens__.get(6).__token_lexeme__(), "Seventh token lexeme should be ']'.");
    }

    // function to test the double characters tokens.
    @Test
    void testDoubleCharacterTokens() {
        var __input_source__ = "< <- <= > >= = == ! <> + ++ - -- * ** / //";
        var __scanner__ = new Scanner(__input_source__);
        var __tokens__ = __scanner__.scanTokens();

        assertEquals(17, __tokens__.size(), "There should be 17 tokens scanned.");

        assertEquals(TokenType.LESS, __tokens__.get(0).__token_type__(), "First token should be LESS.");
        assertEquals("<", __tokens__.get(0).__token_lexeme__(), "First token lexeme should be '<'.");

        assertEquals(TokenType.ASSIGN, __tokens__.get(1).__token_type__(), "Second token should be ASSIGN.");
        assertEquals("<-", __tokens__.get(1).__token_lexeme__(), "Second token lexeme should be '<-'.");

        assertEquals(TokenType.LESS_EQUAL, __tokens__.get(2).__token_type__(), "Third token should be LESS_EQUAL.");
        assertEquals("<=", __tokens__.get(2).__token_lexeme__(), "Third token lexeme should be '<='.");

        assertEquals(TokenType.GREATER, __tokens__.get(3).__token_type__(), "Fourth token should be GREATER.");
        assertEquals(">", __tokens__.get(3).__token_lexeme__(), "Fourth token lexeme should be '>'.");

        assertEquals(TokenType.GREATER_EQUAL, __tokens__.get(4).__token_type__(), "Fifth token should be GREATER_EQUAL.");
        assertEquals(">=", __tokens__.get(4).__token_lexeme__(), "Fifth token lexeme should be '>='.");

        assertEquals(TokenType.EQUAL, __tokens__.get(5).__token_type__(), "Sixth token should be EQUAL.");
        assertEquals("=", __tokens__.get(5).__token_lexeme__(), "Sixth token lexeme should be '='.");

        assertEquals(TokenType.EQUAL_EQUAL, __tokens__.get(6).__token_type__(), "Seventh token should be EQUAL_EQUAL.");
        assertEquals("==", __tokens__.get(6).__token_lexeme__(), "Seventh token lexeme should be '=='.");

        assertEquals(TokenType.BANG, __tokens__.get(7).__token_type__(), "Seventh token should be BANG.");
        assertEquals("!", __tokens__.get(7).__token_lexeme__(), "Seventh token lexeme should be '!'.");

        assertEquals(TokenType.BANG_EQUAL, __tokens__.get(8).__token_type__(), "Eighth token should be BANG_EQUAL.");
        assertEquals("<>", __tokens__.get(8).__token_lexeme__(), "Eighth token lexeme should be '<>'.");

        assertEquals(TokenType.PLUS, __tokens__.get(9).__token_type__(), "Ninth token should be PLUS.");
        assertEquals("+", __tokens__.get(9).__token_lexeme__(), "Ninth token lexeme should be '+'.");

        assertEquals(TokenType.PLUS_PLUS, __tokens__.get(10).__token_type__(), "Tenth token should be PLUS_PLUS.");
        assertEquals("++", __tokens__.get(10).__token_lexeme__(), "Tenth token lexeme should be '++'.");

        assertEquals(TokenType.MINUS, __tokens__.get(11).__token_type__(), "Eleventh token should be MINUS.");
        assertEquals("-", __tokens__.get(11).__token_lexeme__(), "Eleventh token lexeme should be '-'.");

        assertEquals(TokenType.MINUS_MINUS, __tokens__.get(12).__token_type__(), "Twelfth token should be MINUS_MINUS.");
        assertEquals("--", __tokens__.get(12).__token_lexeme__(), "Twelfth token lexeme should be '--'.");

        assertEquals(TokenType.STAR, __tokens__.get(13).__token_type__(), "Thirteenth token should be STAR.");
        assertEquals("*", __tokens__.get(13).__token_lexeme__(), "Thirteenth token lexeme should be '*'.");

        assertEquals(TokenType.STAR_STAR, __tokens__.get(14).__token_type__(), "Fourteenth token should be STAR_STAR.");
        assertEquals("**", __tokens__.get(14).__token_lexeme__(), "Fourteenth token lexeme should be '**'.");

        assertEquals(TokenType.SLASH, __tokens__.get(15).__token_type__(), "Fifteenth token should be SLASH.");
        assertEquals("/", __tokens__.get(15).__token_lexeme__(), "Fifteenth token lexeme should be '/'.");

        assertEquals(TokenType.EOF, __tokens__.get(16).__token_type__(), "Sixteenth token should be EOF.");
        assertEquals("nil", __tokens__.get(16).__token_lexeme__(), "Sixteenth token lexeme should be 'nil'");

    }
}
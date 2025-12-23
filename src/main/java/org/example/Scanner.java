package org.example;

import java.util.ArrayList;
import java.util.List;

import static org.example.TokenType.*;

public class Scanner {
    private final String __source_code__;
    private List<Token> __tokens__;
    private int __current_position__ = 0;
    private int __start_position__ = 0;
    private int __line_number__ = 1;

    public Scanner(String __source_code__) {
        this.__source_code__ = __source_code__;

        this.__tokens__ = new ArrayList<>();
    }

    /**
     * Function that scans the tokens from the source code.
     *
     * @return returns the list of tokens found in the source code
     * */
    public List<Token> scanTokens() {
        // while we are not at the end of the source code we scan.
        while (!__is_at_end_of_source_code__()) {
            __start_position__ = __current_position__;

            // scan the next token
            scanToken();
        }

        __tokens__.add(new Token(EOF, "nil", null, __line_number__));
        return this.__tokens__;
    }

    /**
     * Function to actually scan the next token from the source code.
     *
     * <h1> Single character tokens. </h1>
     * <p>
     * Regular expressions: (, | . | - | \ | + | ; | /)*
     * ,   => COMMA
     * .   => DOT
     * -   => MINUS
     * (   => LEFT_PAREN
     * )   => RIGHT_PAREN
     * [   => LEFT_BRACE
     * ]   => RIGHT_BRACE
     * </p>
     *
     * <h1> Double characters tokens. </h1>
     * <p>
     *     Regular expressions: (< | <> | <= | >= | ++ | -- | ** | <- | / | // )*
     * </p>
     * */
    private void scanToken() {
        char __current_char__ = __advance_character__();

        switch (__current_char__) {
            case ',' -> addToken(TokenType.COMMA);
            case '.' -> addToken(TokenType.DOT);
            case ';' -> addToken(TokenType.SEMICOLON);
            case '(' -> addToken(LEFT_PAREN);
            case ')' -> addToken(RIGHT_PAREN);
            case '[' -> addToken(LEFT_BRACE);
            case ']' -> addToken(RIGHT_BRACE);
            case ':' -> addToken(COLON);

            // meaningless characters.
            case ' ', '\r', '\t' -> {
                // ignore whitespace
            }
            case '\n' -> __line_number__++;

            case '<' -> {
                if (__match_character__('=')) {
                    addToken(LESS_EQUAL);
                } else if (__match_character__('-')) {
                    addToken(ASSIGN);
                } else if (__match_character__('>')) {
                    addToken(BANG_EQUAL);   // not equal operator
                } else {
                    addToken(LESS);
                }
            }
//            case '>' -> {
//                if (__match_character__('=')) {
//                    addToken(GREATER_EQUAL);
//                } else {
//                    addToken(GREATER);
//                }
//            }
            case '>' -> addToken(__match_character__('=') ? GREATER_EQUAL : GREATER);
            case '=' -> addToken(__match_character__('=') ? EQUAL_EQUAL : EQUAL);
            case '!' -> addToken(BANG);
            case '+' -> {
                if (__match_character__('+')) {
                    addToken(PLUS_PLUS);
                } else {
                    addToken(PLUS);
                }
            }
            case '-' -> {
                if (__match_character__('-')) {
                    addToken(MINUS_MINUS);
                } else {
                    addToken(MINUS);
                }
            }
            case '*' -> {
                if (__match_character__('*')) {
                    addToken(STAR_STAR);
                } else {
                    addToken(STAR);
                }
            }
            case '/' -> {
                if (__match_character__('/')) {
                    // this is the case when it is a comment.
                    while (__peek__() != '\n' && !__is_at_end_of_source_code__()) __advance_character__();
                } else {
                    // in this case it is a division operator.
                    addToken(SLASH);
                }
            }
            default -> {
                System.out.println(__current_char__);
                Main.error(__line_number__, "Charactère non prise en compte");
            }
        }
    }

    /**
     * Helper function to check if the expected token is equal to the current token.
     * It advances the cursor only if the condition is mate otherwise is just return.
     *
     * @return true or false on equality of the expected token and the actual token.
     * */
    private boolean __match_character__(char expected) {
        if (__peek__() == '\0' || __peek__() != expected || __is_at_end_of_source_code__()) return false;

        __current_position__++;
        return true;
    }

    /**
     * Helper function to peek the current character without advancing the current position.
     *
     * @return returns the character at the current position without advancing
     * */
    private char __peek__() {
        if (__is_at_end_of_source_code__()) return '\0';
        return __source_code__.charAt(__current_position__);
    }

    /**
     * Helper function to add a token to the list of tokens.
     *
     * @param __token_type__ the type of the token to add
     * */
    private void addToken(TokenType __token_type__) {
        String __text__ = __source_code__.substring(__start_position__, __current_position__);
        __tokens__.add(new Token(__token_type__, __text__, null, __line_number__));
    }

    /**
     * Helper function to check if we are at the end of the source code.
     *
     * @return returns true if we are at the end of the source code, false otherwise
     * */
    private boolean __is_at_end_of_source_code__() {
        return __current_position__ >= __source_code__.length();
    }

    /**
     * Helper function to read the next character and advance the current position.
     *
     * @return returns the character at the current position and advances the position by one
     * */
    private char __advance_character__() {
        return __source_code__.charAt(__current_position__++);
    }

}

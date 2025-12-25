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
     *
     * <h1> String tokens. </h1>
     * <p>
     *     A string token starts with a double quote and ends with a double quote.
     *     The string can be multi-lined.
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
                if (__current_char__ == '"') {
                    // scan the string token.
                    // "bonjour"
                    while (__peek__() != '"' && !__is_at_end_of_source_code__()) {
                        if (__peek__() == '\n') __line_number__++;

                        __advance_character__();
                    }

                    if (__is_at_end_of_source_code__()) {
                        // throw an error, the string is not closed.
                        Main.error(__line_number__, "Chaîne de caractère non terminé");
                    }

                    __advance_character__();
                    var __text__ = __source_code__.substring(__start_position__, __current_position__);
                    addToken(STRING, new AtomicValue<String>(__text__, AtomicTypes.STRING));
                } else if (__is_numeric__(__current_char__)) {
                    // scan numbers( integers and doubles )
                    // 12 23.3
                    while (__is_numeric__(__peek__()) && !__is_at_end_of_source_code__()) {
                        __advance_character__();
                    }

                    if (__match_character__('.')) {
                        if (__is_numeric__(__peek__())) {
                            while (__is_numeric__(__peek__()) && !__is_at_end_of_source_code__()) {
                                __advance_character__();
                            }
                        } else {
                            Main.error(__line_number__, "Nombre mal formé");
                        }
                    }

                    var __number__ = __source_code__.substring(__start_position__, __current_position__);
                    if (__number__.contains(".")) {
                        addToken(NUMBER, new AtomicValue<Double>(Double.parseDouble(__number__), AtomicTypes.DOUBLE));
                    } else {
                        addToken(NUMBER, new AtomicValue<Integer>(Integer.parseInt(__number__), AtomicTypes.INTEGER));
                    }
                } else if (__is_alpha__(__current_char__)) {
                    // scan identifiers.
                    // var x
                    while (__is_alpha_numeric__(__peek__()) && !__is_at_end_of_source_code__()) {
                        __advance_character__();
                    }

                    // todo: continue later.
                } else {
                    System.out.println(__current_char__);
                    Main.error(__line_number__, "Charactère non prise en compte");
                }
            }
        }
    }

    /**
     * Helper function to see if a particular character is an alphabetical one.
     * To be an alphabetical character the actual character must be in between <strong>a - z</strong> or <strong>A - Z</strong>
     * or <strong> equal to : _</strong>
     *
     * @param __char__ the actual character to check against
     * @return will return true it is the case otherwise it will return false.
     * */
    private boolean __is_alpha__(char __char__) {
        return __char__ >= 'a' && __char__ <= 'z' ||
                __char__ >= 'A' && __char__ <= 'Z' ||
                __char__ == '_';
    }

    /**
     * Helper function to check to see if a particular character is an alphanumeric character.
     *
     * @return will return true if it is in any other situation it is going to return false.
     * */
    private boolean __is_alpha_numeric__(char __char__) {
        return __is_numeric__(__char__) || __is_alpha__(__char__);
    }

    /**
     * Helper function to return the character after the current one.
     *
     * @return will return the next character after the next token.
     * */
    private char __peek_next__() {
        if (__is_at_end_of_source_code__()) return '\0';

        return __source_code__.charAt(__current_position__ + 1);
    }

    /**
     * Helper function to check if a given character is a number or not.
     *
     * @param __char__ the character to check against.
     * @return will return true is it match otherwise false.
     * */
    private boolean __is_numeric__(char __char__) {
        return __char__ >= '0' && __char__ <= '9';
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
        addToken(__token_type__, null);
    }

    /**
     * Overloaded version of the <strong>addToken</strong> function.
     *
     * @param __token_type__  the type of the token to add.
     * @param __value__ the value to add to the value of the token.
     * */
    private void addToken(TokenType __token_type__, Valuable __value__) {
        String __lexeme__ = __source_code__.substring(__start_position__, __current_position__);
        __tokens__.add(new Token(__token_type__, __lexeme__, __value__, __line_number__));
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

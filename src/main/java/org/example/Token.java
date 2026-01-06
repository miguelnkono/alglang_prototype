package org.example;


/**
 * The type Token.
 *
 * @param token_type the type of the token
 * @param token_lexeme the lexeme of the token
 * @param token_literal the runtime representation of the token if there is any
 * @param token_line the line where the token was found
 */
public record Token(TokenType token_type, String token_lexeme, Valuable token_literal, int token_line) {

    @Override
    public String toString() {
        return String.format("%s %s %s", this.token_type, this.token_lexeme, this.token_literal);
    }
}

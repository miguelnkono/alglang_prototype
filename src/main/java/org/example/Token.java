package org.example;


/**
 * The type Token.
 *
 * @param __token_type__ the type of the token
 * @param __token_lexeme__ the lexeme of the token
 * @param __token_literal__ the runtime representation of the token if there is any
 * @param __token_line__ the line where the token was found
 */
public record Token(TokenType __token_type__, String __token_lexeme__, Valuable __token_literal__, int __token_line__) {

    @Override
    public String toString() {
        return String.format("%s %s %s", this.__token_type__, this.__token_lexeme__, this.__token_literal__);
    }
}

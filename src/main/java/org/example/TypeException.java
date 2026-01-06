package org.example;

public class TypeException extends RuntimeException {

    public TypeException(String __message__) { super(__message__); }

    public TypeException(String __message__, Token __token__) {
        super(__message__ + " à la ligne " + __token__.token_line());
    }
}

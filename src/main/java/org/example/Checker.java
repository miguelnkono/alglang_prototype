package org.example;

public class Checker implements ExprFinal.Visitor<Typable> {
    @Override
    public Typable visitBinaryExprFinal(ExprFinal.Binary __exprfinal__) {
        return null;
    }

    @Override
    public Typable visitGroupingExprFinal(ExprFinal.Grouping __exprfinal__) {
        return null;
    }

    @Override
    public Typable visitUnaryExprFinal(ExprFinal.Unary __exprfinal__) {
        return null;
    }

    @Override
    public Typable visitLiteralExprFinal(ExprFinal.Literal __exprfinal__) {
        return null;
    }
}

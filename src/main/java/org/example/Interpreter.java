package org.example;

public class Interpreter implements Expr.Visitor<Valuable>, Stmt.Visitor<Void> {
    @Override
    public Valuable visitBinaryExpr(Expr.Binary __expr__) {
        return null;
    }

    @Override
    public Valuable visitGroupingExpr(Expr.Grouping __expr__) {
        return null;
    }

    @Override
    public Valuable visitUnaryExpr(Expr.Unary __expr__) {
        return null;
    }

    @Override
    public Valuable visitLiteralExpr(Expr.Literal __expr__) {
        return null;
    }

    @Override
    public Void visitExpressionStmt(Stmt.Expression __stmt__) {
        return null;
    }
}

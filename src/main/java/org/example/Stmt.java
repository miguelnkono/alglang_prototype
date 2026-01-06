package org.example;

public abstract class Stmt
{
	public interface Visitor<R> {
		 R visitExpressionStmt (Expression __stmt__);
	}

    public static class Expression extends Stmt 
    {
        public Expression (Expr __expression__)
        {
            this.__expression__ = __expression__;
        }

		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visitExpressionStmt(this);
		}

		public final Expr __expression__;
    }

	public abstract <R> R accept(Visitor<R> visitor);
}

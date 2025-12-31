package org.example;

public abstract class Expr
{
	public interface Visitor<R> {
		 R visitBinaryExpr (Binary expr);
		 R visitGroupingExpr (Grouping expr);
		 R visitUnaryExpr (Unary expr);
		 R visitLiteralExpr (Literal expr);
	}

    public static class Binary extends Expr 
    {
        public Binary (Expr __left__, Token __operator__, Expr __right__)
        {
            this.__left__ = __left__;
            this.__operator__ = __operator__;
            this.__right__ = __right__;
        }

		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visitBinaryExpr(this);
		}

		public final Expr __left__;
		public final Token __operator__;
		public final Expr __right__;
    }

    public static class Grouping extends Expr 
    {
        public Grouping (Expr __expression__)
        {
            this.__expression__ = __expression__;
        }

		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visitGroupingExpr(this);
		}

		public final Expr __expression__;
    }

    public static class Unary extends Expr 
    {
        public Unary (Token __operator__, Expr __right__)
        {
            this.__operator__ = __operator__;
            this.__right__ = __right__;
        }

		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visitUnaryExpr(this);
		}

		public final Token __operator__;
		public final Expr __right__;
    }

    public static class Literal extends Expr 
    {
        public Literal (Valuable __value__)
        {
            this.__value__ = __value__;
        }

		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visitLiteralExpr(this);
		}

		public final Valuable __value__;
    }

	public abstract <R> R accept(Visitor<R> visitor);
}

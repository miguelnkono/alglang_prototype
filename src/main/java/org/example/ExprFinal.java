package org.example;

public abstract class ExprFinal
{
	public interface Visitor<R> {
		 R visitBinaryExprFinal (Binary __exprfinal__);
		 R visitGroupingExprFinal (Grouping __exprfinal__);
		 R visitUnaryExprFinal (Unary __exprfinal__);
		 R visitLiteralExprFinal (Literal __exprfinal__);
	}

    public static class Binary extends ExprFinal 
    {
        public Binary (Expr __left__, Token __operator__, Expr __right__)
        {
            this.__left__ = __left__;
            this.__operator__ = __operator__;
            this.__right__ = __right__;
        }

		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visitBinaryExprFinal(this);
		}

		public final Expr __left__;
		public final Token __operator__;
		public final Expr __right__;
    }

    public static class Grouping extends ExprFinal 
    {
        public Grouping (Expr __expression__)
        {
            this.__expression__ = __expression__;
        }

		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visitGroupingExprFinal(this);
		}

		public final Expr __expression__;
    }

    public static class Unary extends ExprFinal 
    {
        public Unary (Token __operator__, Expr __right__)
        {
            this.__operator__ = __operator__;
            this.__right__ = __right__;
        }

		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visitUnaryExprFinal(this);
		}

		public final Token __operator__;
		public final Expr __right__;
    }

    public static class Literal extends ExprFinal 
    {
        public Literal (Valuable __value__)
        {
            this.__value__ = __value__;
        }

		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visitLiteralExprFinal(this);
		}

		public final Valuable __value__;
    }

	private Typable __type__;

	public Typable getType() { return __type__; }
	public void setType(Typable __type__) { this.__type__ = __type__; }

	public abstract <R> R accept(Visitor<R> visitor);

	@Override
	public String toString() { return __type__.toString(); }
}

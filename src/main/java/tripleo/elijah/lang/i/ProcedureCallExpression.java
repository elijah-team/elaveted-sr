package tripleo.elijah.lang.i;

public interface ProcedureCallExpression extends IExpression {
	ExpressionList getArgs();

	void setArgs(ExpressionList aExpl);

	void identifier(IExpression ee);

	String printableString();

	@Override
	String toString();
}

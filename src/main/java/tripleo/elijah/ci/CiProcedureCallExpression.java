package tripleo.elijah.ci;

// FIXME wrap CiExpression and ExpressionList and ExpressionKind too
public interface CiProcedureCallExpression extends CiExpression {
	void identifier(CiExpression ee);

	default CiExpressionList getArgs() {
		return getExpressionList();
	}

	CiExpressionList getExpressionList();

	//CiExpressionList exprList();

	void setExpressionList(CiExpressionList ael);

	@Override
	String printableString();

	@Override
	String toString();
}

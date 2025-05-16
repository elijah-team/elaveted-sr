package tripleo.elijah.ci;

public interface CiCharLitExpression extends CiExpression {
	@Override
	CiExpressionKind getKind();

	@Override
	void setKind(CiExpressionKind aIncrement);

	@Override
	CiExpression getLeft();

	@Override
	void setLeft(CiExpression iexpression);

	@Override
	boolean is_simple();

	@Override
	String repr_();

	@Override
	String toString();
}

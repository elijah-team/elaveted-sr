package tripleo.elijah.ci;

public interface CiFloatExpression extends CiExpression {
	@Override
	CiExpressionKind getKind();

	@Override
	void setKind(CiExpressionKind aType);

	@Override
	CiExpression getLeft();

	@Override
	void setLeft(CiExpression aLeft);

	@Override
	boolean is_simple();

	@Override
	String repr_();

	@Override
	String toString();
}

package tripleo.elijah.ci;

public interface CiBasicBinaryExpression extends CiBinaryExpression {
	@Override
	void setKind(CiExpressionKind aKind);

	@Override
	void setLeft(CiExpression aLeft);

	@Override
	String toString();

	void shift(CiExpressionKind aType);
}

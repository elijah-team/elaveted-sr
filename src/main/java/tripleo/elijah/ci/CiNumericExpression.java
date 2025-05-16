package tripleo.elijah.ci;

import tripleo.elijah_fluffy.diagnostic.TextLocatable;

public interface CiNumericExpression extends CiExpression, TextLocatable {
	@Override
	CiExpressionKind getKind();

	@Override
		// CiExpression
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

	@Override
	int getLine();

	int getValue();
}

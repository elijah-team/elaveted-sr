package tripleo.elijah.ci;

public interface CiSubExpression extends CiExpression {
	CiExpression getExpression();

	@Override
	CiExpressionKind getKind();

	@Override
	boolean is_simple();
}

package tripleo.elijah.ci;

import antlr.Token;

public interface CiGetItemExpression extends CiExpression {
	CiExpression index();

	void parens(Token lb, Token rb);

	@Override
	CiExpressionKind getKind();

	@Override
	boolean is_simple();
}

package tripleo.elijah.ci;

//import org.jetbrains.annotations.Contract;

import tripleo.elijah_fluffy.diagnostic.TextLocatable;

public interface CiIdentExpression extends CiExpression, /*Resolvable,*/ TextLocatable {
//	@Contract("_ -> new")
//	static @NotNull CiIdentExpression forString(String string) {
//		return new IdentExpressionImpl(Helpers.makeToken(string));
//	}

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

	//void setContext(Context context);

	@Override
	String repr_();

	String getText();
}

package tripleo.elijah.ci;

public interface CiStringExpression extends CiExpression {
	@Override
	CiExpressionKind getKind();

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

	String getText();

	void set(String g);
}

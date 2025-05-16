package tripleo.elijah.ci;

public interface CiBinaryExpression extends CiExpression {
	void set(CiBinaryExpression ex);

	CiExpression getRight();

	void setRight(CiExpression right);
}

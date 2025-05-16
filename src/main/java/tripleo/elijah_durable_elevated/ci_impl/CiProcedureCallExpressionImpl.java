package tripleo.elijah_durable_elevated.ci_impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.CiExpressionKind;
import tripleo.elijah.ci.CiExpressionList;
import tripleo.elijah.ci.CiProcedureCallExpression;
import tripleo.elijah_fluffy.util.UnintendedUseException;

public class CiProcedureCallExpressionImpl implements CiProcedureCallExpression {
	private CiExpression     _left;
	private CiExpressionList expressionList = new CiExpressionListImpl();

	///**
	// * Get the argument list
	// *
	// * @return the argument list
	// */
	//@Override
	//public CiExpressionList exprList() {
	//	return expressionList;
	//}

	@Override
	public @NotNull CiExpressionKind getKind() {
		return CiExpressionKind.PROCEDURE_CALL;
	}

	@Override
	public void setKind(final CiExpressionKind aCiExpressionKind) {
		throw new UnintendedUseException();
	}

	@Override
	public CiExpression getLeft() {
		return _left;
	}

	/**
	 * @see #identifier()
	 */
	@Override
	public void setLeft(final CiExpression iexpression) {
		_left = iexpression;
	}

	@Override
	public boolean is_simple() {
		throw new UnintendedUseException();
	}

	@Override
	public String repr_() {
		return "ProcedureCallExpression{%s %s}".formatted(getLeft(), expressionListPrintableString());
	}

	/**
	 * Set the left hand side of the procedure call expression, ie the method name
	 *
	 * @param xyz a method name might come as DotExpression or IdentExpression
	 */
	@Override
	public void identifier(final CiExpression xyz) {
		setLeft(xyz);
	}

	@Override
	public CiExpressionList getExpressionList() {
		return expressionList;
	}

	/**
	 * change then argument list all at once
	 *
	 * @param ael the new value
	 */
	@Override
	public void setExpressionList(final CiExpressionList ael) {
		expressionList = ael;
	}

	@Override
	public String printableString() {
		return String.format("%s%s", getLeft(), expressionListPrintableString());
	}

	private String expressionListPrintableString() {
		return expressionList != null ? expressionList.toString() : "()";
	}

	@Override
	public String toString() {
		return repr_();
	}

	//@Override
	//public void setArgs(CiExpressionList aEl) {
	//	throw new UnintendedUseException();
	//}
}

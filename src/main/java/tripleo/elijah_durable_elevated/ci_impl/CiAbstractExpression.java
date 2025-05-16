package tripleo.elijah_durable_elevated.ci_impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.CiExpressionKind;
import tripleo.elijah.ci.CiExpressionList;

public abstract class CiAbstractExpression implements CiExpression {
	private CiExpressionKind _kind;
	private CiExpression     left;
	private CiExpressionList args;

	public CiAbstractExpression() {
		left  = null;
		_kind = null;
	}

	public CiAbstractExpression(final CiExpression aLeft, final CiExpressionKind aType) {
		left  = aLeft;
		_kind = aType;
	}

	public @NotNull CiExpressionList getArgs() {
		return args;
	}

	public void setArgs(CiExpressionList ael) {
		args = ael;
	}

	@Override
	public CiExpressionKind getKind() {
		return _kind;
	}

	@Override
	public void setKind(final CiExpressionKind type1) {
		_kind = type1;
	}

	@Override
	public CiExpression getLeft() {
		return left;
	}

	@Override
	public void setLeft(final CiExpression aLeft) {
		left = aLeft;
	}

	@Override
	public String repr_() {
		return String.format("<Expression %s %s>", left, _kind);
	}

	public CiExpressionKind get_kind() {
		return _kind;
	}

	public void set_kind(CiExpressionKind a_kind) {
		_kind = a_kind;
	}
}

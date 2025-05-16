package tripleo.elijah_durable_elevated.ci_impl;

import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.CiExpressionKind;
import tripleo.elijah.ci.CiGetItemExpression;
import tripleo.elijah.ci.CiSetItemExpression;

/**
 * Created 8/6/20 1:15 PM
 */
public class CiSetItemExpressionImpl extends CiBasicBinaryExpressionImpl implements CiSetItemExpression {
	public CiSetItemExpressionImpl(final CiGetItemExpression left_, final CiExpression right_) {
		this.setLeft(left_);
		this.setRight(right_);
		this.setKind(CiExpressionKind.SET_ITEM);
	}
}

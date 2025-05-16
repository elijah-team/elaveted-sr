/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_elevated.ci_impl;

import antlr.Token;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.CiExpressionKind;
import tripleo.elijah.ci.CiGetItemExpression;
import tripleo.elijah_fluffy.util.UnintendedUseException;

/**
 *
 * <p>
 * Created Apr 16, 2020 at 7:58:36 AM
 */
public class CiGetItemExpressionImpl extends CiAbstractExpression implements CiGetItemExpression {
	private CiExpression index; // TODO what about multidimensional arrays?

	public CiGetItemExpressionImpl(final CiExpression ee, final CiExpression expr) {
		this.index = expr;
		this.setLeft(ee);
		//this.set_kind(CiExpressionKind.GET_ITEM);
	}

	@Override
	public void set_kind(final CiExpressionKind a_kind) {
		throw new UnintendedUseException("i'm controlling!!");
	}

	@Override
	public @NotNull CiExpressionKind getKind() {
		return CiExpressionKind.GET_ITEM;
	}

	@Override
	public boolean is_simple() {
		return false; // TODO is this correct? Let's err on the side of caution
	}

	@Override
	public CiExpression index() {
		return index;
	}

	@Override
	public void parens(final Token lb, final Token rb) {
		// TODO implement me later
		throw new UnintendedUseException("implement me later");
	}

	public void setIndex(CiExpression aIndex) {
		index = aIndex;
	}
}

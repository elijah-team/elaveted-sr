/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
/*
 * Created on May 19, 2019 23:47
 *
 * $Id$
 *
 */
package tripleo.elijah_durable_elevated.ci_impl;

import antlr.Token;
import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.CiExpressionKind;
import tripleo.elijah.ci.CiExpressionList;
import tripleo.elijah.ci.CiFloatExpression;
import tripleo.elijah_fluffy.util.UnintendedUseException;
import tripleo.elijah_fluffy.util.NotImplementedException;
import tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon;

public class CiFloatExpressionImpl implements CiFloatExpression {
	private final Token n;
	float carrier;

	public CiFloatExpressionImpl(final Token n) {
		this.n  = n;
		carrier = Float.parseFloat(n.getText());
	}

	//public @NotNull List<FormalArgListItem> getArgs() {
	//	return null;
	//}

	public void setArgs(final CiExpressionList ael) {
		throw new UnintendedUseException();
	}

	@Override
	public CiExpressionKind getKind() {
		return CiExpressionKind.FLOAT; // TODO
	}

	@Override
	public void setKind(final CiExpressionKind aType) {
		// log and ignore
		SimplePrintLoggerToRemoveSoon.println_err_2("Trying to set ExpressionType of FloatExpression to " + aType.toString());
	}

	@Override
	public CiExpression getLeft() {
		return this;
	}

	@Override
	public void setLeft(final CiExpression aLeft) {
		throw new NotImplementedException(); // TODO
	}

	@Override
	public boolean is_simple() {
		return true;
	}

	@Override
	public String repr_() {
		return toString();
	}

	@Override
	public String toString() {
		return String.format("FloatExpression (%f)", carrier);
	}

	@Override
	public String printableString() {
		throw new UnintendedUseException();
	}
}

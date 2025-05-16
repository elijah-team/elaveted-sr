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
import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.CiExpressionKind;
import tripleo.elijah.ci.CiStringExpression;
import tripleo.util.NotImplementedException;

public class CiStringExpressionImpl extends CiAbstractExpression implements CiStringExpression {
	private String repr_;

	public CiStringExpressionImpl(final Token g) { // TODO List<Token>
		set(g.getText());
	}

	@Override
	public CiExpressionKind getKind() {
		return CiExpressionKind.STRING_LITERAL;
	}

	@Override
	public CiExpression getLeft() {
//		assert false;
//		return this;
		throw new NotImplementedException();
	}

	@Override
	public void setLeft(final CiExpression iexpression) {
		throw new IllegalArgumentException("Should use set()");
	}

	@Override
	public String repr_() {
		return repr_;
	}

	@Override
	public String getText() {
		return CiHelpers.remove_single_quotes_from_string(repr_); // TODO wont work with triple quoted string
	}

	@Override
	public void set(final String g) {
		repr_ = g;
	}

	@Override
	public boolean is_simple() {
		return true;
	}

	@Override
	public String toString() {
		return String.format("<StringExpression %s>", repr_);
	}

}

//
//
//

/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_elevated.lang.impl;

import com.google.common.base.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang2.*;
import tripleo.elijah_elevated_durable.lang_model.*;
import tripleo.elijah_fluffy.util.*;

public class AliasStatementImpl extends __Access implements AliasStatement {
	// private String name;
	private AccessNotation access_note;
	private El_Category category;
	private final OS_Element parent;
	private IExpression expr;
	private IdentExpression nameToken;

	private       EN_Name             __n;
	private final Eventual<LangModel> _p_langModel = new Eventual<>();

	public AliasStatementImpl(final @NotNull OS_Element aParent) {
		this.parent = aParent;
		if (parent instanceof OS_Container) {
			((OS_Container) parent).add(this);
		} else {
			throw new IllegalStateException("adding AliasStatement to " + aParent.getClass().getName());
		}
	}

	@Override
	public AccessNotation getAccess() {
		return access_note;
	}

	@Override
	public El_Category getCategory() {
		return category;
	}

	@Override // OS_Element
	public Context getContext() {
		final OS_Element parent1 = getParent();
		Preconditions.checkNotNull(parent1);
		return parent1.getContext();
	}

	@Override
	public @NotNull EN_Name getEnName() {
		final var nn = name();
		final var _c = this;
		_p_langModel.then((LangModel Slm) -> __n = Slm.createName(nn, _c));
		assert _p_langModel.isResolved();
		return __n;
	}

	@Override
	public Qualident getExpression() {
		return (Qualident) expr;
	}

	@Override // OS_Element
	public OS_Element getParent() {
		return this.parent;
	}

	@Override // OS_Element2
	public @NotNull String name() {
		return this.nameToken.getText();
	}

	@Override
	public void serializeTo(@NotNull SmallWriter sw) {
		// TODO Auto-generated method stub
//		private       AccessNotation  access_note;
//		private       El_Category     category;
//		private final OS_Element      parent;
		sw.fieldIdent("name", nameToken);
		sw.fieldString("expression", expr.toString());
	}

	@Override
	public void setAccess(AccessNotation aNotation) {
		access_note = aNotation;
	}

	// region ClassItem

	@Override
	public void setCategory(El_Category aCategory) {
		category = aCategory;
	}

	public void setExpression(final @NotNull IExpression expr) {
		if (expr.getKind() != ExpressionKind.IDENT && expr.getKind() != ExpressionKind.QIDENT
				&& expr.getKind() != ExpressionKind.DOT_EXP) // TODO need DOT_EXP to QIDENT
		{
			throw new NotImplementedException();
//			tripleo.elijah.util.Stupidity.println_out_2(String.format("[AliasStatement#setExpression] %s %s", expr, expr.getKind()));
		}
		this.expr = expr;
	}

	@Override
	public void setExpression(final Qualident aXy) {
		expr = aXy;
	}

	@Override
	public void setName(@NotNull final IdentExpression i1) {
		this.nameToken = i1;
	}

	@Override
	public void resolveLangModel(final LangModel aEDLLangModel) {
		_p_langModel.resolve(aEDLLangModel);
	}

	@Override // OS_Element
	public void visitGen(final @NotNull ElElementVisitor visit) {
		visit.visitAliasStatement(this);
	}

	// endregion

}

//
//
//

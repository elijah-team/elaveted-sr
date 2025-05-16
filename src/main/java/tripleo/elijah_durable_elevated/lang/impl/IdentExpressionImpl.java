/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_elevated.lang.impl;

import antlr.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_elevated_durable.lang_model.*;
import tripleo.elijah_fluffy.util.*;

import java.io.*;
import java.util.*;

/**
 * @author Tripleo(sb)
 * <p>
 * Created Apr 1, 2019 at 3:21:26 PM
 */
public class IdentExpressionImpl implements IdentExpression {
	private IdentExpressionR r;

	public IdentExpressionImpl(final @NotNull Token r1, @NotNull String aFilename) {
		r = new IdentExpressionR(EDL_LangModel.create(r1), new AttachedImpl(null), r1, aFilename);
	}

	public IdentExpressionImpl(final @NotNull Token r1, @NotNull String aFilename, final @NotNull Context cur) {
		r = new IdentExpressionR(EDL_LangModel.create(r1), new AttachedImpl(cur), r1, aFilename);
		setContext(cur);
		cur.addName(r.name());
	}

	public IdentExpressionImpl(final @NotNull Token r1, final @NotNull Context cur) {
		r = new IdentExpressionR(EDL_LangModel.create(r1), new AttachedImpl(cur), r1, null);
		//setContext(cur);
		cur.addName(r.name());
	}

	@Contract("_ -> new")
	public static @NotNull IdentExpression forString(String string) {
		return LangGlobals.identFromString(string);
	}

	@Override
	public EN_Name getEnName() {
		return r.name();
	}

	@Override
	public String name() {
		return r.text().getText();
	}

	public @NotNull List<FormalArgListItem> getArgs() {
		throw new UnintendedUseException();
	}

	public void setArgs(final ExpressionList ael) {
		throw new UnintendedUseException();
	}

	@Override
	public Context getContext() {
		return r.attached().getContext();
	}

	@Override
	public void setContext(final Context cur) {
		r.attached().setContext(cur);
	}

	@Override
	public OS_Element getParent() {
		// TODO Auto-generated method stub
		throw new NotImplementedException();
//		return null;
	}

	@Override
	public void visitGen(final tripleo.elijah.lang2.@NotNull ElElementVisitor visit) {
		visit.visitIdentExpression(this);
	}

	@Override
	public @NotNull ExpressionKind getKind() {
		return ExpressionKind.IDENT;
	}

	@Override
	public void setKind(final @NotNull ExpressionKind aExpressionKind) {
		final String message = "Trying to set ExpressionType of IdentExpression to " + aExpressionKind;
		//// log and ignore
		//SimplePrintLoggerToRemoveSoon.println_err_2(message);
		throw new BadApiCall(message);
	}

	@Override
	public @NotNull IExpression getLeft() {
		return this;
	}

	@Override
	public void setLeft(final @NotNull IExpression iexpression) {
		//if (iexpression instanceof IdentExpression) {
		//	text = ((IdentExpression) iexpression).text;
		//} else {
		//	// NOTE was tripleo.elijah.util.Stupidity.println_err_2
		final String message = "Trying to set left-side of IdentExpression to " + iexpression.toString();
		//	throw new IllegalArgumentException(message);
		//}
		//throw new UnintendedUseException();
		throw new BadApiCall(message);
	}

	@Override
	public EN_Name getName() {
		return r.name();
	}

	@Override
	public @NotNull String getText() {
		return r.text().getText();
	}

	@Override
	public boolean is_simple() {
		return true;
	}

	@Override
	public String repr_() {
		return String.format("IdentExpression(%s)", r.text().getText());
	}

	@Override
	public void serializeTo(@NotNull SmallWriter sw) {
		sw.fieldString("fileName", r.fileName());
		sw.fieldToken("text", r.text());
		sw.fieldInteger("line", getLine());
		sw.fieldInteger("column", getColumn());
	}

	@Override
	public int getLine() {
		return token().getLine();
	}

	@Override
	public int getColumn() {
		return token().getColumn();
	}

	@Override
	public int getLineEnd() {
		return token().getLine();
	}

	@Override
	public int getColumnEnd() {
		return token().getColumn();
	}

	@Override
	public @NotNull File getFile() {
		assert r.fileName() != null;
		return new File(Objects.requireNonNull(r.fileName()));
	}

	public Token token() {
		return r.text();
	}

	public OS_Type getType() {
		throw new UnintendedUseException();
	}

	/**
	 * same as getText()
	 */
	@Override
	public @NotNull String toString() {
		return asString();
	}

	@Override
	public String asString() {
		return getText();
	}

	// endregion

	public record IdentExpressionR(@NotNull EN_Name name,
								   @NotNull Attached attached,
								   @NotNull Token text,
								   @Nullable String fileName
	) {
	}

}

//
//
//

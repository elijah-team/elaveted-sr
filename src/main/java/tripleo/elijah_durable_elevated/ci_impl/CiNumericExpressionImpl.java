package tripleo.elijah_durable_elevated.ci_impl;

import antlr.Token;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.CiExpressionKind;
import tripleo.elijah.ci.CiExpressionList;
import tripleo.elijah.ci.CiNumericExpression;
import tripleo.elijah_fluffy.util.NotImplementedException;
import tripleo.elijah_fluffy.util.UnintendedUseException;
import tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon;

import java.io.File;

public class CiNumericExpressionImpl implements CiNumericExpression {
	private final int   carrier;
	private final Token n;

	public CiNumericExpressionImpl(final @NotNull Token n) {
		this.n  = n;
		carrier = Integer.parseInt(n.getText());
	}

	@Override // CiExpression
	public CiExpressionKind getKind() {
		return CiExpressionKind.NUMERIC; // TODO
	}

	@Override // CiExpression
	public void setKind(final CiExpressionKind aType) {
		// log and ignore
		SimplePrintLoggerToRemoveSoon
				.println_err_2("Trying to set ExpressionType of NumericExpression to " + aType.toString());
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
		return String.format("NumericExpression (%d)", carrier);
	}

	@Override
	public int getLine() {
		if (token() != null)
			return token().getLine();
		return 0;
	}

	@Override
	public int getValue() {
		return carrier;
	}

	private Token token() {
		return n;
	}

	@Override
	public int getColumn() {
		if (token() != null)
			return token().getColumn();
		return 0;
	}

	@Override
	public int getColumnEnd() {
		if (token() != null)
			return token().getColumn();
		return 0;
	}

	@Override
	public File getFile() {
		if (token() != null) {
			String filename = token().getFilename();
			if (filename != null)
				return new File(filename);
		}
		return null;
	}

	@Override
	public int getLineEnd() {
		if (token() != null)
			return token().getLine();
		return 0;
	}

	@Override
	public String printableString() {
		throw new UnintendedUseException();
	}

	public void setArgs(final CiExpressionList ael) {
		throw new UnintendedUseException();
	}
}

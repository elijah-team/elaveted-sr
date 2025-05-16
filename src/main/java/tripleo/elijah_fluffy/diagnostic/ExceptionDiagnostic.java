package tripleo.elijah_fluffy.diagnostic;

import org.jetbrains.annotations.*;

import java.io.*;
import java.util.*;

public class ExceptionDiagnostic implements Diagnostic {
	private final Throwable e;

	public ExceptionDiagnostic(final Throwable aE) {
		e = aE;
	}

	@Override
	public Object get() {
		return e;
	}

	@Override
	public String code() {
		return "9003";
	}

	@Override
	public Severity severity() {
		return Severity.ERROR;
	}

	@Override
	public @NotNull TextLocatable primary() {
		return null;
	}

	@Override
	public @NotNull List<TextLocatable> secondary() {
		return null;
	}

	@Override
	public void report(final PrintStream stream) {
		stream.println(code() + " Some exception " + e);
	}
}

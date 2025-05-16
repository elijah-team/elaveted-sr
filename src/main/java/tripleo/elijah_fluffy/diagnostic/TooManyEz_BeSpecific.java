package tripleo.elijah_fluffy.diagnostic;

import org.jetbrains.annotations.*;

import java.io.*;
import java.util.*;

public class TooManyEz_BeSpecific implements Diagnostic {
	final String message = "Too many .ez files, be specific.";

	@Override
	public String code() {
		return "9997";
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
		stream.printf("%s %s%n", code(), message);
	}
}

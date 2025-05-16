package tripleo.elijah_durable_elevated.stages.deduce.post_bytecode;

import org.jetbrains.annotations.*;
import tripleo.elijah_fluffy.diagnostic.*;
import tripleo.elijah_fluffy.util.*;

import java.io.*;
import java.util.*;

public class ZeroPotentialDiagnostic implements Diagnostic {
	@Override
	public @Nullable String code() {
		NotImplementedException.raise();
		return null;
	}

	@Override
	public @NotNull TextLocatable primary() {
		NotImplementedException.raise();
		return null;
	}

	@Override
	public void report(final PrintStream stream) {
		NotImplementedException.raise();
		final int y = 2;
	}

	@Override
	public @NotNull List<TextLocatable> secondary() {
		NotImplementedException.raise();
		return null;
	}

	@Override
	public @Nullable Severity severity() {
		NotImplementedException.raise();
		return null;
	}
}

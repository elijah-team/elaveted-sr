package tripleo.elijah_durable_elevated.stages.deduce.post_bytecode;

import org.jetbrains.annotations.*;
import tripleo.elijah_fluffy.diagnostic.*;

import java.io.*;
import java.util.*;

public interface GCFM_Diagnostic extends Diagnostic {
	static @NotNull GCFM_Diagnostic forThis(final @NotNull String aMessage, final @NotNull String aCode,
			final @NotNull Severity aSeverity) {
		return new GCFM_Diagnostic() {
			@Override
			public String _message() {
				return aMessage;
			}

			@Override
			public String code() {
				return aCode;
			}

			@Override
			public @NotNull TextLocatable primary() {
				return null;
			}

			@Override
			public void report(final @NotNull PrintStream stream) {
				stream.printf("%s %s%n", code(), _message());
			}

			@Override
			public @NotNull List<TextLocatable> secondary() {
				return null;
			}

			@Override
			public Severity severity() {
				return aSeverity;
			}
		};
	}

	String _message();
}

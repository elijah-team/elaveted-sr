package tripleo.elijah_durable_elevated.stages.gen_c;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_elevated.stages.deduce.post_bytecode.GCFM_Diagnostic;
import tripleo.elijah_fluffy.diagnostic.TextLocatable;

import java.io.PrintStream;
import java.util.List;

class Diagnostic_8886 implements GCFM_Diagnostic {
	final int _code = 8886;

	@Override
	public String _message() {
		return String.format("%d y is null (No typename specified)", _code);
	}

	@Override
	public @NotNull String code() {
		return "" + _code;
	}

	@Override
	public @NotNull TextLocatable primary() {
		return null;
	}

	@Override
	public void report(final @NotNull PrintStream stream) {
		stream.println(_message());
	}

	@Override
	public @NotNull List<TextLocatable> secondary() {
		return null;
	}

	@Override
	public @NotNull Severity severity() {
		return Severity.ERROR;
	}
}

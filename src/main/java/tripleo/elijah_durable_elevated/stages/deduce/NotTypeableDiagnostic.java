package tripleo.elijah_durable_elevated.stages.deduce;

import org.jetbrains.annotations.*;
import tripleo.elijah_durable_elevated.stages.instructions.*;
import tripleo.elijah_fluffy.diagnostic.*;

import java.io.*;
import java.util.*;

class NotTypeableDiagnostic implements Diagnostic {
    private final InstructionArgument subject;

    public NotTypeableDiagnostic(final InstructionArgument aInstructionArgument) {
        subject = aInstructionArgument;
    }

	@Override
    public @Nullable String code() {
        return "xx0351";
    }

    @Override
    public @NotNull TextLocatable primary() {
        return null; // IALocatable (for each...)
    }

    @Override
    public void report(final PrintStream stream) {
        stream.println(subject.toString() + "is not resolvable");
    }

    @Override
    public @NotNull List<TextLocatable> secondary() {
        return null;
    }

    @Override
    public @Nullable Severity severity() {
        return Severity.ERROR;
    }
}

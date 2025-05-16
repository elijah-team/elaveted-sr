package tripleo.elijah_durable_elevated.comp.internal;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.queries.*;
import tripleo.elijah_elevated_durable.comp_queries.*;
import tripleo.wrap.*;

public class CW_inputIsDirectory {
	public static void apply(final @NotNull CompilerInput input,
							 final @NotNull CompilationClosure cc,
							 final File ff) {
		final File                        directory = input.getFileForDirectory();
		final QuerySearchEzFiles          q         = new QuerySearchEzFiles(cc);
		final CompilerInstructions_Result loci      = q.process(directory);

		input.setDirectoryResults(loci);
	}
}

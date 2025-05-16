package tripleo.elijah_durable_elevated.comp.internal;

import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah_durable_elevated.comp.nextgen.impl.*;
import tripleo.elijah_durable_elevated.stages.deduce.fluffy.impl.*;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.graph.*;

import java.io.*;

public abstract class ILazyCompilerInstructions_ {
	@Contract(value = "_, _, _ -> new", pure = true)
	public static void ofEventual(final @NotNull CompilerInput input,
								  final @NotNull CompilationClosure cc,
								  final Eventual<CompilerInstructions> eilci) {
		final String file_name = input.getInp();
		final File   f         = new File(file_name);

		// 1. Ask the factory
		// 2. "Associate" our givens
		// 3. Ask the source file to process
		// 4. Just return on success
		// 5. Return null for failure

		final EDL_Compilation comp = (EDL_Compilation) cc.getCompilation();
		//final ICompilationAccess3 ca3  = comp.getCompilationAccess3();
		//final FluffyModule        fm   = comp.getFluffy().module(null);

		comp.getFluffy().exchange(new FX_Ez(input, file_name, f))
				.then((final CK_SourceFile sf) -> {
					sf.advise(input, cc);
					final Eventual<CompilerInstructions> sf2 = sf.process_queryEz2();

					sf2.then(eilci::resolve);
					sf2.onFail(eilci::reject);
				});
	}

	public record FX_Ez(CompilerInput compilerInput, String fileName, File file) implements FX_Base {
	}

	@Contract(value = "_, _ -> new", pure = true)
	public static @NotNull ILazyCompilerInstructions of(final @NotNull CompilerInput input,
														final @NotNull CompilationClosure cc) {
		final String file_name = input.getInp();
		final File   f         = new File(file_name);

		return new ILazyCompilerInstructions() {
			private Operation2<CompilerInstructions> operation;

			@Override
			public CompilerInstructions get() {
				// 1. Ask the factory
				// 2. Advise our givens
				// 3. Ask the source file to process
				// 4. Just return on success
				// 5. Return null for failure

				final CK_SourceFile sf = CK_SourceFileFactory.get(f, CK_SourceFileFactory.K.SpecifiedEzFile);
				sf.advise(input, cc);
				operation = sf.process_queryEz();

				if (operation.mode() == Mode.SUCCESS) {
					final CompilerInstructions parsed = operation.success();
					return parsed;
				}

				return null;
			}

			@Override
			public @Nullable Operation2<CompilerInstructions> getOperation() {
				return operation;
			}
		};
	}

	@Contract(value = "_ -> new", pure = true)
	public static @NotNull ILazyCompilerInstructions of(final @NotNull CompilerInstructions aCompilerInstructions) {
		return new ILazyCompilerInstructions() {
			@Override
			public CompilerInstructions get() {
				return aCompilerInstructions;
			}

			@Override
			public @Nullable Operation2<CompilerInstructions> getOperation() {
				return null;
			}
		};
	}
}

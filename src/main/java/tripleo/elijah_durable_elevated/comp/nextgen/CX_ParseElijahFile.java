package tripleo.elijah_durable_elevated.comp.nextgen;

import antlr.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.compiler_model.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_durable_elevated.*;
import tripleo.elijah_durable_elevated.comp.*;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_elevated_durable.comp.specs.*;
import tripleo.elijah_elevated_durable.parser.antlr2.*;
import tripleo.elijah_fluffy.diagnostic.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.graph.*;
import tripleo.wrap.File;

import java.io.*;

public class CX_ParseElijahFile {

	public static Operation2<OS_Module> parseAndCache(final ElijahSpec aSpec,
													  final ElijahCache aElijahCache,
													  final String absolutePath,
													  final EDL_ICompilation compilation) {
		final @NotNull Operation2<OS_Module> calm;

		try {
			final IO                  io       = compilation.getIO();
			final String              f        = aSpec.file_name();
			final File                file     = aSpec.file();
			final EDL_IO._IO_ReadFile readFile = io.readFile2(file);

			try (final InputStream s = readFile.getInputStream()) {
				calm = calculate(f, s, compilation, readFile.getLongPath1());

				if (calm.mode() == Mode.FAILURE) {
					final Diagnostic failure = calm.failure();

					if (failure.get() != null) {
						final Object e0 = failure.get();
						if (e0 instanceof Exception) {
							final Exception e = (Exception) e0;

							if (DebugFlags.youKnowBetter) {
								SimplePrintLoggerToRemoveSoon.println_err2("parser exception: " + e);
								e.printStackTrace(System.err);
							}

							compilation.getErrSink().exception(e);
						}
					} else {
						assert false;
					}
				} else {
					aElijahCache.put(aSpec, absolutePath, calm.success());
				}
			}

			compilation.getObjectTree().asseverate(compilation.megaGrande(aSpec, calm), Asseverate.ELIJAH_PARSED);

			return calm;
		} catch (final IOException aE) {
			return Operation2.failure_exc(aE);
		}
	}

	public static Eventual<OS_Module> parseAndCache2(final ElijahSpec aSpec,
													 final ElijahCache aElijahCache,
													 final String absolutePath,
													 final EDL_ICompilation compilation) {
		final String f    = aSpec.file_name();
		final File   file = aSpec.file();

		final Eventual<OS_Module> ev = parseAndCache2(f, file, aElijahCache, absolutePath, compilation);
		ev.then(Smod -> {
			aElijahCache.put(aSpec, absolutePath, Smod);
			compilation.getObjectTree().asseverate(compilation.megaGrande(aSpec, Operation2.success(Smod)), Asseverate.ELIJAH_PARSED);
		});
		return ev;
	}

	private static Eventual<OS_Module> parseAndCache2(final String f,
													  final File file,
													  final ElijahCache aElijahCache,
													  final String absolutePath,
													  final EDL_ICompilation compilation) {
		final Eventual<OS_Module> ev = new Eventual<>();
		final @NotNull Operation2<OS_Module> calm;

		try {
			final IO                  io       = compilation.getIO();
			final EDL_IO._IO_ReadFile readFile = io.readFile2(file);

			try (final InputStream s = readFile.getInputStream()) {
				calm = calculate(f, s, compilation, readFile.getLongPath1());

				if (calm.mode() == Mode.FAILURE) {
					final Diagnostic failure = calm.failure();

					if (failure.get() != null) {
						final Object e0 = failure.get();
						if (e0 instanceof Exception) {
							final Exception e = (Exception) e0;

							if (DebugFlags.youKnowBetter) {
								SimplePrintLoggerToRemoveSoon.println_err2("parser exception: " + e);
								e.printStackTrace(System.err);
							}

							compilation.getErrSink().exception(e);
						}
					} else {
						assert false;
					}
				}
			}

			ev.resolve(calm.success());
			return ev;
		} catch (final IOException exc) {
			ev.reject(exc);
			return ev;
		}
	}

	private static Operation2<OS_Module> calculate(final String f,
												   final InputStream s,
												   final EDL_ICompilation compilation,
												   final String absolutePath) {
		final ElijjahLexer lexer = new ElijjahLexer(s);
		lexer.setFilename(f);
		final ElijjahParser parser = new ElijjahParser(lexer);

		new Out(f, compilation, parser);
		parser.setFilename(f);


		// README just saved for reference
		//  this is handled by out above and `parser.out.module'
		//parser.ci   = parser.pcon.newCompilerInstructionsImpl();

		try {
			parser.program();
		} catch (final RecognitionException | TokenStreamException aE) {
			return Operation2.failure_exc(aE);
		}
		assert parser.out != null;
		final OS_Module module = parser.out.module();

		final String x = module.getFileName();
		if (x == null) {
			assert false;
			// TODO 09/26 you mentioned that this is a bug
			// FIXME citation needed
			module.setFileName(absolutePath);
		}

		parser.out = null;
		return Operation2.success(module);
	}

	private static Operation2<OS_Module> calculate(final @NotNull ElijahSpec spec, final EDL_ICompilation compilation) {
		final var absolutePath = spec.getLongPath2(); // !!
		return calculate(spec.file_name(), spec.getModule().s(), compilation, absolutePath);
	}

	public static Operation2<OS_Module> __parseEzFile(String file_name,
													  File file,
													  @NotNull ElijahSpecReader r,
													  @NotNull CY_ElijahSpecParser parser) {
		final ElijahSpec spec = new EDL_ElijahSpec(file_name, file, r.get().success());
		return parser.parse(spec);
	}

	public static Operation2<OS_Module> __calculate(final CM_Filename cmf,
													final InputStream s,
													final EDL_ICompilation compilation,
													final String absolutePath) {
		return calculate(cmf.getString(), s, compilation, absolutePath);
	}

	public interface ElijahSpecReader {
		@NotNull
		Operation<InputStream> get();
	}
}

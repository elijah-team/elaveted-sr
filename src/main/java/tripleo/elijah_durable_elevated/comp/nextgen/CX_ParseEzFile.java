package tripleo.elijah_durable_elevated.comp.nextgen;

import antlr.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.compiler_model.*;
import tripleo.elijah_elevated_durable.compiler_model.*;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_elevated_durable.comp.specs.*;
import tripleo.elijah_elevated_durable.parser.*;
import tripleo.elijah_elevated_durable.parser.antlr2.*;
import tripleo.elijah_fluffy.diagnostic.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.wrap.File;

import java.io.*;
import java.util.*;

public enum CX_ParseEzFile {
	;

	public static Operation2<CompilerInstructions> parseAndCache(final EzSpec aSpec,
																 final EzCache aEzCache,
																 final String absolutePath) {
		final @NotNull Operation<InputStream>  cis = Objects.requireNonNull(aSpec.sis());
		final Operation2<CompilerInstructions> cio;

		if (cis.mode() == Mode.SUCCESS) {
			cio = calculate(aSpec.file_name_string(), cis.success());
			final CompilerInstructions R = cio.success();
			aEzCache.put(aSpec, absolutePath, R);

			final CM_Ez cm = ((EDL_ICompilation) aEzCache.getCompilation()).megaGrande(aSpec);
			cm.advise(cio);
			cm.advise(aEzCache.getCompilation().getObjectTree());
		} else {
			cio = Operation2.failure(new ExceptionDiagnostic(cis.failure()));
		}
		return cio;
	}

	private static Operation2<CompilerInstructions> calculate(final String aAbsolutePath, final InputStream aInputStream) {
		final EzLexer lexer = new EzLexer(aInputStream);
		lexer.setFilename(aAbsolutePath);
		final EzParser parser = new EzParser(lexer);
		parser.setFilename(aAbsolutePath);
		parser.pcon = new PCon();
		parser.ci   = parser.pcon.newCompilerInstructionsImpl();
		try {
			parser.program();
		} catch (final RecognitionException | TokenStreamException aE) {
			return Operation2.failure(new ExceptionDiagnostic(aE));
		}
		final CompilerInstructions instructions = parser.ci;
		instructions.setFilename(CM_Factory.Filename__of(aAbsolutePath));
		return Operation2.success(instructions);
	}

	public static Operation<CompilerInstructions> parseEzFile(final @NotNull File aFile,
															  final EDL_ICompilation aCompilation) {
		assert false;
		try (final InputStream readFile = aCompilation.getIO().readFile(aFile)) {

			// FIXME double conversion
			CY_EzSpecParser parser = new CY_EzSpecParser() {
				@Override
				public Operation2<CompilerInstructions> parse(final EzSpec spec) {
					final Operation2<CompilerInstructions> cio = calculate(aFile.getAbsolutePath(), readFile);
					return cio;
				}
			};
			EDL_EzSpec spec = null;

			assert false;

			return Operation.convert(parser.parse(spec));
		} catch (final IOException aE) {
			return Operation.failure(aE);
		}
	}
}

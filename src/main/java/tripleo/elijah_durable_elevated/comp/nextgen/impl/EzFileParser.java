package tripleo.elijah_durable_elevated.comp.nextgen.impl;

import antlr.*;
import com.google.common.base.*;
import org.jdeferred2.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
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
import java.util.Optional;

public class EzFileParser {
	private final Eventual<CompilerInstructions>   ev;
	private final Eventual<EzSpec>                 specP;
	private       Operation2<CompilerInstructions> oci;
	private       EzCache                          ezCache;
	private       EDL_ICompilation                 compilation;

	public EzFileParser() {
		ev = new Eventual<>();
		ev.then(Sci -> {
			oci = Operation2.success(Sci);
		});
		ev.onFail(new FailCallback<Diagnostic>() {
			@Override
			public void onFail(final Diagnostic d) {
				oci = Operation2.failure(d);
			}
		});
		specP = new Eventual<>();
		specP.then(this::onSpec);
	}

	private void onSpec(EzSpec spec) {
		Preconditions.checkNotNull(spec);

		try {
			// [T920907]
			final String absolutePath = spec.file().getCanonicalFile().toString();

			Preconditions.checkNotNull(absolutePath);

			final Optional<CompilerInstructions> early = ezCache.get(absolutePath);
			if (early.isPresent()) {
				final CompilerInstructions actual = early.get();
				ev.resolve(actual);
				return;
			}

			final Operation<InputStream> ois = spec.sis();
			Preconditions.checkNotNull(ois);

			if (ois.mode() == Mode.SUCCESS) {
				_actualParse(spec.file_name_string(), ois.success());
				ev.then(Sci -> {
					ezCache.put(spec, absolutePath, Sci);

					final CM_Ez cm = compilation.megaGrande(spec);

					compilation.lcm().push(new LCM_Just__CM_Ez(cm, Sci));
					cm.advise(ezCache.getCompilation().getObjectTree());
				});
			} else {
//				ev.reject(cio.failure());
				assert false;
			}
		} catch (final IOException aE) {
			final ExceptionDiagnostic d = new ExceptionDiagnostic(aE);
			ev.reject(d);
			return;
		}
	}

	public class SNCI {
		private Diagnostic diagnostic;

		public CompilerInstructions get(final @NotNull EzParser parser, final String absolutePath) {
			final PCon                 pcon         = new PCon();
			final CompilerInstructions instructions = pcon.newCompilerInstructionsImpl();
			instructions.setFilename(CM_Factory.Filename__of(absolutePath));

			parser.pcon = pcon;
			parser.ci   = instructions;

			try {
				parser.program();
				return instructions;
			} catch (final RecognitionException | TokenStreamException aE) {
				this.diagnostic = new ExceptionDiagnostic(aE);
				return null;
			}
		}
	}

	private void _actualParse(final String aAbsolutePath, final InputStream aInputStream) {
		final EzLexer lexer = new EzLexer(aInputStream);
		lexer.setFilename(aAbsolutePath);
		final EzParser parser = new EzParser(lexer);
		parser.setFilename(aAbsolutePath);

		final SNCI                 snci = new SNCI();
		final CompilerInstructions p    = snci.get(parser, aAbsolutePath);
		if (p != null) {
			ev.resolve(p);
			return;
		}
		if (snci.diagnostic != null) {
			ev.reject(snci.diagnostic);
			return;
		}
	}

	public void configure(final EzCache aEzCache) {
		ezCache     = aEzCache;
		compilation = (EDL_ICompilation) ezCache.getCompilation();
	}

	public void parse(final String aFileName, final File aFile, final Operation<InputStream> iso) {
		final EzSpec spec = new EDL_EzSpec(aFileName, aFile, () -> iso);
		specP.resolve(spec);
	}

	public Operation2<CompilerInstructions> oci() {
		return this.oci;
	}

	public Eventual<CompilerInstructions> getEventual() {
		return ev;
	}
}

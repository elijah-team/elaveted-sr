package tripleo.elijah_elevated_durable.comp;

import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.ci_impl.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.compiler_model.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_durable_elevated.comp.caches.*;
import tripleo.elijah_durable_elevated.comp.internal.*;
import tripleo.elijah_durable_elevated.comp.nextgen.*;
import tripleo.elijah_durable_elevated.util.*;
import tripleo.elijah_elevated_durable.*;
import tripleo.elijah_fluffy.diagnostic.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.elijah_potential.comp.*;
import tripleo.wrap.File;

import java.io.*;
import java.util.regex.*;

@SuppressWarnings("UnnecessaryLocalVariable")
public class EDL_USE implements tripleo.elijah.comp.USE {
	private static final   FilenameFilter   accept_source_files = (directory, file_name) -> {
		final boolean matches = Pattern.matches(".+\\.elijah$", file_name)
				|| Pattern.matches(".+\\.elijjah$", file_name);
		return matches;
	};
	private final @NotNull EDL_ICompilation c;
	private final @NotNull ErrSink          errSink;

	@Override
	public ElijahCache getElijahCache() {
		return elijahCache;
	}

	private final @NotNull ElijahCache elijahCache = new DefaultElijahCache();

	@Contract(pure = true)
	public EDL_USE(final @NotNull CompilationClosure cc) {
		c       = (@NotNull EDL_ICompilation) cc.getCompilation();
		errSink = cc.errSink();
	}

	@Override
	public Operation2<OS_Module> findPrelude(final String prelude_name) {
		final CY_FindPrelude cyFindPrelude = new CY_FindPrelude(
				() -> c,
				() -> elijahCache
		);
		return cyFindPrelude.findPrelude(prelude_name);
	}

	@Override
	public void logProgress(CompProgress aCompProgress, String aAbsolutePath) {
		this.c.getCompilationEnclosure().logProgress(aCompProgress, aAbsolutePath);
	}

	@Override
	public void use(final @NotNull CompilerInstructions compilerInstructions) {
		// TODO

		if (compilerInstructions.getFilename() == null)
			return;

		final File file            = compilerInstructions.makeFile();
		final File instruction_dir = file.getParentFile();

		if (instruction_dir == null) {
			 SimplePrintLoggerToRemoveSoon.println_err_4("106106 ************************************** "+file);
			// Prelude.elijjah is a special case
			// instruction_dir = file;
			return;
		}

		for (final LibraryStatementPart lsp : compilerInstructions.getLibraryStatementParts()) {
			final String dir_name = Helpers.remove_single_quotes_from_string(lsp.getDirName());
			final File    dir;// = new File(dir_name);
			USE_Reasoning reasoning = null;
			if (dir_name.equals("..")) {
				dir = instruction_dir/* .getAbsoluteFile() */.getParentFile(); // FIXME 09/26 this has always been questionable
				reasoning = USE_Reasonings.parent(compilerInstructions, true, instruction_dir, lsp);
			} else {
				dir = new File(instruction_dir, dir_name);
				reasoning = USE_Reasonings.child(compilerInstructions, false, instruction_dir, dir_name, dir, lsp);
			}
			use_internal(dir, lsp, reasoning);
		}

		final LibraryStatementPart lsp = new LibraryStatementPartImpl();
		lsp.setName(Helpers0.makeToken("default")); // TODO: make sure this doesn't conflict
		lsp.setDirName(Helpers0.makeToken(String.format("\"%s\"", instruction_dir)));
		lsp.setInstructions(compilerInstructions);
		USE_Reasoning reasoning = USE_Reasonings.default_(compilerInstructions, false, instruction_dir, lsp);
		use_internal(instruction_dir, lsp, reasoning);
	}

	@Override
	public Operation2<OS_Module> parseElijjahFile(@NotNull File f,
												  @NotNull String file_name,
												  @NotNull LibraryStatementPart lsp) {
		logProgress(CompProgress.USE__parseElijjahFile, f.getAbsolutePath());

		if (!f.exists()) {
			final Diagnostic e = new FileNotFoundDiagnostic(f.wrapped()); // eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee

			return Operation2.failure(e);
		}

		Operation2<OS_Module> om;

		try {
			var rdr = new CX_ParseElijahFile.ElijahSpecReader() {
				@Override
				public @NotNull Operation<InputStream> get() {
					try {
						final InputStream readFile = c.getIO().readFile(f);
						return Operation.success(readFile);
					} catch (FileNotFoundException aE) {
						return Operation.failure(aE);
					}
				}
			};
			om = CX_ParseElijahFile.__parseEzFile(
					file_name,
					f,
					rdr,
					//c.getIO(),
					c.con().defaultElijahSpecParser(elijahCache)
			);

			switch (om.mode()) {
			case SUCCESS -> {
				final OS_Module mm = om.success();
				final CM_Module cm = c.megaGrande(mm);

				//assert mm.getLsp() == null;
				//assert mm.prelude() == null;

				cm.advise(lsp);
				cm.advise(() -> findPrelude(CompilationAlways.defaultPrelude()));

				return om;
			}
			default -> {
				return om;
			}
			}
		} catch (final Exception aE) {
			return Operation2.failure(new ExceptionDiagnostic(aE));
		}
	}

	private void use_internal(@NotNull File dir, LibraryStatementPart lsp, USE_Reasoning aReasoning) {
		if (dir.wrapped() == null) {
			int y = 2;
		}
		if (!dir.isDirectory()) {
			errSink.reportError(INTEGER_MARKER_CODES.USE_NOT_A_DIRECTORY, "Not a directory " + dir);
			return;
		}
		//
		final File[] files = dir.listFiles(EDL_USE.accept_source_files);
		if (files != null) {
			CW_sourceDirRequest.apply(files, dir, lsp, (File file) -> {
				final String file_name = file.toString();
				return parseElijjahFile(file, file_name, lsp);
			}, c, aReasoning);
		}
	}

	public Operation2<OS_Module> __parseElijahFile(final File aFile, final String aFileName, final LibraryStatementPart aLsp) {
		assert false;
		return null;
	}
}

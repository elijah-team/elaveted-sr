package tripleo.elijah_elevated_durable.comp_queries;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.queries.*;
import tripleo.elijah_fluffy.diagnostic.*;
import tripleo.graph.*;
import tripleo.wrap.File;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class QuerySearchEzFiles {
	private final @NotNull CompilationClosure cc;
	private final          FilenameFilter     ez_files_filter = new EzFilesFilter();

	public QuerySearchEzFiles(final @NotNull CompilationClosure ccl) {
		this.cc = ccl;
	}

	public CompilerInstructions_Result process(final @NotNull tripleo.wrap.File directory) {
		final CompilerInstructions_Result R = new CompilerInstructions_ResultImpl();
		R.setDirectory(directory);
		R.advise(cc);

		CK_SourceDirectory sd = new CK_SourceDirectory(directory, cc);
		sd.elaborateGet(directory, ez_files_filter);

		return R;
	}

	public static class Diagnostic_9995 implements Diagnostic {
		private final File file;
		private final int  code = 9995;

		public Diagnostic_9995(final File aFile) {
			file = aFile;
		}

		@Override
		public @Nullable String code() {
			return "" + code;
		}

		@Override
		public @NotNull TextLocatable primary() {
			return null;
		}

		@Override
		public void report(final PrintStream stream) {

		}

		@Override
		public @NotNull List<TextLocatable> secondary() {
			return null;
		}

		@Override
		public @Nullable Severity severity() {
			return null;
		}
	}

	public static class EzFilesFilter implements FilenameFilter {
		@Override
		public boolean accept(final java.io.File file, final String s) {
			final boolean matches2 = Pattern.matches(".+\\.ez$", s);
			return matches2;
		}
	}
}

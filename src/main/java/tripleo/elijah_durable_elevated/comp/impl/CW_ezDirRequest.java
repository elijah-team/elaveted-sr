package tripleo.elijah_durable_elevated.comp.impl;

import tripleo.elijah.ci.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.queries.*;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_elevated_durable.comp_queries.*;
import tripleo.elijah_fluffy.diagnostic.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.graph.*;
import tripleo.wrap.*;

import java.util.*;
import java.util.function.*;

import static tripleo.elijah.nextgen.inputtree.EIT_InputType.*;

public class CW_ezDirRequest {
//	public static void apply() {
//	}

	public static List<Operation2<CompilerInstructions>> apply(String[] files,
	                                                           File directory,
	                                                           LibraryStatementPart ignoredALsp,
	                                                           Function<File, CompilerInstructions> parseEzFile,
	                                                           CompilationClosure cc,
	                                                           QSEZ_Reasoning aReasoning) {
		EDL_ICompilation c       = (EDL_ICompilation) cc.getCompilation();
		ErrSink          errSink = cc.errSink();
		List<Operation2<CompilerInstructions>> R       = new ArrayList<>();

		for (final String file_name : files) {
			try {
				final File                 file   = new File(directory, file_name);
				final CompilerInstructions ezFile = parseEzFile.apply(file);
				if (ezFile != null) {
					R.add(Operation2.success(ezFile));

					c.getObjectTree().asseverate(ezFile, Asseverate.EZ_PARSED);
					c.reports().addInput(() -> file_name, EZ_FILE);
				} else {
					R.add(Operation2.failure(new QuerySearchEzFiles.Diagnostic_9995(file)));
					errSink.reportError("9995 ezFile is null " + file); // TODO Diagnostic
				}
			} catch (final Exception e) {
				R.add(Operation2.failure(new ExceptionDiagnostic(e)));
			}
		}
		return R;
	}
}

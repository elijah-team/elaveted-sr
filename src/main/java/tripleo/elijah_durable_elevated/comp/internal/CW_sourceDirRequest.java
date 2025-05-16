package tripleo.elijah_durable_elevated.comp.internal;

import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.wrap.*;

import java.util.function.*;

public class CW_sourceDirRequest {
	@SuppressWarnings("CallToPrintStackTrace")
	public static void apply(File[] files,
							 File ignoredDir,
							 LibraryStatementPart ignoredALsp,
							 Function<File, Operation2<OS_Module>> ffom,
							 EDL_ICompilation c,
							 USE_Reasoning ignoredAReasoning) {
		for (final File file : files) {
//			final CompFactory.InputRequest inp = c.con().createInputRequest(file, do_out, lsp);

			final String file_name = file.toString();

			c.addInput(new EOT_Nameable() {
						   @Override
						   public String getNameableString() {
							   return file_name;
						   }
					   },
					   EIT_InputType.ELIJAH_SOURCE,
					   OS_Module.class,
					   () -> _inputTransformCallback(ffom, file));
		}
	}

	private static @Nullable OS_Module _inputTransformCallback(final Function<File, Operation2<OS_Module>> ffom, final File file) {
		final Operation2<OS_Module> om = ffom.apply(file);

		if (om.mode() == Mode.FAILURE) {
			logProgress(204, "" + om.failure());

			final var d = om.failure().get();
			if (d instanceof Exception e) {
				// help!!
				e.printStackTrace();
			}

			logProgress(2033, d.getClass().getName());
			return null;
		}
		return om.success();
	}

	private static void logProgress(int code, String message) {
		// FIXME ce.logProgress
		System.out.printf("[CW_sourceDirRequest::logProgress] %d %s%n", code, message);
	}
}

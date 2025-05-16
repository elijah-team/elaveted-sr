package tripleo.elijah_elevated_durable.comp_notation;

import com.google.common.collect.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.notation.*;
import tripleo.elijah.stages.logging.*;
import tripleo.elijah_durable_elevated.comp.functionality.f202.*;
import tripleo.elijah_elevated_durable.comp.*;

import java.util.*;

public class GN_WriteLogs implements GN_Notable {
	private final @NotNull ICompilationAccess ca;
	// private final boolean silent;
	private final @NotNull List<ElLog> logs;

	@Contract(pure = true)
	public GN_WriteLogs(final @NotNull ICompilationAccess aCa, final @NotNull List<ElLog> aLogs) {
		ca = aCa;
		// silent = aCa.testSilence() == ElLog.Verbosity.SILENT;
		logs = aLogs;
	}

	@Override
	public void run() {
		if (false) {
			final Multimap<String, ElLog> logMap = ArrayListMultimap.create();

			for (final ElLog deduceLog : logs) {
				logMap.put(deduceLog.getFileName(), deduceLog);
			}

			final F202 f202 = new F202(ca.getCompilation().getErrSink(), (EDL_ICompilation) ca.getCompilation());

			for (final Map.Entry<String, Collection<ElLog>> stringCollectionEntry : logMap.asMap().entrySet()) {
				f202.processLogs(stringCollectionEntry.getValue());
			}
		}
	}
}

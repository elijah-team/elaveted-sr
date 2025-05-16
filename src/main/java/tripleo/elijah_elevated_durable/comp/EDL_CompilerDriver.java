package tripleo.elijah_elevated_durable.comp;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah_fluffy.util.*;

import java.util.*;

public class EDL_CompilerDriver implements CompilerDriver {
	@SuppressWarnings({"FieldCanBeLocal", "unused"})
	private final ICompilationBus                  cb;
	private final Map<DriverToken, CompilerDriven> defaults = new HashMap<>();
	@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
	private final Map<DriverToken, CompilerDriven> drivens  = new HashMap<>();

	@SuppressWarnings("BooleanVariableAlwaysNegated") // dont like this one
	private /* static */ boolean initialized;

	public EDL_CompilerDriver(final ICompilationBus aCompilationBus) {
		cb = aCompilationBus;

		if (!initialized) {
			defaults.put(CompilationAlways.Tokens.COMPILATION_RUNNER_START, new EDL_CD_CompilationRunnerStart());
			defaults.put(CompilationAlways.Tokens.COMPILATION_RUNNER_FIND_STDLIB2, new EDL_CD_FindStdLib());
			initialized = true;
		}
	}

	@Override
	public @NotNull Operation<CompilerDriven> get(final DriverToken aToken) {
		final Operation<CompilerDriven> o;

		if (drivens.containsKey(aToken)) {
			o = Operation.success(drivens.get(aToken));
			return o;
		}

		if (defaults.containsKey(aToken)) {
			final CompilerDriven x = defaults.get(aToken);
			o = Operation.success(x);
		} else {
			o = Operation.failure(new Exception("Compiler Driven get failure"));
		}

		return o;
	}
}

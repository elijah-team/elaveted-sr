package tripleo.elijah_elevated_durable.comp;

import lombok.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah_durable_elevated.comp.i.extra.*;
import tripleo.elijah_durable_elevated.comp.internal.*;
import tripleo.elijah_fluffy.util.*;

import java.util.*;

public class EDL_CB_StartCompilationRunnerAction implements CB_Action, CB_Process {
	static        boolean               started;
	private final EDL_CompilationRunner compilationRunner;
	private final CompilerInstructions  rootCI;
	@Getter
	final                  CB_Output            o;
	private final @NotNull IPipelineAccess      pa;

	@Contract(pure = true)
	public EDL_CB_StartCompilationRunnerAction(final EDL_CompilationRunner aCompilationRunner,
	                                           final @NotNull IPipelineAccess aPa,
	                                           final CompilerInstructions aRootCI) {
		compilationRunner = aCompilationRunner;
		pa                = aPa;
		rootCI            = aRootCI;

		o = pa.getCompilationEnclosure().getCB_Output(); // new CB_Output();
	}

	@Contract(value = " -> new", pure = true)
	@NotNull
	public CB_Process cb_Process() {
		return this;
	}

	@Override
	public void execute(CB_Monitor monitor) {
		final CompilerDriver            compilationDriver = pa.getCompilationEnclosure().getCompilationDriver();
		final Operation<CompilerDriven> ocrsd = compilationDriver.get(CompilationAlways.Tokens.COMPILATION_RUNNER_START);

		switch (ocrsd.mode()) {
		case SUCCESS -> {
			final CD_CompilationRunnerStart compilationRunnerStart = (CD_CompilationRunnerStart) ocrsd.success();
			final CR_State                  crState                = compilationRunner.getCrState();

//			assert !(started);
			if (started) {
				//throw new AssertionError();
				tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon.println_err_4("twice for "+this);
			} else {
				compilationRunnerStart.start(rootCI, crState, o);
				started = true;
			}

			monitor.reportSuccess(this, o);
		}
		case FAILURE, NOTHING -> {
			monitor.reportFailure(this, o);
			throw new IllegalStateException("Error");
		}
		}
	}

	@Contract(pure = true)
	@Override
	public @NotNull String name() {
		return "StartCompilationRunnerAction";
	}

	@Override
	@NotNull
	public List<CB_Action> steps() {
		return tripleo.elijah_fluffy.util.Helpers.List_of(EDL_CB_StartCompilationRunnerAction.this);
	}

	public @NotNull CB_Output getO() {
		// 24/01/04 back and forth
		return this.o;
	}
}

package tripleo.elijah_elevated_durable.comp;

import lombok.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.g.*;
import tripleo.elijah.stateful.*;
import tripleo.elijah_durable_elevated.comp.caches.*;
import tripleo.elijah_durable_elevated.comp.i.extra.*;
import tripleo.elijah_durable_elevated.comp.internal.*;
import tripleo.elijah_elevated_durable.backbone.*;

import java.util.function.*;

public class EDL_CompilationRunner extends _RegistrationTarget implements ICompilationRunner {
	public final @NotNull  EzCache          ezCache;
	private final @NotNull EDL_ICompilation _compilation;
	private final @NotNull ICompilationBus  cb;
	private final @NotNull CR_State        crState;
	@Getter
	private final @NotNull IProgressSink   progressSink;
	private /*@NotNull*/   EDL_CB_StartCompilationRunnerAction startAction;

	public EDL_CompilationRunner(final @NotNull ICompilationAccess aca, final CR_State aCrState) {
		this(
				aca,
				aCrState,
				() -> ((EDL_CompilationEnclosure) aca.getCompilation().getCompilationEnclosure()).getCompilationBus()
			);
	}

	public EDL_CompilationRunner(final @NotNull ICompilationAccess aca,
	                             final @NotNull CR_State aCrState,
	                             final Supplier<ICompilationBus> scb) {
		_compilation = (EDL_ICompilation) aca.getCompilation();

		final CompilationEnclosure compilationEnclosure = _compilation.getCompilationEnclosure();

		compilationEnclosure.setCompilationAccess(aca);

		//final @NotNull CIS    cis = _compilation._cis();
		final ICompilationBus compilationBus = compilationEnclosure.getCompilationBus();

		if (compilationBus == null) {
			cb = scb.get();
			compilationEnclosure.setCompilationBus(cb);
		} else {
			cb = compilationEnclosure.getCompilationBus();
		}

		progressSink = cb.defaultProgressSink();
		crState = aCrState;
		ezCache      = new DefaultEzCache((EDL_ICompilation) aca.getCompilation());
	}

	@Override
	public Compilation _accessCompilation() {
		return _compilation;
	}

	@Override
	public EDL_CIS _cis() {
		return _compilation._cis();
	}

	@Override
	public EDL_ICompilation c() {
		return _compilation;
	}

	@Override
	public EzCache ezCache() {
		return ezCache;
	}

	@Override
	public CompilationEnclosure getCompilationEnclosure() {
		return (CompilationEnclosure) _accessCompilation().getCompilationEnclosure();
	}

	@Override
	public void logProgress(final int number, final String text) {
		if (number == 130)
			return;

		tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon.println_err_3("%d %s".formatted(number, text));
	}

	@Override
	public void start(final CompilerInstructions aRootCI, @NotNull final GPipelineAccess pa) {
		// FIXME only run once 06/16
		if (startAction == null) {
			startAction = new EDL_CB_StartCompilationRunnerAction(this, (IPipelineAccess) pa, aRootCI);

			// FIXME CompilerDriven vs Process ('steps' matches "CK", so...)
			final CB_Process process = startAction.cb_Process();
			cb.add(process);
		} else {
			assert false;
		}
	}

	public static class __CompRunner_Monitor implements CB_Monitor {
		@Override
		public void reportFailure(final CB_Action action, final CB_Output output) {
			tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon.println_err_4(""+output.get());
		}

		@Override
		public void reportSuccess(final CB_Action action, final CB_Output output) {
			int y=2;
			for (final CB_OutputString outputString : output.get()) {
				tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon.println_out_3("** CompRunnerMonitor ::  " + action.name() + " :: outputString :: " + outputString.getText());
			}
		}
	}

	@Override
	public CR_State getCrState() {
		// 24/01/04 back and forth
		return this.crState;
	}
}

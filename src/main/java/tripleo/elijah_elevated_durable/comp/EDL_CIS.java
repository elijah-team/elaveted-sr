package tripleo.elijah_elevated_durable.comp;

import io.reactivex.rxjava3.annotations.*;
import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.subjects.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.g.*;
import tripleo.elijah_durable_elevated.*;
import tripleo.elijah_durable_elevated.comp.*;
import tripleo.elijah_durable_elevated.util.*;
import tripleo.elijah_fluffy.util.*;

public class EDL_CIS implements Observer<CompilerInstructions>, GCIS {
	private final ObservableCompletableProcess<CompilerInstructions> ocp_ci                      = new ObservableCompletableProcess<>();
	private final Subject<CompilerInstructions>                      compilerInstructionsSubject = ReplaySubject.create();
	private       IProgressSink                                      progressSink;
	private       CompilerInstructionsObserver                       _cio;

	public @NotNull Operation<Ok> almostComplete() {
		return _cio.almostComplete();
	}

	@Override
	public void onSubscribe(final io.reactivex.rxjava3.disposables.@NonNull Disposable d) {
		if (DebugFlags.CIS_ocp_ci__FeatureGate) {
			compilerInstructionsSubject.onSubscribe(d);
		} else {
			ocp_ci.onSubscribe(d);
		}
	}

	@Override
	public void onNext(@NonNull final CompilerInstructions aCompilerInstructions) {
		if (DebugFlags.CIS_ocp_ci__FeatureGate) { // l.add
			compilerInstructionsSubject.onNext(aCompilerInstructions);
		} else {
			ocp_ci.onNext(aCompilerInstructions);
		}
	}

	@Override
	public void onError(@NonNull final Throwable e) {
		if (DebugFlags.CIS_ocp_ci__FeatureGate) {
			compilerInstructionsSubject.onError(e);
		} else {
			ocp_ci.onError(e);
		}
	}

	@Override
	public void onComplete() {
		throw new UnintendedUseException();
	}

	public void set_cio(CompilerInstructionsObserver a_cio) {
		_cio = a_cio;
	}

	public void subscribe(final @NotNull Observer<CompilerInstructions> aCio) {
		if (DebugFlags.CIS_ocp_ci__FeatureGate) {
			compilerInstructionsSubject.subscribe(aCio);
		} else {
			ocp_ci.subscribe(aCio);
		}
	}

	public void subscribeTo(final EDL_ICompilation aC) {
		aC.subscribeCI((Observer<CompilerInstructions>) _cio);
	}

	public IProgressSink getProgressSink() {
		return progressSink;
	}

	public void setProgressSink(IProgressSink aProgressSink) {
		progressSink = aProgressSink;
	}
}

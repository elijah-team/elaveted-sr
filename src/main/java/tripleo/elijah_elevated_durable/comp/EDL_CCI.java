package tripleo.elijah_elevated_durable.comp;

import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah_fluffy.util.*;

public class EDL_CCI implements CCI {
	// private final @NotNull Compilation compilation;
	private final EDL_CIS       _cis;
	private final IProgressSink _ps;

	@Contract(pure = true)
	public EDL_CCI(final @NotNull EDL_ICompilation aCompilation, final EDL_CIS aCis, final IProgressSink aProgressSink) {
		// compilation = compilation;
		_cis = aCis;
		_ps = aProgressSink;
	}

	@Override
	public void accept(final @NotNull Maybe<ILazyCompilerInstructions> mcci, final @NotNull IProgressSink aPs) {
		if (mcci.isException())
			return;

		final ILazyCompilerInstructions cci = mcci.o;
		final CompilerInstructions ci = cci.get();

		aPs.note(IProgressSink.Codes.DefaultCCI_accept, ProgressSinkComponent.CCI, -1, new Object[] { ci.getName() });

		IProgressSink t = null;
		try {
			t = _cis.getProgressSink();
			_cis.setProgressSink(aPs);
			_cis.onNext(ci); // CIO::l.add(aCompilerInstructions);
		} finally {
			_cis.setProgressSink(t);
		}
		// compilation.pushItem(ci);
	}
}

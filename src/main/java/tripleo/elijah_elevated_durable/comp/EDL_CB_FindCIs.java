package tripleo.elijah_elevated_durable.comp;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.percy.*;
import tripleo.elijah_durable_elevated.comp.internal.*;
import tripleo.elijah_durable_elevated.nextgen.comp_model.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.wrap.*;

import java.nio.file.*;
import java.util.*;

class EDL_CB_FindCIs implements CB_Action {
	private final EDL_CompilationRunner compilationRunner;
	private final List<CompilerInput>   _inputs;
	private final CB_Output o;

	@Contract(pure = true)
	public EDL_CB_FindCIs(final EDL_CompilationRunner aCompilationRunner, final List<CompilerInput> aInputs) {
		compilationRunner = aCompilationRunner;
		_inputs           = aInputs;
		o                 = compilationRunner.getCompilationEnclosure().getCB_Output();
	}

	@Override
	public void execute(CB_Monitor aMonitor) {
//		final CK_Monitor       monitor11   = /*aMonitor;//*/compilationRunner.getCompilationEnclosure().getDefaultMonitor();
		final CR_State         st = compilationRunner.getCrState();
		final EDL_ICompilation c  = (EDL_ICompilation) st.ca().getCompilation();
		final @NotNull ErrSink errSink = c.getErrSink();
//		final CK_StepsContext  context   = new CD_CRS_StepsContext(st, o);

		for (final CompilerInput input : c.getCompilationEnclosure().getCompilerInput()) {
			_processInput(c.getCompilationClosure(), errSink, input);
		}

		logProgress_Stating("outputString.size", "" + o.get().size());

		tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon.println_out_3("** CB_FindCIs :: outputString.size :: " + o.get().size());

		for (final CB_OutputString outputString : o.get()) {
			// 08/13
			tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon.println_out_3("** CB_FindCIs :: outputString :: " + outputString.getText());
		}

		// TODO capture action outputs
		//  09/27 is that not being done above??
		aMonitor.reportSuccess(this, o);

		//final CK_AlmostComplete almostComplete = new CK_AlmostComplete();
		//almostComplete.execute(context, monitor);
	}

	@Contract(pure = true)
	@Override
	public @NotNull String name() {
		return "FindCIs";
	}

	private void _processInput(final @NotNull CompilationClosure c,
							   final @NotNull ErrSink aErrSink,
							   final @NotNull CompilerInput input) {
		// FIXME 24/01/09 oop
		switch (input.ty()) {
		case NULL, SOURCE_ROOT -> {
		}
		default -> {
			return;
		}
		}

		final CM_CompilerInput cm = ((EDL_Compilation) c.getCompilation()).get(input); // eeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
		final File             f  = cm.fileOf();
		final CompilationClosure compilationClosure = c.getCompilation().getCompilationClosure();

		if (input.isEzFile()) {
			if (input.isNull()) {
				input.certifyRoot();
			}

			final EDL_Compilation compilation = (EDL_Compilation) compilationClosure.getCompilation();
			compilation.addInput3(input, new ElevatedInput3Callback() {
				@Override
				public void run(final CompilerInput aInput, CompilationClosure compilationClosure) {
					CW_inputIsEzFile.apply(aInput, compilationClosure);
				}
			});
		} else {
			// aErrSink.reportError("9996 Not an .ez file "+file_name);
			if (f.isDirectory()) {
				final EDL_Compilation compilation = (EDL_Compilation) compilationClosure.getCompilation();
				compilation.addInput3(input, new ElevatedInput3Callback() {
					@Override
					public void run(final CompilerInput aInput, CompilationClosure compilationClosure) {
						// FIXME 24/01/09 Duplication alert??
						final EDL_Compilation comp = (EDL_Compilation) compilationClosure.getCompilation();
						comp.addCompilerInputWatcher(EDL_CB_FindCIs::__CN_CompilerInputWatcher__event);
						CW_inputIsDirectory.apply(aInput, compilationClosure, f);
					}
				});
			} else {
				final NotDirectoryException d = new NotDirectoryException(f.toString());
				aErrSink.reportError("9995 Not a directory " + f.getAbsolutePath());
			}
		}
	}

	private void logProgress_Stating(final String aSection, final String aStatement) {
		tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon.println_out_3("** CB_FindCIs :: %s :: %s".formatted(aSection, aStatement));
	}

	private static void __CN_CompilerInputWatcher__event(final CN_CompilerInputWatcher.e aEvent, final CompilerInput aCompilerInput, final Object aObject) {
		switch (aEvent) {
		case ACCEPT_CI -> {
			final Maybe<ILazyCompilerInstructions> mci = (Maybe<ILazyCompilerInstructions>) aObject;
			aCompilerInput.accept_ci(mci);
		}
		case IS_EZ -> {
			final CM_CompilerInput cm = (CM_CompilerInput) aObject;
			cm.onIsEz();
		}
		default -> {
			System.err.println("~~ [11/24 111] " + aEvent + " " + aCompilerInput);
		}
		}
	}
}

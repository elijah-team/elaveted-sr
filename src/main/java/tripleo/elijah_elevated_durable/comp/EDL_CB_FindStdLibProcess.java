package tripleo.elijah_elevated_durable.comp;

import org.jdeferred2.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah_durable_elevated.comp.internal.*;
import tripleo.elijah_elevated_durable.backbone.*;
import tripleo.elijah_elevated_durable.compilation_bus.*;
import tripleo.elijah_fluffy.diagnostic.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.elijah_potential.comp.*;

import java.util.*;

import static tripleo.elijah_fluffy.util.Helpers.*;

public class EDL_CB_FindStdLibProcess implements CB_Process {
	private final CB_FindStdLibAction action;

	public EDL_CB_FindStdLibProcess(CompilationEnclosure ce, EDL_CompilationRunner cr) {
		action = new CB_FindStdLibAction(ce, cr);
	}

	@Override
	public List<CB_Action> steps() {
		return List_of(action);
	}

	@Override
	public String name() {
		return "CB_FindStdLibProcess";
	}

	class CB_FindStdLibAction implements CB_Action {
		private final     CompilationEnclosure           ce;
		private final     CR_State                       crState;
		private final     Eventual<CompilerInstructions> _stdLib2;
		private @Nullable CD_FindStdLib                  findStdLib;

		public CB_FindStdLibAction(final CompilationEnclosure aCe, final @NotNull EDL_CompilationRunner aCr) {
			ce      = aCe;
			crState = aCr.getCrState();

			obtain(); // TODO 09/08 Make this more complicated

			_stdLib2 = new Eventual<>();
			_stdLib2.then(Sci -> ce.getCompilation().use(Sci, USE_Reasonings.findStdLib(findStdLib)));
			_stdLib2.onFail(d -> System.err.println("6363 " + d));

		}

		private void obtain() {
			final Operation<CompilerDriven> x = ce.getCompilationDriver()
					.get(CompilationAlways.Tokens.COMPILATION_RUNNER_FIND_STDLIB2);

			if (x.mode() == Mode.SUCCESS) {
				findStdLib = (CD_FindStdLib) x.success();
			}
		}

		@Override
		public void execute(CB_Monitor aMonitor) {
			if (findStdLib != null) {
				final String preludeName = CompilationAlways.defaultPrelude();
				//findStdLib.findStdLib(crState, preludeName, this::getPushItem);

				final Eventual<CompilerInstructions> stdLib2 = ((EDL_CD_FindStdLib) findStdLib).findStdLib2(crState, preludeName);
				stdLib2.then(new DoneCallback<CompilerInstructions>() {
					@Override
					public void onDone(final CompilerInstructions result) {
						_stdLib2.resolve(result);
					}
				});
				stdLib2.onFail(new FailCallback<Diagnostic>() {
					@Override
					public void onFail(final Diagnostic result) {
						assert false;
					}
				});

				final CB_Output o = ce.getCB_Output();
				aMonitor.reportSuccess(this, o);
			}
		}

		@Contract(pure = true)
		@Override
		public @NotNull String name() {
			return "find std lib";
		}

		@Contract(value = " -> new", pure = true)
		public @NotNull CB_Process process() {
			return new SingleActionProcess(this, EDL_CB_FindStdLibProcess.this.name());
		}
	}
}

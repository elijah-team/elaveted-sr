package tripleo.elijah_elevated_durable.comp;

import clojure.lang.*;
import org.jdeferred2.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.g.*;
import tripleo.elijah_durable_elevated.comp.*;
import tripleo.elijah_durable_elevated.comp.internal.*;
import tripleo.elijah_elevated_durable.backbone.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.elijah_prolific.v.*;
import tripleo.graph.*;

import java.util.*;
import java.util.function.*;

public class EDL_CompilerController implements CompilerController {
	private final     Eventual<IPersistentMap>   configP      = new Eventual<>();
	private final     List<EventualRegister>     allRegisters = new ArrayList<>();
	private final     ICompilationAccess3        compilationAccess3;
	private           ICompilationBus            cb;
	private           List<CompilerInput>        inputs;
	private @Nullable EDL_ICompilation           c;
	private final     Eventual<EDL_ICompilation> cP           = new Eventual<>("Controller::Compilation");
	/// fixme (groovy,vavr,kotlin, *clj!!*): LazyKt.lazy();
	private final     CK_Markers                 processMarkers;

	public EDL_CompilerController(final ICompilationAccess3 aCompilationAccess3) {
		compilationAccess3 = aCompilationAccess3;
		assert compilationAccess3.getComp() != null;
		processMarkers = (new Supplier<CK_Markers>() {
			{
				//NotImplementedException.raise_stop();
			}

			@Override
			public CK_Markers get() {

				final CK_Markers res = new CK_Markers() {
					@Override
					public void add(final CK_Marker aCKMarker) {
						final CompilationInterfaceRevised revised = revised();
						if (revised == null) {
							cP.then(Sc -> {
								Sc.revised().addMarker(aCKMarker);
							});
						} else {
							revised.addMarker(aCKMarker);
						}
					}

					@Override
					public int size() {
						final CompilationInterfaceRevised revised = revised();
						if (revised == null) {
							cP.then(Sc -> {
								Sc.revised().markerCount();
							});
							return -1;
						} else {
							return revised().markerCount();
						}
					}
				};
				final String s = "/compiler-controller/0";// + (res.size());
				cP.then(Sc -> {
					res.add(new CK_Marker() {
						@Override
						public String getPath() {
							return s;
						}

						@Override
						public CK_MarkerType getMarkerType() {
							return CK_MarkerType.PROCESS;
						}

					});
				});
				return res;
			}
		}).get();
		//NotImplementedException.raise_stop();
	}

	public void _setInputs(final Compilation aCompilation, final List<CompilerInput> aInputs) {
		if (null != aCompilation) {
			cP.resolve((EDL_ICompilation) aCompilation);
			cP.then(Sc -> c = Sc);
		}
		inputs = aInputs;
	}

	@Override
	public void setEnclosure(final GCompilationEnclosure aCompilationEnclosure) {
		final CompilationEnclosure ce = (CompilationEnclosure) aCompilationEnclosure;
		_setInputs(ce.getCompilation(), ce.getCompilerInput());
	}

	@Override
	public void printUsage() {
		SimplePrintLoggerToRemoveSoon.println_out_2("Usage: eljc [--showtree] [-sE|O] <directory or .ez file names>");
	}

	@Override
	public Operation<Ok> processOptions() {
		assert cP.isResolved();
		class DoneCallback2 implements DoneCallback<EDL_ICompilation> {
			private Operation<Ok> xx;

			@Override
			public void onDone(final EDL_ICompilation result) {

				final OptionsProcessor             op  = new ApacheOptionsProcessor();
				final CompilerInstructionsObserver cio = new CompilerInstructionsObserver(c);
				assert c != null;
				final CompilationEnclosure compilationEnclosure = c.getCompilationEnclosure();

				compilationEnclosure.setCompilationAccess(c.con().createCompilationAccess());
				compilationEnclosure.setCompilationBus(cb = c.con().createCompilationBus());

				c._cis().set_cio(cio);

				this.xx = op.process(c, inputs, cb); // TODO 09/08 Make this more complicated
			}
		}
		;
		final DoneCallback2 cb1 = new DoneCallback2();
		cP.then(cb1);
		return cb1.xx;
	}

	@Override
	public void runner() {
		runner(new _DefaultCon());
	}

	public void hook(final EDL_CompilationRunner aCr) {

	}

	@Override
	public void runner(final @NotNull Con con) {
		if (false) c.____m();

		c._cis().subscribeTo(c);

		final CompilationEnclosure ce = c.getCompilationEnclosure();

		final ICompilationAccess compilationAccess = ce.getCompilationAccess();
		assert compilationAccess != null;

		final ICompilationRunner    icr = con.newCompilationRunner(compilationAccess);
		final EDL_CompilationRunner cr  = (EDL_CompilationRunner) icr;

		ce.setCompilationRunner(cr);

		hook(cr);

//		var inputTree = c.getInputTree();
//
//		for (CompilerInput input : inputs) {
//			if (input.isNull()) // README filter out args
//				inputTree.addNode(input);
//		}

		cb.add(new EDL_CB_FindCIs(cr, inputs));
		cb.add(new EDL_CB_FindStdLibProcess(ce, cr));

//		for (CompilerInput input : inputs) {
//			input.
//		}

		((EDL_CompilationBus) cb).runProcesses();

		c.getFluffy().checkFinishEventuals();

		allRegisters.stream().map(r -> r.maybeCheckFinishEventuals());
		V.exit();
	}

	public ICompilationAccess3 getCompilationAccess3() {
		return compilationAccess3;
	}

	public static class _DefaultCon implements Con {
		@Override
		public EDL_CompilationRunner newCompilationRunner(final ICompilationAccess compilationAccess) {
			final CR_State              crState = new CR_State(compilationAccess);
			final EDL_CompilationRunner cr      = new EDL_CompilationRunner(compilationAccess, crState);

			crState.setRunner(cr);

			return cr;
		}
	}

	@Override
	public void addToAllRegisters(EventualRegister aEventualRegister) {
		allRegisters.add(aEventualRegister);
	}

	@Override
	public void setConfig(final @NotNull IPersistentMap aConfig) {
		this.configP.resolve(aConfig);
	}

	@Override
	public void onConfig(final DoneCallback<IPersistentMap> cb) {
		this.configP.then(cb);
	}

	@Override
	public CompilationInterfaceRevised revised() {
		return this.c != null ? this.c.revised() : null;
	}

	@Override
	public CompilationInterfaceRevised2 revised2() {
		assert this.c != null;
		return this.c.revised2();
	}

	@Override
	public int errorCount() {
		assert this.c != null;
		return this.c.errorCount();
	}
}

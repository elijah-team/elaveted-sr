/*  -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_elevated.comp.internal;

import org.apache.commons.lang3.tuple.*;
import org.jdeferred2.impl.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.notation.*;
import tripleo.elijah.g.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.outputstatement.*;
import tripleo.elijah.stages.logging.*;
import tripleo.elijah_durable_elevated.comp.*;
import tripleo.elijah_durable_elevated.comp.i.*;
import tripleo.elijah_durable_elevated.comp.i.extra.*;
import tripleo.elijah_durable_elevated.comp.nextgen.*;
import tripleo.elijah_durable_elevated.nextgen.output.*;
import tripleo.elijah_durable_elevated.stages.gen_c.*;
import tripleo.elijah_durable_elevated.stages.gen_fn.*;
import tripleo.elijah_durable_elevated.stages.gen_generic.*;
import tripleo.elijah_durable_elevated.stages.gen_generic.pipeline_impl.*;
import tripleo.elijah_durable_elevated.stages.write_stage.pipeline_impl.*;
import tripleo.elijah_elevated_durable.backbone.*;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_fluffy.anno.*;
import tripleo.graph.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.*;

public class CR_State implements GCR_State {

	private final CompilationEnclosure  ce;
	private final ICompilationAccess    ca;
	public        CB_Action             cur;
	public  ProcessRecord         pr;
	private EDL_CompilationRunner compilationRunner;

	@Contract(pure = true)
	public CR_State(ICompilationAccess aCa) {
		ca = aCa;
		pr = new ProcessRecordImpl(ca);
		ce = (CompilationEnclosure) ca.getCompilationEnclosure();

		ce._resolvePipelineAccessPromise(new ProcessRecord_PipelineAccess());
	}

	public CompilationEnclosure getCompilationEnclosure() {
		return ce;
	}

	public ICompilationAccess ca() {
		return ca;
	}

	public EDL_CompilationRunner runner() {
		return compilationRunner;
	}

	public void setRunner(EDL_CompilationRunner aCompilationRunner) {
		compilationRunner = aCompilationRunner;
	}

	private static class ProcessRecordImpl implements ProcessRecord {
		// private final DeducePipeline dpl;
		private final @NotNull ICompilationAccess  ca;
		private             EDL_IPipelineAccess pa;
		private @ElLateInit EDL_PipelineLogic   pipelineLogic;

		public ProcessRecordImpl(final @NotNull ICompilationAccess ca0) {
			ca = ca0;

			final CompilationEnclosure compilationEnclosure0 = (CompilationEnclosure) ca0.getCompilationEnclosure();
			compilationEnclosure0.getPipelineAccessPromise().then(pa0 -> {
				pa = (EDL_IPipelineAccess) pa0;
				pipelineLogic = new EDL_PipelineLogic(pa, ca);
			});
		}

		@Contract(pure = true)
		@Override
		public ICompilationAccess ca() {
			return ca;
		}

		@Contract(pure = true)
		@Override
		public IPipelineAccess pa() {
			return pa;
		}

		@Contract(pure = true)
		@Override
		public EDL_PipelineLogic pipelineLogic() {
			return pipelineLogic;
		}

		@Override
		public void writeLogs() {
			final CompilationEnclosure ce = pa.getCompilationEnclosure();
			ce.writeLogs();
		}
	}

	class ProcessRecord_PipelineAccess implements EDL_IPipelineAccess {
		private final @NotNull List<EvaNode>                                         _l_classes         = new ArrayList<>();
		private final @NotNull List<EvaClass>                                        activeClasses      = new ArrayList<>();
		private final @NotNull List<EvaNamespace>                                          activeNamespaces   = new ArrayList<>();
		private final          org.jdeferred2.impl.DeferredObject<EvaPipeline, Void, Void>               EvaPipelinePromise = new org.jdeferred2.impl.DeferredObject<>();
		private final          Map<OS_Module, org.jdeferred2.impl.DeferredObject<GenerateC, Void, Void>> gc2m_map           = new HashMap<>();
		@SuppressWarnings("rawtypes")
		private final @NotNull Map<Provenance, Pair<Class, Class>>                           installs = new HashMap<>();
		private final          org.jdeferred2.impl.DeferredObject<List<EvaNode>, Void, Void> nlp      = new org.jdeferred2.impl.DeferredObject<>();
		private final          List<NG_OutputItem>                                           outputs = new ArrayList<NG_OutputItem>();
		private final @NotNull DeferredObject<PipelineLogic, Void, Void> ppl     = new org.jdeferred2.impl.DeferredObject<>();
		@NotNull
		List<BaseEvaFunction> activeFunctions = new ArrayList<BaseEvaFunction>();
		private EDL_AccessBus _ab;
		private WritePipeline _wpl;
		private GenerateResultSink  grs;
		private List<CompilerInput> inp;

		@Override
		public void _send_GeneratedClass(final EvaNode aClass) {
			_l_classes.add(aClass);
		}

		@Override
		public void _setAccessBus(final EDL_AccessBus ab) {
			_ab = ab;
		}

		@Override
		public void activeClass(final EvaClass aEvaClass) {
			activeClasses.add(aEvaClass);
		}

		@Override
		public void activeFunction(final BaseEvaFunction aEvaFunction) {
			activeFunctions.add(aEvaFunction);
		}

		@Override
		public void activeNamespace(final EvaNamespace aEvaNamespace) {
			activeNamespaces.add(aEvaNamespace);
		}

		@Override
		public void addLog(final ElLog aLOG) {
			getCompilationEnclosure().addLog(aLOG);
		}

		@Override
		public void addOutput(final NG_OutputItem aOutput) {
			this.outputs.add(aOutput);
		}

		@Override
		public EDL_AccessBus getAccessBus() {
			return _ab;
		}

		@Override
		public @NotNull List<EvaClass> getActiveClasses() {
			return activeClasses;
		}

		@Override
		public @NotNull List<BaseEvaFunction> getActiveFunctions() {
			return activeFunctions;
		}

		@Override
		public @NotNull List<EvaNamespace> getActiveNamespaces() {
			return activeNamespaces;
		}

		@Override
		public EDL_ICompilation getCompilation() {
			return (EDL_ICompilation) ca.getCompilation();
		}

		@Override
		public CompilationEnclosure getCompilationEnclosure() {
			return getCompilation().getCompilationEnclosure();
		}

		@Override
		public List<CompilerInput> getCompilerInput() {
			return getCompilationEnclosure().getCompilerInput();
		}

		@Override
		public void setCompilerInput(final List<CompilerInput> aInputs) {
			getCompilationEnclosure().setCompilerInput(aInputs);
		}

		@Override
		public GenerateResultSink getGenerateResultSink() {
			return grs;
		}

		@Override
		public void setGenerateResultSink(final GenerateResultSink aGenerateResultSink) {
			grs = aGenerateResultSink;
		}

		// @Override
		// public @NotNull List<NG_OutputItem> getOutputs() {
		// return outputs;
		// }

		@Override
		public @NotNull DeferredObject<PipelineLogic, Void, Void> getPipelineLogicPromise() {
			return ppl;
		}

		@Override
		public ProcessRecord getProcessRecord() {
			return pr;
		}

		@Override
		public WritePipeline getWitePipeline() {
			return _wpl;
		}

		@Override
		public void install_notate(final Provenance aProvenance,
		                           final Class<? extends GN_Notable> aRunClass,
		                           final Class<? extends GN_Env> aEnvClass) {
			installs.put(aProvenance, Pair.of(aRunClass, aEnvClass));
		}

		@Override
		public void notate(final Provenance aProvenance, final GN_Env aGNEnv) {
			var y = installs.get(aProvenance);
			// tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon.println_err_4("210 "+y);

			Class<?> x = y.getLeft();
			// var z = y.getRight();

			try {
				var inst = x.getMethod("getFactoryEnv", GN_Env.class);

				var notable1 = inst.invoke(null, aGNEnv);

				if (notable1 instanceof @NotNull GN_Notable notable) {
					final NotableAction notableAction = new NotableAction(notable);

					// cb.add(notableAction);

					notableAction._actual_run();  // eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee

					// tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon.println_err_4("227 "+inst);
				}
			} catch (NoSuchMethodException | SecurityException | InvocationTargetException | IllegalAccessException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		@Override
		public void notate(final Provenance provenance, final @NotNull GN_Notable aNotable) {
			var cb = getCompilationEnclosure().getCompilationBus();

			// FIXME 07/01 this doesn't work, why??
			// also keep in mind `provenance'
			final NotableAction notableAction = new NotableAction(aNotable);

			cb.add(notableAction);

			// 09/01 aNotable.run();
			notableAction._actual_run();
		}

		@Override
		public PipelineLogic pipelineLogic() {
			return getProcessRecord().pipelineLogic();
		}

		@Override
		public void registerNodeList(final org.jdeferred2.DoneCallback<List<EvaNode>> done) {
			nlp.then(done);
		}

		@Override
		public void resolvePipelinePromise(final PipelineLogic aPipelineLogic) {
			this._ab.resolvePipelineLogic(aPipelineLogic);
		}

		@Override
		public void setWritePipeline(final WritePipeline aWritePipeline) {
			_wpl = aWritePipeline;
		}

		@Override
		public void setNodeList(final @NotNull List<EvaNode> aEvaNodeList) {
			nlp/* ;) */.resolve(new ArrayList<>(aEvaNodeList));
		}

		private DeferredObject<GenerateC, Void, Void> gc2m_get(final OS_Module aMod) {
			return gc2m_map.computeIfAbsent(aMod, (__$$) -> Objects.requireNonNull(((EDL_Compilation) aMod.getCompilation()).world().findModule(aMod)).generateCDeferred());
		}

		@Override
		public void resolveWaitGenC(final OS_Module mod, final GenerateFiles aGenerateFiles) {
			gc2m_get(mod).resolve((GenerateC) aGenerateFiles);
		}

		@Override
		public void waitGenC(final OS_Module mod, final Consumer<GenerateC> cb) {
			gc2m_get(mod).then(cb::accept);
		}

		@Override
		public void finishPipeline(final GPipelineMember aPM, final WP_Flow.OPS aOps) {
			final String formatted = "[FinishPipeline] %s %s".formatted(aPM.finishPipeline_asString(), aOps);
			tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon.println_err_4(formatted);
		}

		@Override
		public void runStepsNow(final CK_Steps aSteps, final CK_StepsContext aStepsContext) {
			final CK_Monitor monitor = ((CompilationEnclosure) ca.getCompilationEnclosure()).getDefaultMonitor();
			CK_DefaultStepRunner.runStepsNow(aSteps, aStepsContext, monitor);
		}

		@Override
		public void addFunctionStatement(final EG_Statement aStatement) {
			if (aStatement instanceof FunctionStatement fs) {
				addFunctionStatement(fs);
			}
		}

		@Override
		public void subscribePipelineLogic(final AccessBus.@NotNull AB_PipelineLogicListener aListener) {
			final AccessBus ab = getAccessBus();
			ab.subscribePipelineLogic(aListener::pl_slot);
		}

		@Override
		public void subscribe_lgc(final AccessBus.@NotNull AB_LgcListener aListener) {
			final AccessBus ab = getAccessBus();
			ab.subscribe_lgc(aListener);
		}

		public void addFunctionStatement(final FunctionStatement aFunctionStatement) {
			EvaPipelinePromise.then(gp -> {
				gp.addFunctionStatement(aFunctionStatement);
			});
		}

		@Override
		public void setEvaPipeline(final @NotNull EvaPipeline agp) {
			if (EvaPipelinePromise.isResolved()) {
				EvaPipelinePromise.then(ep -> {
					assert ep == agp;
				});
			} else {
				EvaPipelinePromise.resolve(agp);
			}
		}
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//

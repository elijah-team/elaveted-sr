package tripleo.elijah_elevated_durable.comp;

import io.reactivex.rxjava3.annotations.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.g.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.world.i.*;
import tripleo.elijah_durable_elevated.comp.i.extra.*;
import tripleo.elijah_durable_elevated.stages.deduce.*;
import tripleo.elijah_durable_elevated.stages.gen_fn.*;
import tripleo.elijah_elevated_durable.comp_notation.*;
import tripleo.elijah_fluffy.util.*;

class PL_ModuleListener implements ModuleListener {
	private final EDL_PipelineLogic    pipelineLogic;
//	@SuppressWarnings("FieldCanBeLocal")
//	private final IPipelineAccess      pa1;
	private final PL_ForModuleListener fml;

	public PL_ModuleListener(final EDL_PipelineLogic aPipelineLogic, final IPipelineAccess aPa) {
		pipelineLogic = aPipelineLogic;
//		pa1           = aPa;
		fml = new PL_ForModuleListener(pipelineLogic);
	}

	@Override
	public void close() {
		//NotImplementedException.raise_stop();
	}

	@Override
	public void listen(final GWorldModule module1) {
		final WorldModule module = (WorldModule) module1;
		module.getErq().then(rq -> fml.action(module, rq, pipelineLogic.dp, pipelineLogic.modMap));
	}

	static class PL_ForModuleListener {
		private final EDL_PipelineLogic pipelineLogic;

		public PL_ForModuleListener(final EDL_PipelineLogic aPipelineLogic) {
			pipelineLogic = aPipelineLogic;
		}

		public void action(final @NotNull WorldModule module,
						   final GN_PL_Run2.GenerateFunctionsRequest rq,
						   final DeducePhase dp,
						   final @NonNull EDL_PipelineLogic.@NotNull ModMap p) {
			final OS_Module         mod = module.module();
			final GenerateFunctions gfm = pipelineLogic.getGenerateFunctions(mod);

			gfm.generateFromEntryPoints(rq);

			p.then(mod, this::__internal_GeneratedClasses_cb);
		}

		public void __internal_GeneratedClasses_cb(final @NotNull Eventual<DeducePhase.GeneratedClasses> eventual) {
			final DeducePhase.@NotNull GeneratedClasses lgc = pipelineLogic.dp.getGeneratedClasses();
			eventual.resolve(lgc);
		}
	}
}

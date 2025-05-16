package tripleo.elijah_durable_elevated.stages.deduce.pipeline_impl;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.world.i.*;
import tripleo.elijah_durable_elevated.comp.i.extra.*;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_fluffy.diagnostic.*;
import tripleo.elijah_fluffy.util.*;

class PL_AddModules implements PipelineLogicRunnable {
	private final Eventual<PipelineLogic> plp = new Eventual<>();

	@Contract(pure = true)
	public PL_AddModules(final @NotNull IPipelineAccess aPipelineAccess) {
		var w = aPipelineAccess.getCompilation().world();

		w.addModuleProcess(new CompletableProcess<>() {
			@Override
			public void add(final WorldModule item) {
//				plp.then(pipelineLogic -> pipelineLogic.addModule(item));
				tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon.println_err_4("[PL_AddModules::ModuleCompletableProcess] add "+item.module().getFileName());
			}

			@Override
			public void complete() {
//				NotImplementedException.raise_stop();
			}

			@Override
			public void error(final Diagnostic d) {
			//	throw new UnintendedUseException();
			}

			@Override
			public void preComplete() {
				//throw new UnintendedUseException();
			}

			@Override
			public void start() {
				tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon.println_out_4("[PL_AddModules::ModuleCompletableProcess] start");
			}
		});
	}

	@Override
	public void run(final @NotNull EDL_PipelineLogic pipelineLogic) {
		plp.resolve(pipelineLogic);
	}
}

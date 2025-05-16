package tripleo.elijah_durable_elevated.stages.deduce.pipeline_impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_elevated_durable.comp.EDL_PipelineLogic;

class PL_EverythingBeforeGenerate implements PipelineLogicRunnable {
	@Override
	public void run(final @NotNull EDL_PipelineLogic pipelineLogic) {
		final EDL_PipelineLogic.ModuleCompletableProcess mcp = pipelineLogic._mcp();

		var ce = pipelineLogic._pa().getCompilationEnclosure();
		ce.getCompilation().world().addModuleProcess(mcp);
	}
}

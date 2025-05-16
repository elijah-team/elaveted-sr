package tripleo.elijah_elevated_durable.comp_notation;

import org.jetbrains.annotations.*;
import tripleo.elijah_durable_elevated.stages.gen_generic.*;
import tripleo.elijah_durable_elevated.work.*;

import java.util.function.*;

@SuppressWarnings("unused")
public class GM_GenerateModuleResult {
	final GenerateResult              generateResult;
	final GN_GenerateNodesIntoSink    generateNodesIntoSink;
	final GM_GenerateModuleRequest    generateModuleRequest;
	final Supplier<GenerateResultEnv> figs;

	public GM_GenerateModuleResult(final GenerateResult aGenerateResult, final GN_GenerateNodesIntoSink aGenerateNodesIntoSink, final GM_GenerateModuleRequest aGenerateModuleRequest, final Supplier<GenerateResultEnv> aFigs) {
		generateResult        = aGenerateResult;
		generateNodesIntoSink = aGenerateNodesIntoSink;
		generateModuleRequest = aGenerateModuleRequest;
		figs                  = aFigs;
	}

	void doResult(final @NotNull WorkManager wm) {
		// TODO find GenerateResultEnv and centralise them
		final WorkList       wl             = new EDP_WorkList();
		final GenerateFiles  generateFiles1 = generateModuleRequest.getGenerateFiles(figs);
		final GenerateResult gr             = gr();

		generateFiles1.finishUp(gr, wm, wl);

		wm.addJobs(wl);
		wm.drain();

		gr.additional(generateResult);
	}

	private GenerateResult gr() {
		return generateNodesIntoSink._env().gr();
	}
}

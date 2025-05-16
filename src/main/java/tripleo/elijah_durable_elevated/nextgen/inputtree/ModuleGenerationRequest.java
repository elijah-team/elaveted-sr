package tripleo.elijah_durable_elevated.nextgen.inputtree;

import tripleo.elijah.g.*;
import tripleo.elijah_durable_elevated.work.*;
import tripleo.elijah_durable_elevated.stages.gen_fn.EvaNode;
import tripleo.elijah_durable_elevated.stages.gen_generic.GenerateResult;
import tripleo.elijah_elevated_durable.backbone.CompilationEnclosure;

import java.util.List;
import java.util.function.Consumer;

public interface ModuleGenerationRequest extends GModuleGenerationRequest {
	List<GEvaNode> baseNodes();

	WorkManager work();

	Consumer<GGenerateResult> baseGenerate();

	GCompilationEnclosure baseEnclosure();


	List<EvaNode> getEvaNodeList();

	Consumer<GenerateResult> getGenerateResultConsumer();

	CompilationEnclosure getCompilationEnclosure();
}

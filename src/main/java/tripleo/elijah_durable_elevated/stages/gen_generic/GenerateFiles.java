package tripleo.elijah_durable_elevated.stages.gen_generic;

import org.jetbrains.annotations.*;
import tripleo.elijah.stages.logging.*;
import tripleo.elijah_durable_elevated.stages.gen_fn.*;
import tripleo.elijah_durable_elevated.stages.gen_generic.pipeline_impl.*;
import tripleo.elijah_durable_elevated.work.*;

import java.util.*;

public interface GenerateFiles extends CodeGenerator {
	ElLog elLog();

	void finishUp(final GenerateResult aGenerateResult, final @NotNull WorkManager wm, final WorkList aWorkList);

	void generate_constructor(EvaConstructor aGf, GenerateResult aGr, WorkList aWl, GenerateResultSink aResultSink,
							  final WorkManager aWorkManager, final @NotNull GenerateResultEnv aFileGen);

	void generate_function(EvaFunction aEvaFunction, GenerateResult aGenerateResult, WorkList aWorkList,
						   GenerateResultSink aResultSink);

	GenerateResult generateCode(Collection<EvaNode> lgn, @NotNull GenerateResultEnv aFileGen);

	<T> GenerateResultEnv getFileGen();

	GenerateResult resultsFromNodes(@NotNull List<EvaNode> aNodes, WorkManager wm, GenerateResultSink grs,
			@NotNull GenerateResultEnv fg);
}

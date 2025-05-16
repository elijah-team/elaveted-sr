package tripleo.elijah.comp.i;

import org.jdeferred2.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah_durable_elevated.comp.i.extra.*;
import tripleo.elijah_durable_elevated.stages.gen_fn.*;
import tripleo.elijah_durable_elevated.stages.gen_generic.*;
import tripleo.vendor.mal.*;

import java.util.*;

public interface AccessBus {
	stepA_mal.@NotNull MalEnv2 env();

	@NotNull
	Compilation getCompilation();

	IPipelineAccess getPipelineAccess();

	void resolveGenerateResult(GenerateResult aGenerateResult);

	void resolvePipelineLogic(PipelineLogic aPipelineLogic);

	void subscribe_GenerateResult(@NotNull AB_GenerateResultListener aGenerateResultListener);

	void subscribe_lgc(@NotNull AB_LgcListener aLgcListener);

	void subscribePipelineLogic(DoneCallback<PipelineLogic> aPipelineLogicDoneCallback);

	public interface AB_GenerateResultListener {
		void gr_slot(GenerateResult gr);
	}

	public interface AB_LgcListener {
		void lgc_slot(List<EvaNode> lgc);
	}

	public interface AB_PipelineLogicListener {
		void pl_slot(PipelineLogic lgc);
	}
}

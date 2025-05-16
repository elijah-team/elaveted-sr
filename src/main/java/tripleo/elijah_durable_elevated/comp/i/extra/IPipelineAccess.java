package tripleo.elijah_durable_elevated.comp.i.extra;

import org.jdeferred2.impl.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.notation.*;
import tripleo.elijah.g.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.outputstatement.*;
import tripleo.elijah_durable_elevated.comp.*;
import tripleo.elijah_durable_elevated.comp.i.*;
import tripleo.elijah_durable_elevated.comp.internal.*;
import tripleo.elijah_durable_elevated.nextgen.output.*;
import tripleo.elijah_durable_elevated.stages.gen_fn.*;
import tripleo.elijah_durable_elevated.stages.gen_generic.*;
import tripleo.elijah_durable_elevated.stages.gen_generic.pipeline_impl.*;
import tripleo.elijah_elevated_durable.backbone.*;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.graph.*;

import java.util.*;

public interface IPipelineAccess extends GPipelineAccess {
	void addOutput(NG_OutputItem aO);

	EDL_AccessBus getAccessBus();

	EDL_ICompilation getCompilation();

	CompilationEnclosure getCompilationEnclosure();

	List<CompilerInput> getCompilerInput();

	GenerateResultSink getGenerateResultSink();

	DeferredObject/* Promise */<PipelineLogic, Void, Void> getPipelineLogicPromise();

	ProcessRecord getProcessRecord();

	WritePipeline getWitePipeline();

	void install_notate(Provenance aProvenance, Class<? extends GN_Notable> aRunClass,
						Class<? extends GN_Env> aEnvClass);

	void notate(Provenance aProvenance, GN_Env aPlRun2);

	void notate(Provenance provenance, GN_Notable aNotable);

	PipelineLogic pipelineLogic();

	void registerNodeList(org.jdeferred2.DoneCallback<List<EvaNode>> done);

	void resolvePipelinePromise(PipelineLogic aPipelineLogic);

	void resolveWaitGenC(OS_Module mod, GenerateFiles gc);

	void setCompilerInput(List<CompilerInput> aInputs);

	void setEvaPipeline(@NotNull EvaPipeline agp);

	void setGenerateResultSink(GenerateResultSink aGenerateResultSink);

	void setNodeList(List<EvaNode> aEvaNodeList);

	void setWritePipeline(WritePipeline aWritePipeline);

	void runStepsNow(CK_Steps aSteps, CK_StepsContext aStepsContext);

	void addFunctionStatement(EG_Statement aStatement);

	void subscribePipelineLogic(AccessBus.AB_PipelineLogicListener aListener);

	void subscribe_lgc(AccessBus.@NotNull AB_LgcListener aListener);
}

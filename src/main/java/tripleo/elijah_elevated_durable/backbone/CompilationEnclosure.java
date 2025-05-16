package tripleo.elijah_elevated_durable.backbone;

import io.reactivex.rxjava3.annotations.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.g.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.nextgen.reactive.*;
import tripleo.elijah.stages.logging.*;
import tripleo.elijah.world.i.*;
import tripleo.elijah_durable_elevated.comp.*;
import tripleo.elijah_durable_elevated.comp.i.extra.*;
import tripleo.elijah_durable_elevated.comp.internal.*;
import tripleo.elijah_durable_elevated.pre_world.*;
import tripleo.elijah_durable_elevated.stages.gen_fn.*;
import tripleo.elijah_durable_elevated.stages.generate.*;
import tripleo.elijah_durable_elevated.stages.inter.*;
import tripleo.elijah_durable_elevated.stages.write_stage.pipeline_impl.*;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.graph.*;
import tripleo.paths.*;

import java.util.*;
import java.util.function.*;

public interface CompilationEnclosure extends Asseverable, GCompilationEnclosure {
	void addEntryPoint(@NotNull Mirror_EntryPoint aMirrorEntryPoint, IClassGenerator dcg);

	void addModuleListener(ModuleListener aModuleListener);

	@NotNull ModuleThing addModuleThing(OS_Module aMod);

	void addReactive(@NotNull Reactivable r);

	void addReactive(@NotNull Reactive r);

	void addReactiveDimension(ReactiveDimension aReactiveDimension);

	void AssertOutFile(@NotNull NG_OutputRequest aOutputRequest);

	@NotNull Eventual<AccessBus> getAccessBusPromise();

	@Contract(pure = true)
	CB_Output getCB_Output();

	@Contract(pure = true)
	EDL_ICompilation getCompilation();

	@Contract(pure = true)
	@NotNull ICompilationAccess getCompilationAccess();

	void setCompilationAccess(@NotNull ICompilationAccess aca);

	@Contract(pure = true)
	ICompilationBus getCompilationBus();

	void setCompilationBus(ICompilationBus aCompilationBus);

	// @Contract(pure = true) //??
	CompilationClosure getCompilationClosure();

	@Contract(pure = true)
	CompilerDriver getCompilationDriver();

	@Contract(pure = true)
	ICompilationRunner getCompilationRunner();

	void setCompilationRunner(EDL_CompilationRunner aCompilationRunner);

	@Contract(pure = true)
	List<CompilerInput> getCompilerInput();

	void setCompilerInput(List<CompilerInput> aInputs);

	ModuleThing getModuleThing(OS_Module aMod);

	@Contract(pure = true)
	EDL_IPipelineAccess getPipelineAccess();

	@Contract(pure = true)
	@NotNull Eventual<IPipelineAccess> getPipelineAccessPromise();

	@Contract(pure = true)
	EDL_PipelineLogic getPipelineLogic();

	void setPipelineLogic(EDL_PipelineLogic aPipelineLogic);

	void logProgress(@NotNull CompProgress aCompProgress, Object x);

	void noteAccept(@NotNull WorldModule aWorldModule);

	@NonNull EDL_CompilationEnclosure.OFA OutputFileAsserts();

	void reactiveJoin(Reactive aReactive);

	void setCompilerDriver(CompilerDriver aCompilerDriver);

	void AssertOutFile_Class(OutputStrategyC.OSC_NFC aNfc, NG_OutputRequest aOutputRequest);

	void AssertOutFile_Function(OutputStrategyC.OSC_NFF aNff, NG_OutputRequest aOutputRequest);

	void AssertOutFile_Namespace(OutputStrategyC.OSC_NFN aNfn, NG_OutputRequest aOutputRequest);

	void _resolvePipelineAccessPromise(IPipelineAccess aPa);

	void waitCompilationRunner(Consumer<EDL_CompilationRunner> ccr);

	@Override
	void logProgress2(CompProgress aCompProgress, AsseverationLogProgress aAsseverationLogProgress);

	CK_Monitor getDefaultMonitor();

	void runStepsNow(CK_Steps aSteps, CK_AbstractStepsContext aStepContext);

	PipelinePlugin getPipelinePlugin(String aPipelineName);

	void addPipelinePlugin(final @NotNull Function<GPipelineAccess, PipelineMember> aCr);

	void addPipelinePlugin(PipelinePlugin aPipelinePlugin);

	void writeLogs();

	void addLog(ElLog aLOG);

	List<ElLog> getLogs();

	EIT_ModuleList getModuleList();
}

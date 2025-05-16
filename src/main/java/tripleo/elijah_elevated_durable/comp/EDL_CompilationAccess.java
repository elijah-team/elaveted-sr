package tripleo.elijah_elevated_durable.comp;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.ICompilationAccess;
import tripleo.elijah.g.*;
import tripleo.elijah_durable_elevated.comp.*;
import tripleo.elijah_durable_elevated.stages.deduce.IFunctionMapHook;
import tripleo.elijah_durable_elevated.stages.logging.EDL_ElLog;

import java.util.Collections;
import java.util.List;

public class EDL_CompilationAccess implements ICompilationAccess {
	protected final EDL_ICompilation compilation;
	private final   Pipeline         pipelines = new Pipeline();

	@Contract(pure = true)
	public EDL_CompilationAccess(final EDL_ICompilation aCompilation) {
		compilation = aCompilation;
	}

	@Override
	public void addFunctionMapHook(final GFunctionMapHook aFunctionMapHook1) {
		IFunctionMapHook aFunctionMapHook = (IFunctionMapHook) aFunctionMapHook1;
		compilation.getCompilationEnclosure().getPipelineLogic().dp.addFunctionMapHook(aFunctionMapHook);
	}

	@Override
	public void addPipeline(final GPipelineMember pl) {
		pipelines.add((PipelineMember)pl);
	}

	@Override
	public @NotNull List<GFunctionMapHook> functionMapHooks() {
		return Collections.unmodifiableList(compilation.getCompilationEnclosure().getPipelineLogic().dp.getFunctionMapHooks());
	}

	@Override
	public EDL_ICompilation getCompilation() {
		return compilation;
	}

	@Override
	public @NotNull GPipeline internal_pipelines() {
		return pipelines;
	}

	@Override
	public void setPipelineLogic(final GPipelineLogic pl) {
		setPipelineLogic((EDL_PipelineLogic) pl);
	}

	//@Override
	public void setPipelineLogic(final EDL_PipelineLogic pl) {
		//assert compilation.getCompilationEnclosure().getPipelineLogic() == null;
		if (compilation.getCompilationEnclosure().getPipelineLogic() != null) {
			//throw new AssertionError();
			tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon.println_err_4("903056 pipelineLogic already set");
		} else {
			compilation.getCompilationEnclosure().setPipelineLogic(pl);
		}
	}

	@Override
	@NotNull
	public EDL_ElLog.Verbosity testSilence() {
		return compilation.cfg().silent ? EDL_ElLog.Verbosity.SILENT : EDL_ElLog.Verbosity.VERBOSE;
	}

	@Override
	public GCompilationEnclosure getCompilationEnclosure() {
		return getCompilation().getCompilationEnclosure();
	}

}

package tripleo.elijah_durable_elevated.comp;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.g.*;
import tripleo.elijah_durable_elevated.comp.i.extra.*;
import tripleo.elijah_durable_elevated.stages.hooligan.pipeline_impl.*;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_fluffy.util.*;

public class LawabidingcitizenPipeline extends PipelineMember implements GPipelineMember {
	private final @NotNull IPipelineAccess               pa;
	private final          LawabidingcitizenPipelineImpl i = new LawabidingcitizenPipelineImpl();

	@Contract(pure = true)
	public LawabidingcitizenPipeline(@NotNull GPipelineAccess pa0) {
		pa = (IPipelineAccess) pa0;
	}

	@Override
	public void run(final Ok aSt, final CB_Output aOutput) {
		try {
			final EDL_ICompilation compilation = pa.getCompilation();
			i.run(compilation);
		} catch (Throwable t) {
			t.printStackTrace();
			int y = 2;
			y = y;
		}
	}

	@Override
	public String finishPipeline_asString() {
		return this.getClass().toString();
	}
}

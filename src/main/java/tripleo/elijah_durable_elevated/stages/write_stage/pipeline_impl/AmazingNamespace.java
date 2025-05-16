package tripleo.elijah_durable_elevated.stages.write_stage.pipeline_impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah_elevated_durable.comp.EDL_ICompilation;
import tripleo.elijah_durable_elevated.comp.i.extra.IPipelineAccess;
import tripleo.elijah_durable_elevated.nextgen.output.NG_OutputNamespace;
import tripleo.elijah_durable_elevated.stages.gen_c.GenerateC;
import tripleo.elijah_durable_elevated.stages.gen_fn.EvaNamespace;

class AmazingNamespace implements Amazing {
	private final OS_Module                        mod;
	private final EDL_ICompilation                 compilation;
	private final WPIS_GenerateOutputs.OutputItems itms;
	private final EvaNamespace n;

	public AmazingNamespace(final @NotNull EvaNamespace n, final WPIS_GenerateOutputs.OutputItems itms,
			final IPipelineAccess aPa) {
		this.n = n;
		mod = n.module();
		compilation = (EDL_ICompilation) mod.getCompilation();
		this.itms = itms;
	}

	public OS_Module mod() {
		return mod;
	}

	void waitGenC(final GenerateC ggc) {
		var on = new NG_OutputNamespace();
		on.setNamespace(compilation.world().getNamespace(n).getGarish(), ggc);
		itms.addItem(on);
	}
}

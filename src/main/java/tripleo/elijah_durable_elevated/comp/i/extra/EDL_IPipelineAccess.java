package tripleo.elijah_durable_elevated.comp.i.extra;

import tripleo.elijah.g.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_durable_elevated.stages.gen_c.*;
import tripleo.elijah_durable_elevated.stages.gen_fn.*;
import tripleo.elijah_durable_elevated.stages.write_stage.pipeline_impl.*;
import tripleo.elijah_elevated_durable.comp.*;

import java.util.*;
import java.util.function.*;

public interface EDL_IPipelineAccess extends IPipelineAccess {
	void _send_GeneratedClass(EvaNode aClass);

	void _setAccessBus(EDL_AccessBus ab);

	void activeClass(EvaClass aEvaClass);

	void activeFunction(BaseEvaFunction aEvaFunction);

	void activeNamespace(EvaNamespace aEvaNamespace);

	List<EvaClass> getActiveClasses();

	// List<NG_OutputItem> getOutputs();

	List<BaseEvaFunction> getActiveFunctions();

	List<EvaNamespace> getActiveNamespaces();

	void waitGenC(OS_Module mod, Consumer<GenerateC> aCb);

	void finishPipeline(GPipelineMember aPM, WP_Flow.OPS aOps);

}

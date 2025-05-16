package tripleo.elijah_durable_elevated.comp;

import tripleo.elijah.comp.i.*;
import tripleo.elijah.g.*;
import tripleo.elijah_fluffy.util.*;

public abstract class PipelineMember implements GPipelineMember {
	public abstract void run(Ok aSt, CB_Output aOutput) throws Exception;

	public abstract String finishPipeline_asString();
}

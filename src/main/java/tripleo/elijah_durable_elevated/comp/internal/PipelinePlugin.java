package tripleo.elijah_durable_elevated.comp.internal;

import org.jetbrains.annotations.*;
import tripleo.elijah.g.*;
import tripleo.elijah_durable_elevated.comp.*;

public interface PipelinePlugin {
	@NotNull PipelineMember instance(@NotNull GPipelineAccess aCe);

	String name();
}

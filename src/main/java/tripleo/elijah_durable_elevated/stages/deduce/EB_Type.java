package tripleo.elijah_durable_elevated.stages.deduce;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.logging.*;
import tripleo.elijah_durable_elevated.stages.gen_fn.*;

public interface EB_Type {
    void resolveType(OS_Module aModule, Context aCtx, ElLog LOG, final @NotNull GenType aR) throws ResolveError;
}

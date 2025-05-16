package tripleo.elijah_durable_elevated.stages.deduce.fluffy.i;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_durable_elevated.comp.internal.*;
import tripleo.elijah_durable_elevated.lang.impl.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.graph.*;

public interface FluffyComp extends EventualRegister {
	void find_multiple_items(final @NotNull OS_Module aModule, OS_ModuleImpl.Complaint aC);

	FluffyModule module(OS_Module aModule);

	@Override
	void checkFinishEventuals();

	@Override
	<P> void register(Eventual<P> e);

	Operation<Ok> maybeCheckFinishEventuals();

	Eventual<CK_SourceFile> exchange(ILazyCompilerInstructions_.FX_Ez aFXEz);
}

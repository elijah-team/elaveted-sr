package tripleo.elijah_durable_elevated.comp.internal;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_fluffy.util.*;

public interface CR_Action {
	void attach(@NotNull EDL_CompilationRunner cr);

	@NotNull
	Operation<Ok> execute(@NotNull CR_State st, CB_Output aO);

	String name();
}

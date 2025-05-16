package tripleo.elijah_durable_elevated.world.i;

import tripleo.elijah_durable_elevated.stages.gen_fn.*;
import tripleo.elijah_fluffy.util.*;

public interface LF_CodeRegistration {
	void accept(final EvaFunction aEvaFunction, final Eventual<Integer> aCodeCallback);
}

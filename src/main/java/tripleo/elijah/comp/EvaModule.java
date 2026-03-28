package tripleo.elijah.comp;

import tripleo.elijah.lang.i.*;
import tripleo.elijah.world.i.*;
import tripleo.elijah_durable_elevated.stages.deduce.*;
import tripleo.elijah_fluffy.util.*;

public record EvaModule(
		OS_Module langModule,
		WorldModule worldModule,
		Eventual<DeduceTypes2> deduceTypes2,
		Eventual<DeduceTypes2> deduceTypes22
) {
}

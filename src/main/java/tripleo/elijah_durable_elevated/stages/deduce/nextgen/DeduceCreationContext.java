package tripleo.elijah_durable_elevated.stages.deduce.nextgen;

import org.jetbrains.annotations.*;
import tripleo.elijah_durable_elevated.stages.deduce.*;
import tripleo.elijah_durable_elevated.stages.gen_fn.*;
import tripleo.elijah_fluffy.util.*;

public interface DeduceCreationContext {

	@NotNull DeducePhase getDeducePhase();

	DeduceTypes2 getDeduceTypes2();

	@NotNull GeneratePhase getGeneratePhase();

	Eventual<BaseEvaFunction> makeGenerated_fi__Eventual(FunctionInvocation aFunctionInvocation);

}

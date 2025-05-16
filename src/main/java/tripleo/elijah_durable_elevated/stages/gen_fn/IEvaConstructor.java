package tripleo.elijah_durable_elevated.stages.gen_fn;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.reactive.*;
import tripleo.elijah_durable_elevated.stages.deduce.*;
import tripleo.elijah_durable_elevated.stages.gen_generic.*;
import tripleo.elijah_fluffy.util.*;

public interface IEvaConstructor extends
		IEvaFunctionBase,
		EvaNode,
		DependencyTracker,
		IDependencyReferent,
		ExpectationBase {

	Eventual<DeduceElement3_Constructor> de3_Promise();

	@Override
	@NotNull FunctionDef getFD();

	@Override
	@Nullable VariableTableEntry getSelf();

	@Override
	String identityString();

	@Override
	@NotNull OS_Module module();

	String name();

	void setFunctionInvocation(@NotNull FunctionInvocation fi);

	@Override
	String toString();

	void noteDependencies(Dependency aDependency);

	interface BaseEvaConstructor_Reactive extends Reactive {

	}
}

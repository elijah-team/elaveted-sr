package tripleo.elijah_durable_elevated.stages.gen_fn;

import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.lang.i.FunctionDef;
import tripleo.elijah_durable_elevated.work.*;
import tripleo.elijah_durable_elevated.stages.deduce.*;
import tripleo.elijah_durable_elevated.stages.gen_generic.ICodeRegistrar;


public interface IClassGenerator {
	ICodeRegistrar getCodeRegistrar();

	DeducePhase.GeneratedClasses getGeneratedClasses();

	FunctionInvocation newFunctionInvocation(FunctionDef fd, ProcTableEntry pte, ClassInvocation ci);

	@Nullable
	ClassInvocation registerClassInvocation(ClassStatement cs, String className);

	void submitGenerateClass(ClassInvocation ci, GenerateFunctions gf);

	void submitGenerateFunction(FunctionInvocation ci, GenerateFunctions gf);

	WorkList wl();
}

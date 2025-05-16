package tripleo.elijah.world.i;

import tripleo.elijah.lang.i.*;
import tripleo.elijah_durable_elevated.stages.gen_fn.*;

public interface LivingRepo {
	LivingClass addClass(ClassStatement cs);

	LivingFunction addFunction(IEvaFunctionBase fd);

	LivingPackage addPackage(OS_Package pk);

	OS_Package getPackage(String aPackageName);

	LivingFunction addFunction(IEvaFunctionBase aFunction, Add aMainFunction);

	LivingClass addClass(EvaClass aClass, Add aMainClass);

	void addNamespace(EvaNamespace aNamespace, Add aNone);

	boolean isPackage(String aString);

	enum Add {NONE, MAIN_FUNCTION, MAIN_CLASS}
}

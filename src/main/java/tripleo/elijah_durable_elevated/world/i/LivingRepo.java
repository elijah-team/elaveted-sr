package tripleo.elijah_durable_elevated.world.i;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.g.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.world.i.*;
import tripleo.elijah_durable_elevated.lang.impl.*;
import tripleo.elijah_durable_elevated.stages.gen_fn.*;
import tripleo.elijah_fluffy.util.*;

import java.util.*;
import java.util.function.*;

public interface LivingRepo extends GLivingRepo {
	Collection<WorldModule> getMods__();

	boolean isPackage(String aPkgName);

	WorldModule getModule(OS_Module aMod);

	enum Add {
		MAIN_CLASS, MAIN_FUNCTION, NONE
	}

	void _completeModules();

	ElevatedLivingClass addClass(ClassStatement cs);

	ElevatedLivingClass addClass(EvaClass aClass, Add addFlag);

	ElevatedLivingFunction addFunction(BaseEvaFunction aFunction, Add aMainFunction);

	ElevatedLivingFunction addFunction(BaseFunctionDef fd);

	void addModule(OS_Module mod, String aFilename, final Compilation aC);

	// DefaultLivingClass addClass(EvaClass aClass, Add aMainClass);

	void addModule2(WorldModule aWorldModule);

	void addModuleProcess(CompletableProcess<WorldModule> wmcp);

	/*Default*/LivingNamespace addNamespace(EvaNamespace aNamespace, Add aNone);

	LivingPackage addPackage(OS_Package pk);

	void eachModule(Consumer<WorldModule> object);

	List<ClassStatement> findClass(String main);

	@Nullable WorldModule findModule(final @NotNull OS_Module mod);

	ElevatedLivingClass getClass(EvaClass aEvaClass);

	List<ElevatedLivingClass> getClassesForClassNamed(String string);

	List<ElevatedLivingClass> getClassesForClassStatement(ClassStatement cls);

	ElevatedLivingFunction getFunction(BaseEvaFunction aBaseEvaFunction);

	LivingNamespace getNamespace(EvaNamespace aEvaNamespace);

	OS_Package getPackage(String aPackageName);

	boolean hasPackage(String aPackageName);

	OS_Package makePackage(Qualident aPkgName);

	Collection<WorldModule> modules();
}

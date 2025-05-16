package tripleo.elijah.compiler_model;

import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.nextgen.inputtree.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.g.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_durable_elevated.stages.deduce.fluffy.impl.*;
import tripleo.elijah_fluffy.util.*;

import java.io.*;

public interface CM_Module extends CM_Base {
	void advise(ElijahSpec aSpec);

	void advise(Operation2<OS_Module> aModuleOperation);

	GWorldModule adviseCreator(Compilation aCon);

	void adviseWorld(GLivingRepo aWorld);

	void advise(LibraryStatementPart aLsp);

	void advise(PreludeProvider preludeProvider);

	InputStream s();

	void set(OS_Module aModule);

	EIT_ModuleInput getEITInput();

	/*
	 OS_Module
	 CM_Module
	 WorldModule

	 DeduceTypes2 (DeducedModule)
	 ...
	 */

	interface PreludeProvider {
		Operation2<OS_Module> getOperation();
	}
}

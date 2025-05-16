package tripleo.graph;

import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.compiler_model.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah_fluffy.util.*;

public interface CK_SourceFile extends CM_Base {
	CompilerInput compilerInput();

	EIT_Input input(); // s ??

	EOT_OutputFile output(); // s ??

	Operation2<CompilerInstructions> process_queryEz();

	Eventual<CompilerInstructions> process_queryEz2();

	Eventual<OS_Module> process_queryElijah();

	Eventual<OS_Module> process_queryElijah2();

	void advise(CompilationClosure aCc);

	void advise(CompilerInput aInput, CompilationClosure aCc);
}

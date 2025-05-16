package tripleo.elijah_durable_elevated.comp.i;

import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah_elevated_durable.comp.*;

public interface LCM_CompilerAccess {
	EDL_ICompilation c();

	ICompilationRunner cr();

	Compilation.CompilationConfig cfg();
}

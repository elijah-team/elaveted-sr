package tripleo.elijah.comp.i;

import clojure.lang.*;
import org.jdeferred2.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.g.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.graph.*;

public interface CompilerController {
	void setEnclosure(GCompilationEnclosure aCompilationEnclosure);

	void printUsage();

	Operation<Ok> processOptions();

	void runner();

	void runner(Con con);

	void addToAllRegisters(EventualRegister aEventualRegister);

	void setConfig(@NotNull IPersistentMap aConfig);

	void onConfig(DoneCallback<@NotNull IPersistentMap> aConfig);

	interface Con {
		ICompilationRunner newCompilationRunner(ICompilationAccess aCompilationAccess);
	}

	CompilationInterfaceRevised revised();

	CompilationInterfaceRevised2 revised2();

	int errorCount();
}

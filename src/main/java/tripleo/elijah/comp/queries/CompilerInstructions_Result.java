package tripleo.elijah.comp.queries;

import org.apache.commons.lang3.tuple.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah_fluffy.diagnostic.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.wrap.*;

import java.util.*;

public interface CompilerInstructions_Result {
	List<Operation2<CompilerInstructions>> getDirectoryResult();

	List<Pair<Operation2<CompilerInstructions>, QSEZ_Reasoning>> getDirectoryResult2();

	void add(Operation2<CompilerInstructions> aCio, final QSEZ_Reasoning aReasoning);
	default void add(CompilerInstructions aSci, QSEZ_Reasoning aReasoning) {
		add(aSci, null, aReasoning);
	}

	void setDirectory(File aDirectory);

	void advise(CompilationClosure aCompilationClosure);

	void add(CompilerInstructions aCio, final @Nullable Diagnostic failure, final QSEZ_Reasoning aReasoning);
}

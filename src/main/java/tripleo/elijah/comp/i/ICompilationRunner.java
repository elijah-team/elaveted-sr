package tripleo.elijah.comp.i;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.g.*;

public interface ICompilationRunner {
	EzCache ezCache();

	GCompilationEnclosure getCompilationEnclosure();

	void logProgress(int number, String text);

	void start(CompilerInstructions aRootCI, @NotNull GPipelineAccess pa);

	Compilation _accessCompilation();

	GCIS _cis();

	Compilation c();

	GCR_State getCrState();
}

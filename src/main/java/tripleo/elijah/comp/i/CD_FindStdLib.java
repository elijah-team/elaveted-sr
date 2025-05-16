package tripleo.elijah.comp.i;

import tripleo.elijah.ci.*;
import tripleo.elijah.g.*;
import tripleo.elijah_fluffy.util.*;

import java.util.function.*;

public interface CD_FindStdLib extends CompilerDriven {
	void findStdLib(GCR_State crState, String aPreludeName, Consumer<Operation2<CompilerInstructions>> coci);

	CompilerInstructions maybeFoundResult();
}

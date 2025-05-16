package tripleo.graph;

import tripleo.elijah.comp.i.*;
import tripleo.elijah_fluffy.diagnostic.*;
import tripleo.elijah_fluffy.util.*;

public interface CK_StepsContext {
	void addOutputString(CB_OutputString os);

	void addDiagnostic(Diagnostic d);

	Operation<Ok> getExecutionResult();

	void begin();
}

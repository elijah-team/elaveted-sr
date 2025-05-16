package tripleo.graph;

import tripleo.elijah_fluffy.util.*;

public interface CK_Action {
	Operation<Ok> execute(CK_StepsContext context, CK_Monitor aMonitor); // OutputStrings, Diagnostics, etc...
}

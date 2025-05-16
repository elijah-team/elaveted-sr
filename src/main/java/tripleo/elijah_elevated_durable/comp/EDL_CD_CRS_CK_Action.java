package tripleo.elijah_elevated_durable.comp;

import tripleo.graph.*;
import tripleo.elijah_durable_elevated.comp.internal.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.elijah_potential.comp.xxinternal.*;

class EDL_CD_CRS_CK_Action implements CK_Action {
	private final EDL_CD_CompilationRunnerStart CDCompilationRunnerStart1;
	private final CR_Action                     action;

	public EDL_CD_CRS_CK_Action(final EDL_CD_CompilationRunnerStart aCDCompilationRunnerStart1, final CR_Action aAction) {
		CDCompilationRunnerStart1 = aCDCompilationRunnerStart1;
		action                    = aAction;
	}

	@Override
	public Operation<Ok> execute(final CK_StepsContext context1, final CK_Monitor aMonitor) {
		final CD_CRS_StepsContext context = (CD_CRS_StepsContext) context1;

		final Operation<Ok> result = action.execute(context.getState(), context.getOutput());

		CDCompilationRunnerStart1.getCrActionResultList().add(result); // FIXME 10/20 associate result with action in list (steps)

		return result;
	}
}

package tripleo.elijah_durable_elevated.comp.internal;

import tripleo.graph.*;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.elijah_potential.comp.xxinternal.*;

public class CK_AlmostComplete implements CK_Action {

	@Override
	public Operation<Ok> execute(final CK_StepsContext context1, final CK_Monitor aMonitor) {
		final CD_CRS_StepsContext context           = (CD_CRS_StepsContext) context1;
		final CR_State              crState           = context.getState();
		final EDL_CompilationRunner compilationRunner = crState.runner();

		return compilationRunner._cis().almostComplete();
	}

}

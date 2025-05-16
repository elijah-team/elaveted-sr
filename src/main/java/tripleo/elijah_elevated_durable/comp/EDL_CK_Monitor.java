package tripleo.elijah_elevated_durable.comp;

import tripleo.elijah_fluffy.util.*;
import tripleo.graph.*;

class EDL_CK_Monitor implements CK_Monitor {
	@Override
	public void reportSuccess() {
		throw new UnintendedUseException();
	}

	@Override
	public void reportFailure() {
		throw new UnintendedUseException();
	}
}

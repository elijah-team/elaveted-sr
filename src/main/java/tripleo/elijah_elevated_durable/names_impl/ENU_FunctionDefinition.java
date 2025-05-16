package tripleo.elijah_elevated_durable.names_impl;

import tripleo.elijah_durable_elevated.lang.impl.*;
import tripleo.elijah_elevated_durable.lang_model.*;

public class ENU_FunctionDefinition implements EN_Understanding {
	private final FunctionDefImpl carrier;

	public ENU_FunctionDefinition(final FunctionDefImpl aFunctionDef) {
		carrier = aFunctionDef;
	}

	public FunctionDefImpl getCarrier() {
		return carrier;
	}
}

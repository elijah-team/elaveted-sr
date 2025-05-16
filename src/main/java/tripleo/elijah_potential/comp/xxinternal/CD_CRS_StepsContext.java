package tripleo.elijah_potential.comp.xxinternal;

import tripleo.graph.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah_durable_elevated.comp.internal.*;

public class CD_CRS_StepsContext extends CK_AbstractStepsContext {
	private final CR_State  state;
	private final CB_Output output;

	public CD_CRS_StepsContext(final CR_State aState, final CB_Output aOutput) {
		state  = aState;
		output = aOutput;
	}

	public CR_State getState() {
		// 24/01/04 back and forth
		return this.state;
	}

	public CB_Output getOutput() {
		// 24/01/04 back and forth
		return this.output;
	}

	//void addOutputString(CB_OutputString os);
	//void addDiagnostic(Diagnostic d);
}

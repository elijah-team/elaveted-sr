package tripleo.elijah.world.i;

import org.jdeferred2.impl.*;
import tripleo.elijah.comp.nextgen.inputtree.*;
import tripleo.elijah.g.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_elevated_durable.comp_notation.*;
import tripleo.elijah_durable_elevated.stages.gen_c.*;
import tripleo.elijah_fluffy.util.*;

public interface WorldModule extends GWorldModule {
	EIT_ModuleInput getEITInput();

	Eventual<GN_PL_Run2.GenerateFunctionsRequest> getErq();

	OS_Module module();

	DeferredObject<GenerateC, Void, Void> generateCDeferred();
}

package tripleo.elijah.comp.i;

import tripleo.elijah.comp.*;
import tripleo.elijah_fluffy.util.*;

public interface RuntimeProcess {
	void postProcess();

	void prepare() throws Exception;

	Operation<Ok> run(final Compilation aComp, RP_Context ctx);
}

package tripleo.elijah_durable_elevated.comp.internal;

import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah_durable_elevated.comp.*;
import tripleo.elijah_durable_elevated.comp.i.*;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_fluffy.util.*;

public final class EmptyProcess implements RuntimeProcess {
	public EmptyProcess(final ICompilationAccess aCompilationAccess, final ProcessRecord aPr) {
	}

	@Override
	public void postProcess() {
	}

	@Override
	public void prepare() {
	}

	@Override
	public Operation<Ok> run(final Compilation aComp, final RP_Context ctx0) {
		var ctx = (Pipeline.RP_Context_1) ctx0;
		run((EDL_ICompilation) aComp, ctx.getState(), ctx.getContext());
		return null;
	}

	//@Override
	public void run(final EDL_ICompilation aComp, final CR_State st, final CB_Output output) {

	}
}

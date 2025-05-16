package tripleo.elijah_durable_elevated.comp.impl;

import tripleo.elijah.ci.*;
import tripleo.elijah_durable_elevated.comp.i.*;
import tripleo.elijah_elevated_durable.comp.*;

public class LCM_Event_RootCI implements LCM_Event {
	private static final LCM_Event_RootCI INSTANCE = new LCM_Event_RootCI();

	private LCM_Event_RootCI() {}

	public static LCM_Event_RootCI instance() {
		return INSTANCE;
	}

	@Override
	public void handle(final LCM_HandleEvent aHandleEvent) {
		final LCM_CompilerAccess                c      = aHandleEvent.compilation();
		final CompilerInstructions              rootCI = (CompilerInstructions) aHandleEvent.obj();
		final EDL_Compilation.CompilationConfig cfg    = c.cfg();

		try {
//			c.c().setRootCI(rootCI);
			c.cr().start(rootCI, c.c().pa());
		} catch (Exception aE) {
			aHandleEvent.lcm().exception(aHandleEvent, aE);
		}
	}
}

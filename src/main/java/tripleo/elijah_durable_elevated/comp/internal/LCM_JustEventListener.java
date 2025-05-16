package tripleo.elijah_durable_elevated.comp.internal;

import com.google.common.eventbus.*;
import org.apache.commons.lang3.tuple.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.compiler_model.*;
import tripleo.elijah.nextgen.comp_model.*;
import tripleo.elijah_durable_elevated.*;
import tripleo.elijah_durable_elevated.comp.i.*;
import tripleo.elijah_durable_elevated.comp.nextgen.impl.*;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_fluffy.util.*;

public class LCM_JustEventListener {
	private static int       eventsHandled;
	private        CM_UleLog LOG;

	@Subscribe
	public void someLCM_Event(@NotNull final LCM_HandleEvent aHandleEvent) {
		LOG.info("do event [" + aHandleEvent + "]");
		aHandleEvent.event().handle(aHandleEvent);
		eventsHandled++;
	}

	@Subscribe
	public void _LCM_Just__CM_Ez(final @NotNull LCM_Just__CM_Ez aCMEz) {
		LOG.info("do event [" + aCMEz + "]");
		int y = 2;
		eventsHandled++;
	}

	@Subscribe
	public void _LCM_Just__ELIJAH_PARSED(final @NotNull LCM_Just__ELIJAH_PARSED aELIJAHParsed) {
		LOG.info("do event [" + aELIJAHParsed + "]");
		int y = 2;
		// cr wm
		// gen wm
		// ded ewm
		// genc dwm
		// wr off
		eventsHandled++;

		if (DebugFlags.MakeSense) {
			final CM_Module o = aELIJAHParsed.module();
			final EDL_Compilation c = aELIJAHParsed.compilation();
			o.adviseCreator(c);
			o.adviseWorld(c.world());
		}

	}

	@Subscribe
	public void _LCM_Just__CI_SPECCED(final @NotNull LCM_Just__CI_SPECCED aCISpecced) {
		LOG.info("do event [" + aCISpecced + "]");
		int y = 2;
		eventsHandled++;
	}

	@Subscribe
	public void _LCM_Just__CI_HASHED(final @NotNull LCM_Just__CI_HASHED aEvent) {
		LOG.info("do event [" + aEvent + "]");
		int y = 2;

		var compilation = aEvent.compilation();
		var spec        = aEvent.spec();
		var hash        = aEvent.hash().success();
		var p           = aEvent.sourceFile();

		compilation.getCompilationEnclosure().logProgress(CompProgress.Ez__HasHash, Pair.of(spec, hash));

		if (p.compilerInput() != null) {
			p.compilerInput().accept_hash(hash);
		} else {
			NotImplementedException.raise_stop();
		}
		eventsHandled++;
	}

	@Subscribe
	public void onDeadEvent(DeadEvent aDeadEvent) {
		LOG.info("dead event [" + aDeadEvent + "]");
		int y = 2;
		eventsHandled++;
	}

	public void _setLog(@NotNull EDL_ICompilation _compilation) {
		LOG = _compilation.con().getULog();
	}
}

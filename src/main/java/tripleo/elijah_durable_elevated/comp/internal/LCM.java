package tripleo.elijah_durable_elevated.comp.internal;

import com.google.common.eventbus.*;
import org.jetbrains.annotations.*;
import tripleo.elijah_durable_elevated.comp.i.*;
import tripleo.elijah_elevated_durable.comp.*;

public class LCM {
	private final EventBus          eventBus;
	@SuppressWarnings("FieldCanBeLocal")
	private final LCM_EventListener listener;
	private final EDL_ICompilation  _compilation;
	private final LCM_JustEventListener jl;

	public LCM(final EDL_ICompilation aCompilation) {
		_compilation = aCompilation;

		eventBus     = new EventBus();
		listener     = new LCM_EventListener();
		listener._setLog(_compilation);
		eventBus.register(listener);

		jl = new LCM_JustEventListener();
		jl._setLog(_compilation);
		eventBus.register(jl);
	}

	public void asv(final Object obj, final LCM_Event event) {
		eventBus.post(new LCM_HandleEvent(_compilation.getLCMAccess(), this, obj, event));
	}

	public void exception(final LCM_HandleEvent aHandleEvent, final Exception aE) {
		throw new RuntimeException(aE); // FIXME 11/24 rethrow
	}

	public void push(final LCM_Just aEvent) {
		eventBus.post(aEvent); //ConcatenatedIterator ci;
	}

	public static class LCM_EventListener {
		private        tripleo.elijah.nextgen.comp_model.CM_UleLog LOG;
//		private static final Logger LOG = LoggerFactory.getLogger(LCM_EventListener.class);
		private static int                                         eventsHandled;

		@Subscribe
		public void handleDeadEvent(@NotNull final DeadEvent deadEvent) {
			LOG.info("unhandled event [" + deadEvent.getEvent() + "]");
			eventsHandled++;
		}

		public void _setLog(EDL_ICompilation _compilation) {
			LOG = _compilation.con().getULog();
		}

		@Subscribe
		public void someLCM_Event(@NotNull final LCM_HandleEvent aHandleEvent) {
			LOG.info("do event [" + aHandleEvent + "]");
			aHandleEvent.event().handle(aHandleEvent);
			eventsHandled++;
		}

		void resetEventsHandled() {
			eventsHandled = 0;
		}

		int getEventsHandled() {
			return eventsHandled;
		}
	}
}

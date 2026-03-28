package tripleo.elijah_fluffy.util;

import org.jdeferred2.*;
import org.jdeferred2.impl.*;
import org.jetbrains.annotations.*;
import tripleo.elijah_fluffy.diagnostic.*;

public class Eventual<P> {
	private final String                              mDescription;
	private final DeferredObject<P, Diagnostic, Void> prom = new DeferredObject<>();

	// This is not final so it can change it's spots??
	private EventualBehavior<P> beh = new DefaultEventualBehavior<>();

	{
		beh.setParent(this);
	}

	public Eventual(String aDescription) {
		mDescription = aDescription;
	}

	public Eventual() {
		mDescription = "GENERIC-DESCRIPTION";
	}

	public Eventual(final EventualBehavior<P> aBehavior) {
		this();
		beh = aBehavior/*.advise*/; // !! too much immutable nonsense
		aBehavior.setParent(this);
	}

	public void resolve(final P p) {
		beh.resolve(p);
	}

	public void then(final DoneCallback<? super P> cb) {
		beh.then(cb);
	}

	public void register(@NotNull final EventualRegister er) {
		//beh.register(er);
		er.register(this);
	}

	public void fail(final Diagnostic d) {
		beh.fail(d);
	}

	public boolean isResolved() {
		return beh.isResolved();
	}

	public String description() {
		return mDescription; // !!
	}

	public boolean isFailed() {
		return beh.isFailed();
	}

	public boolean isPending() {
		return beh.isPending();
	}

	public void onFail(final FailCallback<Diagnostic> fcb) {
		beh.onFail(fcb);
	}

	public void reject(final Diagnostic aReject) {
		beh.reject(aReject);
	}

	public boolean isRejected() {
		return beh.isRejected();
	}

	public void reject(final Exception exc) {
		beh.reject(exc);
	}


	public static <T> Eventual<T> never() {
		return new Eventual<>(new NeverEventualBehavior<>());
	}

	@SuppressWarnings("unused")
	public static <T> Eventual<T> resettable() {
		return new Eventual<>(new ResettableEventualBehavior<>());
	}

	public static <T> Eventual<T> finished(T value) {
		return new Eventual<>(new AlreadyEventualBehavior<>(value));
	}

	public static <T> Eventual<T> failed(Diagnostic value) {
		return new Eventual<>(new FailedEventualBehavior<>(value));
	}

	private static class DefaultEventualBehavior<P> implements EventualBehavior<P> {
		private DeferredObject<P, Diagnostic, Void> prom;

		@Override
		public void resolve(final P p) {
			prom.resolve(p);
		}

		@Override
		public void then(final DoneCallback<? super P> cb) {
			prom.then(cb);
		}

		@Override
		public boolean isResolved() {
			return prom.isResolved();
		}

		@Override
		public boolean isFailed() {
			return prom.isRejected();
		}

		@Override
		public boolean isPending() {
			return prom.isPending();
		}

		@Override
		public void onFail(final FailCallback<Diagnostic> fcb) {
			prom.fail(fcb);
		}

		@Override
		public void reject(final Diagnostic aReject) {
			prom.reject(aReject);
		}

		@Override
		public boolean isRejected() {
			return prom.isRejected();
		}

		@Override
		public void reject(final Exception exc) {
			reject(new ExceptionDiagnostic(exc));
		}

		@Override
		public void setParent(final Eventual<P> parent) {
			this.prom = parent.prom;
		}
	}

	private static class ResettableEventualBehavior<P> implements EventualBehavior<P> {
		private DeferredObject<P, Diagnostic, Void> prom;

		@Override
		public void resolve(final P p) {
			prom.resolve(p);
		}

		@Override
		public void then(final DoneCallback<? super P> cb) {
			prom.then(cb);
		}

		@Override
		public boolean isResolved() {
			return prom.isResolved();
		}

		@Override
		public boolean isFailed() {
			return prom.isRejected();
		}

		@Override
		public boolean isPending() {
			return prom.isPending();
		}

		@Override
		public void onFail(final FailCallback<Diagnostic> fcb) {
			prom.fail(fcb);
		}

		@Override
		public void reject(final Diagnostic aReject) {
			prom.reject(aReject);
		}

		@Override
		public boolean isRejected() {
			return prom.isRejected();
		}

		@Override
		public void reject(final Exception exc) {
			reject(new ExceptionDiagnostic(exc));
		}

		@Override
		public void setParent(final Eventual<P> parent) {
			this.prom = parent.prom;
		}
	}

	private static class FailedEventualBehavior<P> implements EventualBehavior<P> {

		private final Diagnostic d;

		public FailedEventualBehavior(final Diagnostic aValue) {
			this.d = aValue;
		}

		@Override
		public void resolve(final P p) {
			throw new IllegalStateException();
		}

		@Override
		public void then(final DoneCallback<? super P> cb) {
			throw new IllegalStateException();
		}

		@Override
		public boolean isResolved() {
			return false;
		}

		@Override
		public boolean isFailed() {
			return true;
		}

		@Override
		public boolean isPending() {
			return false;
		}

		@Override
		public void onFail(final FailCallback<Diagnostic> fcb) {
			fcb.onFail(Diagnostic.withMessage("-1", "Failed Eventual", Diagnostic.Severity.INFO)); // ??
		}

		@Override
		public void reject(final Diagnostic aReject) {
			throw new IllegalStateException();
		}

		@Override
		public boolean isRejected() {
			return isFailed();
		}

		@Override
		public void reject(final Exception exc) {
			throw new IllegalStateException();
		}

		@Override
		public void setParent(final Eventual<P> parent) {
			throw new IllegalStateException();
		}

		@SuppressWarnings({"unused", "SuspiciousGetterSetter"})
		public Diagnostic getDiagnostic() {
			return this.d;
		}
	}

	private static class AlreadyEventualBehavior<P> implements EventualBehavior<P> {
		private final P                                   value;
		private       DeferredObject<P, Diagnostic, Void> prom;

		public AlreadyEventualBehavior(final P aValue) {
			this.value = aValue;
		}

		@Override
		public void resolve(final P p) {
			throw new ProgramIsWrongIfYouAreHere("shouldn't be doing this");
		}

		@Override
		public void then(final DoneCallback<? super P> cb) {
			cb.onDone(value);
		}

		@Override
		public boolean isResolved() {
			return true;
		}

		@Override
		public boolean isFailed() {
			return prom.isRejected();
		}

		@Override
		public boolean isPending() {
			return prom.isPending();
		}

		@Override
		public void onFail(final FailCallback<Diagnostic> fcb) {
			prom.fail(fcb);
		}

		@Override
		public void reject(final Diagnostic aReject) {
			prom.reject(aReject);
		}

		@Override
		public boolean isRejected() {
			return prom.isRejected();
		}

		@Override
		public void reject(final Exception exc) {
			reject(new ExceptionDiagnostic(exc));
		}

		@Override
		public void setParent(final Eventual<P> parent) {
			this.prom = parent.prom;
		}
	}

	private static class NeverEventualBehavior<P> implements EventualBehavior<P> {
		private DeferredObject<P, Diagnostic, Void> prom;

		@Override
		public void resolve(final P p) {
			prom.resolve(p);
		}

		@Override
		public void then(final DoneCallback<? super P> cb) {
			prom.then(cb);
		}

		@Override
		public boolean isResolved() {
			return prom.isResolved();
		}

		@Override
		public boolean isFailed() {
			return prom.isRejected();
		}

		@Override
		public boolean isPending() {
			return prom.isPending();
		}

		@Override
		public void onFail(final FailCallback<Diagnostic> fcb) {
			prom.fail(fcb);
		}

		@Override
		public void reject(final Diagnostic aReject) {
			prom.reject(aReject);
		}

		@Override
		public boolean isRejected() {
			return prom.isRejected();
		}

		@Override
		public void reject(final Exception exc) {
			reject(new ExceptionDiagnostic(exc));
		}

		@Override
		public void setParent(final Eventual<P> parent) {
			this.prom = parent.prom;
		}
	}
}

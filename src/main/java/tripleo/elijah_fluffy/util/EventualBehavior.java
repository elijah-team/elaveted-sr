package tripleo.elijah_fluffy.util;

import org.jdeferred2.*;
import org.jetbrains.annotations.*;
import tripleo.elijah_fluffy.diagnostic.*;

public interface EventualBehavior<P> {
	void resolve(P p);

	void then(DoneCallback<? super P> cb);

	//void register(@NotNull EventualRegister ev);

	default void fail(Diagnostic d) {
		reject(d);
	}

	boolean isResolved();

	//String description();

	boolean isFailed();

	boolean isPending();

	void onFail(FailCallback<Diagnostic> fcb);

	void reject(Diagnostic aReject);

	boolean isRejected();

	void reject(Exception exc);

	void setParent(Eventual<P> parent);
}

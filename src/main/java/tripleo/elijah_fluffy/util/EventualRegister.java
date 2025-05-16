package tripleo.elijah_fluffy.util;

import org.jetbrains.annotations.*;

public interface EventualRegister {
	<P> void register(Eventual<P> e);

	void checkFinishEventuals();

	@NotNull
	String _host();

	Operation<Ok> maybeCheckFinishEventuals();
}

package tripleo.graph;

import org.apache.commons.lang3.tuple.*;
import org.jetbrains.annotations.*;

public interface Cursor<T> {
	long id();

	// fixme: make this more complicated (CompletableFuture nonsense)
	/*Supplier<*/
	@Nullable T/*>*/ get();

	Pair<Long, @NotNull T> get2();
}

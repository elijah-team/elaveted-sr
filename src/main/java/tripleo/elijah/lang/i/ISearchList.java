package tripleo.elijah.lang.i;

import com.google.common.collect.*;
import org.jetbrains.annotations.*;

public interface ISearchList {
	void add(Context c);

	boolean contains(Context context);

	@NotNull ImmutableList<Context> getList();
}

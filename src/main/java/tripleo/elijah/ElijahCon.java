package tripleo.elijah;

import org.jdeferred2.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah_durable_elevated.comp.*;
import tripleo.elijah_durable_elevated.factory.comp.*;
import tripleo.elijah_elevated_durable.comp.*;

import java.util.*;

public class ElijahCon {

	public static void compile(final String[] args, @Nullable final CompilerController[] holder) {
		final EDL_ICompilation   comp        = CompilationFactory.mkCompilation(new StdErrSink(), new EDL_IO());
		final List<String>       stringArray = new ArrayList<>(Arrays.asList(args));
		final CompilerController actual      = comp.feedInputsCon(stringArray);
		if (holder != null) {
			holder[0] = actual;
		}
	}

	public static void compileA(final List<String> stringList,
								@Nullable final List<CompilerController> holder,
								final DoneCallback<CompilerController> cb) {
		final EDL_ICompilation   comp   = CompilationFactory.mkCompilation(new StdErrSink(), new EDL_IO());
		final CompilerController actual = comp.feedInputsCon(stringList);
		if (holder != null) {
			holder.set(0, actual);
		}
		cb.onDone(actual);
	}
}

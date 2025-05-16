package tripleo.elijah;

import org.jdeferred2.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah_durable_elevated.comp.*;
import tripleo.elijah_durable_elevated.factory.comp.*;
import tripleo.elijah_elevated_durable.comp.*;

import java.util.*;

public class ElijahCon {

	public static void compile(final String[] args, final CompilerController[] holder) {
		final List<String>       stringList = new ArrayList<>(Arrays.asList(args));
		final EDL_ICompilation   comp       = CompilationFactory.mkCompilation(new StdErrSink(), new EDL_IO());
		final CompilerController actual     = comp.feedInputsCon(stringList);
		if (holder != null) {
			holder[0] = actual;
		}
	}

	public static void compileA(final List<String> args,
								final List<CompilerController> holder,
								final DoneCallback<CompilerController> cb) {
		final EDL_ICompilation   comp       = CompilationFactory.mkCompilation(new StdErrSink(), new EDL_IO());
		final CompilerController actual     = comp.feedInputsCon(args);
		if (holder != null) {
			holder.set(0, actual);
		}
		cb.onDone(actual);
	}
}

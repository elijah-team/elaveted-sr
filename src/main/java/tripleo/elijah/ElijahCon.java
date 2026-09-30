package tripleo.elijah;

import org.jdeferred2.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah_durable_elevated.comp.*;
import tripleo.elijah_durable_elevated.factory.*;
import tripleo.elijah_durable_elevated.factory.comp.*;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.graph.*;

import java.util.*;

public class ElijahCon {

	public static void compile(final String[] args, @Nullable final CompilerController[] holder) {
		final EDL_ICompilation   comp       = CompilationFactory.mkCompilation(new StdErrSink(), new EDL_IO());
		final List<String>       stringList = new ArrayList<>(Arrays.asList(args));
		final CompilerController actual     = feedInputsCon(comp, stringList);
		if (holder != null) {
			holder[0] = actual;
		}
	}

	/**
	 * Convenience function
	 */
	// @Override
	public static CompilerController feedInputsCon(final EDL_ICompilation comp, final List<String> aStringList) {
		// contrast with defaultCompilerController
		final NonOpinionatedBuilder nob        = new NonOpinionatedBuilder();
		final ICompilationAccess3   ca3        = comp.getCompilationAccess3();
		final CompilerController    controller = nob.createCompilerController(ca3);
		comp._ccP().resolve(controller); // !!
		final CompilationInterfaceRevised revised = comp.revised();
		assert revised != null;
		final int state = revised.getState();
		assert state == 0; // fixme replace this with something more advanced/applicable (parts has roman state machine, for example (activej vs maybe clojure.logic))

		revised.advise(new RevisedAdvisable() {
			@Override
			public void reverse(final CompilationInterfaceRevised aCompilationInterfaceRevised, final Compilation c) {
				assert comp == c;
				comp.feedInputs(nob.inputs(aStringList), controller);
			}
		});
		final int state2 = revised.getState();
		// noinspection ExcessiveRangeCheck,ConditionCoveredByFurtherCondition
		assert state2 == 1 || state2 != 0;
		return controller;
	}

	public static void compileA(final List<String> stringList,
								@Nullable final List<CompilerController> holder,
								final DoneCallback<CompilerController> cb) {
		final EDL_ICompilation   comp   = CompilationFactory.mkCompilation(new StdErrSink(), new EDL_IO());
		final CompilerController actual = feedInputsCon(comp, stringList);
		if (holder != null) {
			holder.set(0, actual);
		}
		cb.onDone(actual);
	}


}

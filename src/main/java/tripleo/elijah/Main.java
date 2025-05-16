package tripleo.elijah;

import clojure.lang.*;
import org.jdeferred2.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.i.CompilerController;
import tripleo.elijah_fluffy.util.*;

import java.util.*;

public class Main {

	public static class ElavetedRunner {

		private CompilerController[] ca;
		private String[]             stringArray;
		private boolean              triggerOk;
		private List<String>         stringList;

		public void defaultCompilerController() {
			CompilerController[] ca = new CompilerController[1];
			this.ca = ca;
		}

		public void feedArray(final String[] aStringArray) {
			this.stringArray = aStringArray;
		}

		public boolean trigger() {
			if (this.ca == null) return false;
			if (this.stringArray == null) return false;
			ElijahCon.compile(this.stringArray, ca);
			return (this.triggerOk = true);
		}

		public CompilerController getCompilerController() {
			assert triggerOk();
			return this.ca[0];
		}

		private boolean triggerOk() {
			return this.triggerOk;
		}

		private CompilerController @NotNull [] feedCljList(final @NotNull PersistentList pargs, final List<String> args2) {
			String[] args = new String[pargs.count()];
			int      i    = 0;
			for (Object str : pargs) {
				args[i++] = (String) str;
				args2.add((String) str);
			}

			this.stringList = args2;

			feedArray(args);
			return ca;
		}

		public boolean triggerCallback(final IPersistentMap config) {
			if (this.ca == null) return false;
			if (this.stringArray == null) return false;

			assert this.stringList != null;

			ElijahCon.compileA(this.stringList, Helpers.List_of(ca), new DoneCallback<CompilerController>() {
				@Override
				public void onDone(final CompilerController value) {
					_onCompilerController(value, config);
				}
			});


			return (this.triggerOk = true);
		}

		private static void _onCompilerController(final @NotNull CompilerController value,
												  final IPersistentMap aConfig) {
			value.setConfig(aConfig);
			final String key  = "CompilerController";
			final Object ccs0 = aConfig.valAt(key, null);
			if (ccs0 != null) {
				final IFn ccs = (IFn) ccs0;
				ccs.invoke(value);
			}
		}
	}

	public static void main(final String[] args) throws Exception {
		ElavetedRunner elaveted = new ElavetedRunner();
		elaveted.defaultCompilerController();
		elaveted.feedArray(args);
		assert elaveted.trigger();
	}

	public static CompilerController main2(final String[] args) {
		ElavetedRunner elaveted = new ElavetedRunner();
		elaveted.defaultCompilerController();
		elaveted.feedArray(args);
		assert elaveted.trigger();
		return elaveted.getCompilerController();
	}

	public static CompilerController main3(final clojure.lang.@NotNull PersistentList pargs,
										   final clojure.lang.IPersistentMap config) {
		ElavetedRunner elaveted = new ElavetedRunner();
		elaveted.defaultCompilerController();

		if (false) {
			//elaveted.feedArray(args);
			assert elaveted.trigger();
			return elaveted.getCompilerController();
		}

		final List<String> args2 = new ArrayList<>();
		elaveted.feedCljList(pargs, args2);
		final boolean b = elaveted.triggerCallback(config);
		assert b;
		return elaveted.getCompilerController();
	}

}

package tripleo.elijah;

import clojure.lang.*;
import org.jdeferred2.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah_fluffy.util.*;

import java.util.*;

public class Main {

	public static void main(final String[] args) throws Exception {
		ElavetedRunner elaveted = new ElavetedRunner();
		elaveted.defaultCompilerController();
		elaveted.feedArray(args);
		if (!elaveted.trigger()) throw new AssertionError();
	}

	public static CompilerController main2(final String[] args) {
		ElavetedRunner elaveted = new ElavetedRunner();
		elaveted.defaultCompilerController();
		elaveted.feedArray(args);
		if (!elaveted.trigger()) throw new AssertionError();
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
		if (!b) throw new AssertionError();
		return elaveted.getCompilerController();
	}

	public static class ElavetedRunner {

		private final Eventual<Ok>         _key = new Eventual<>("ElavetedRunner::key");
		private       CompilerController[] ca;
		private       String[]             stringArray;
		private       boolean              triggerOk;
		private       List<String>         stringList;
		private       boolean              _done;

		public void defaultCompilerController() {
			CompilerController[] ca = new CompilerController[1];
			this.ca = ca;
			check();
		}

		private void check() {
			if (this._done) return;
			if (this.ca == null) return;
			if (this.stringArray == null) return;
			this.key().then((Ok ignored) -> {
				ElijahCon.compile(stringArray, ca);
				triggerOk = true;
				_done     = true;
			});
		}

		private <P, F> Eventual<Ok> key() {
			return this._key;
		}

		public boolean trigger() {
			if (!this._done) {
				this.key().resolve(Ok.instance());
				check();
			}
			return this._done;
		}

		public CompilerController getCompilerController() {
			if (!triggerOk()) throw new AssertionError();
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

		public void feedArray(final String[] aStringArray) {
			this.stringArray = aStringArray;
			check();
		}

		public boolean triggerCallback(final IPersistentMap config) {
			if (this.ca == null) return false;
			if (this.stringArray == null) return false;

			if (this.stringList == null) throw new AssertionError();

			ElijahCon.compileA(this.stringList, Helpers.List_of(ca), new DoneCallback<CompilerController>() {
				@Override
				public void onDone(final CompilerController value) {
					_onCompilerController(value, config);
				}
			});

			return (this.triggerOk = true);
		}

		private static void _onCompilerController(final @NotNull CompilerController aController,
												  final IPersistentMap aConfig) {
			aController.setConfig(aConfig);
			final String key  = "CompilerController";
			final Object ccs0 = aConfig.valAt(key, null);
			if (ccs0 != null) {
				final IFn ccs = (IFn) ccs0;
				ccs.invoke(aController);
			}
		}
	}

}

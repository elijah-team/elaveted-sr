package tripleo.elijah_clojure.example;

import clojure.java.api.Clojure;
import clojure.lang.IFn;

import java.util.concurrent.Callable;

public class CljExampleMain {
	public static Object callClojure(String ns, String fn) throws Exception {
		final IFn    require = Clojure.var("clojure.core", "require");
		final Object read    = Clojure.read(ns);
		require.invoke(read);

		return ((Callable) Clojure.var(ns, fn)).call();
	}

	public static Object callClojure2() throws Exception {
		final IFn    require = Clojure.var("clojure.core", "require");
		final Object read    = Clojure.read(packageName);
		require.invoke(read);

		String fn = "start";
		return ((Callable) Clojure.var(packageName, fn)).call();
	}

	public static final String packageName = "evaleted-lein.aleph";
}

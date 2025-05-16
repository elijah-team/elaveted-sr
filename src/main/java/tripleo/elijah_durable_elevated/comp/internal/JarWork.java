package tripleo.elijah_durable_elevated.comp.internal;

import clojure.lang.*;

public interface JarWork {
	default void logProgress(String result) {
		System.out.println(result);
	}

	void work() throws WorkException;

	void callfoo(Var foo);
}

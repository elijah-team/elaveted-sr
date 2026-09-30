package tripleo.elijah_fluffy.util;


import tripleo.elijah_fluffy.diagnostic.*;

public interface CompletableProcess<T> {
	void add(T item);

	void complete();

	void error(ElDiagnostic d);

	void preComplete();

	void start();
}

package tripleo.elijah_durable_elevated.comp;

public interface TriConsumer<A, B, C> {
	void accept(A a, B b, C c);
}

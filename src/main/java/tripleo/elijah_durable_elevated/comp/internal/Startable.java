package tripleo.elijah_durable_elevated.comp.internal;

public interface Startable {
	void start();

	Thread stealThread();

	boolean isSignalled();
}

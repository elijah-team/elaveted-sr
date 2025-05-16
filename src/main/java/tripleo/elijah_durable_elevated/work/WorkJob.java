package tripleo.elijah_durable_elevated.work;

public interface WorkJob {
	void run(WorkManager aWorkManager);

	boolean isDone();
}

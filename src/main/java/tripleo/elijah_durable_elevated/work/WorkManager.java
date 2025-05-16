package tripleo.elijah_durable_elevated.work;

import org.jetbrains.annotations.*;

public interface WorkManager {
	void drain();

	@Nullable WorkJob next();

	int totalSize();

	void addJobs(WorkList aWorkList);
}

package tripleo.elijah_durable_elevated.work;

import java.util.*;

public interface WorkList {
	boolean isDone();

	void addJob(WorkJob aJob);

	List<WorkJob> getJobs();

	boolean isEmpty();

	void setDone();
}

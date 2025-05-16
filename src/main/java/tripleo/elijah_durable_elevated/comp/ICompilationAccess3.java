package tripleo.elijah_durable_elevated.comp;

import tripleo.elijah.comp.*;
import tripleo.elijah.stages.logging.*;
import tripleo.elijah_elevated_durable.comp.*;

import java.util.*;

public interface ICompilationAccess3 {
	EDL_ICompilation getComp();

	boolean getSilent();

	void addLog(ElLog aLog);

	void writeLogs(boolean aSilent);

	PipelineLogic getPipelineLogic();

	List<ElLog> getLogs();
}

package tripleo.elijah_durable_elevated.comp.i;

import tripleo.elijah.comp.i.*;
import tripleo.elijah.g.*;
import tripleo.elijah_durable_elevated.comp.i.extra.*;
import tripleo.elijah_elevated_durable.comp.*;

public interface ProcessRecord extends GProcessRecord {
	ICompilationAccess ca();

	IPipelineAccess pa();

	EDL_PipelineLogic pipelineLogic();

	@Override
	void writeLogs();
}

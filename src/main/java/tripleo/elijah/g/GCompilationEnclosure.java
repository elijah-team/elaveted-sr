package tripleo.elijah.g;

import org.apache.commons.lang3.tuple.*;
import tripleo.elijah.comp.i.*;
import tripleo.paths.*;

public interface GCompilationEnclosure {
	GModuleThing addModuleThing(GOS_Module aModule);

	void logProgress(CompProgress aCompProgress, Pair<Integer, String> aCodeMessagePair);

	GModuleThing getModuleThing(GOS_Module aModule);

	void logProgress2(CompProgress aCompProgress, AsseverationLogProgress aAsseverationLogProgress);
}

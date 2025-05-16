package tripleo.elijah.comp.i;

import tripleo.elijah.comp.*;
import tripleo.elijah_fluffy.util.*;

import java.util.*;

@FunctionalInterface
public interface OptionsProcessor {
	Operation<Ok> process(Compilation aC, List<CompilerInput> aInputs, ICompilationBus aCb);
}

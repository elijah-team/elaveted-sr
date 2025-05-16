package tripleo.elijah.comp.i;

import tripleo.elijah.comp.specs.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_fluffy.util.*;

public interface CY_ElijahSpecParser {
	Operation2<OS_Module> parse(ElijahSpec spec);
}

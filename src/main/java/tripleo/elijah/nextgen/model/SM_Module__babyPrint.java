package tripleo.elijah.nextgen.model;

import org.jetbrains.annotations.*;
import tripleo.elijah_fluffy.util.*;

public enum SM_Module__babyPrint {
	;

	public static void babyPrint(final @NotNull SM_Module sm) {
		for (final SM_ModuleItem item : sm.items()) {
			SimplePrintLoggerToRemoveSoon.println_out_4(String.valueOf(item));
		}
	}
}

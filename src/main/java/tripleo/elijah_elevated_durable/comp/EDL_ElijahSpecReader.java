package tripleo.elijah_elevated_durable.comp;

import org.jetbrains.annotations.*;
import tripleo.elijah_durable_elevated.comp.nextgen.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.paths.*;

import java.io.*;

class EDL_ElijahSpecReader implements CX_ParseElijahFile.ElijahSpecReader {
	private final CP_Path          local_prelude;
	private final EDL_ICompilation c;

	public EDL_ElijahSpecReader(final CP_Path aCPPath, final EDL_ICompilation aC) {
		c             = aC;
		local_prelude = aCPPath;
	}

	@Override
	public @NotNull Operation<InputStream> get() {
		try {
			final InputStream readFile = local_prelude.getReadInputStream(c.getIO());
			return Operation.success(readFile);
		} catch (FileNotFoundException aE) {
			return Operation.failure(aE);
		}
	}
}

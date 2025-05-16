package tripleo.elijah_elevated_durable.comp.specs;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah_fluffy.diagnostic.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.wrap.File;

import java.io.*;
import java.util.function.*;

public record EDL_EzSpec(
		String file_name,
		tripleo.wrap.File file,
		Supplier<Operation<InputStream>> sois) implements EzSpec {

	public static Operation2<EzSpec> of(final String aFileName, final File aFile, final Operation<InputStream> aInputStreamOperation) {
		if (aInputStreamOperation.mode() == Mode.SUCCESS) {
			final EDL_EzSpec ezSpec = new EDL_EzSpec(aFileName, aFile, new Supplier<Operation<InputStream>>() {
				@Override
				public Operation<InputStream> get() {
					return aInputStreamOperation;
				}
			});
			return Operation2.success(ezSpec);
		} else {
			return Operation2.failure(new ExceptionDiagnostic(aInputStreamOperation.failure()));
		}
	}

	@Override
	public @Nullable Operation<InputStream> sis() {
		return sois.get();
	}
}

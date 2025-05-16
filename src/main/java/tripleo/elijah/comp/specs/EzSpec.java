package tripleo.elijah.comp.specs;

import org.jetbrains.annotations.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.wrap.File;

import java.io.*;
import java.util.*;

public interface EzSpec {
	String file_name();

	File file();

	@Nullable Operation<InputStream> sis();

	@Override
	boolean equals(Object obj);

	@Override
	int hashCode();

	@Override
	String toString();

	default String file_name_string() {
		final String result = file().toString();

		assert Objects.equals(result, file().getWrappedFilename());

		return result; //getWrappedFilename(); // File.toString!!
	}
}

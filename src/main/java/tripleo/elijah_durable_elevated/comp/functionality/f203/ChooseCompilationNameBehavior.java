package tripleo.elijah_durable_elevated.comp.functionality.f203;

import org.jetbrains.annotations.*;
import tripleo.elijah_elevated_durable.comp.*;

import java.io.*;

public class ChooseCompilationNameBehavior implements ChooseDirectoryNameBehavior {
	private final EDL_ICompilation c;

	public ChooseCompilationNameBehavior(final EDL_ICompilation aC) {
		c = aC;
	}

	@Override
	public tripleo.wrap.@NotNull File chooseDirectory() {
		final String c_name = c.getCompilationNumberString();

		final File fn00 = new File("COMP", c_name);
//		final File fn0 = new File(fn00, "date");

		return tripleo.wrap.File.wrap(fn00);
	}
}

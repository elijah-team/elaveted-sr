package tripleo.elijah.javac_model.tools;

import tripleo.elijah.javac_model.lang.model.*;

import java.io.*;
import java.util.*;

public interface Tool {
	default String name() {
		return "";
	}

	int run(InputStream in, OutputStream out, OutputStream err, String... arguments);

	Set<SourceVersion> getSourceVersions();

}

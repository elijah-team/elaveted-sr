package tripleo.elijah.javac_model.tools;

import tripleo.elijah.javac_model.lang.model.element.*;

import java.util.*;

public interface JavaFileObject extends FileObject {

	Kind getKind();

	boolean isNameCompatible(String simpleName, Kind kind);

	NestingKind getNestingKind();

	Modifier getAccessLevel();

	enum Kind {
		SOURCE(".java"),

		CLASS(".class"),

		HTML(".html"),

		OTHER("");
		public final String extension;

		Kind(String extension) {
			this.extension = Objects.requireNonNull(extension);
		}
	}

}

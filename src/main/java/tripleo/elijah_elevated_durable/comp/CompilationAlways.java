package tripleo.elijah_elevated_durable.comp;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.i.*;

public enum CompilationAlways {
	;

	@NotNull
	public static String defaultPrelude() {
		return "c";
	}

	public enum Tokens {
		;

		// README 10/20 Disjointed needs annotation
		//  12/04 ServiceLoader
		public static final DriverToken COMPILATION_RUNNER_FIND_STDLIB2 = DriverToken.makeToken("COMPILATION_RUNNER_FIND_STDLIB2");
		public static final DriverToken COMPILATION_RUNNER_START        = DriverToken.makeToken("COMPILATION_RUNNER_START");
	}
}

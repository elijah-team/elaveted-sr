package tripleo.elijah.comp;

import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.wrap.*;

public interface USE {
	ElijahCache getElijahCache();

	Operation2<OS_Module> findPrelude(String prelude_name);

	void logProgress(CompProgress aCompProgress, String aAbsolutePath);

	void use(@NotNull CompilerInstructions compilerInstructions);

	Operation2<OS_Module> parseElijjahFile(@NotNull File f,
										   @NotNull String file_name,
										   @NotNull LibraryStatementPart lsp);
}

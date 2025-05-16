package tripleo.elijah.comp;

import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.queries.*;
import tripleo.elijah_fluffy.util.*;

public interface CompilerInput {
	void accept_ci(Maybe<ILazyCompilerInstructions> compilerInstructionsMaybe);

	void accept_hash(String hash);

	Maybe<ILazyCompilerInstructions> acceptance_ci();

	void certifyRoot();

	tripleo.wrap.File getDirectory();

	void setDirectory(tripleo.wrap.File f);

	boolean isElijjahFile();

	boolean isEzFile();

	boolean isNull();

	boolean isSourceRoot();

	void setArg();

	void setDirectoryResults(CompilerInstructions_Result aLoci);

	void setMaster(CompilerInputMaster master);

	void setSourceRoot();

	@Override
	String toString();

	Ty ty();

	tripleo.wrap.File makeFile();

	tripleo.wrap.File getFile();

	String getInp();

	tripleo.wrap.File getFileForDirectory();

	CompilerInstructions_Result getDirectoryResults();

	Object getExt(Class<?> aClass);

	void putExt(Class<?> aClass, Object o);

	enum CompilerInputField {
		TY, HASH, ACCEPT_CI, DIRECTORY_RESULTS
	}

	enum Ty {
		NULL, /* as is from command line/ */
		SOURCE_ROOT,
		ROOT, /* the base of the compilation */
		ARG, /* represents a compiler change (CC) */
		STDLIB
	}
}

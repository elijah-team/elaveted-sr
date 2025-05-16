package tripleo.elijah.ci;

import tripleo.elijah.comp.*;
import tripleo.elijah.compiler_model.*;
import tripleo.elijah_durable_elevated.nextgen.inputtree.*;
import tripleo.elijah_fluffy.xlang.*;
import tripleo.wrap.*;

import java.util.List;
import java.util.Optional;

public interface CompilerInstructions {
	void add(GenerateStatement generateStatement);

	void add(LibraryStatementPart libraryStatementPart);

	List<LibraryStatementPart> getLibraryStatementParts();

	Optional<String> genLang();  // not a promise? Calculated? C<O<S>>>??

	//CM_Filename getFilename();
	//
	//void setFilename(CM_Filename filename);

	String getName();

	void setName(LocatableString name);

	LocatableString getLocatableName();

	CiIndexingStatement indexingStatement();

	List<LibraryStatementPart> _lsps();

	String getFilename();

	void setFilename(String aString);

	void advise(CompilerInput aAdvisement);

	tripleo.wrap.File makeFile();

	default void setFilename(CM_Filename aCMFilename) {
		setFilename(aCMFilename.getString());
	}

	void addUnderstandingListener(Class<EIT_ModuleInputImpl.LangOfModule> aLangOfModuleClass, EIT_ModuleInputImpl.UnderstandingListener aUnderstandingListener);

	interface CompilerInstructionsBuilder {
		CompilerInstructions build();

		void add(GenerateStatement generateStatement);

		void add(LibraryStatementPart libraryStatementPart);

		void setGenLang(String aGenLangString);  // ??

		//void setFilename(CM_Filename filename);

		CiIndexingStatement createIndexingStatement();

		void setName(LocatableString name);
	}
}

package tripleo.elijah.contexts;

import tripleo.elijah.comp.Compilation;
import tripleo.elijah.lang.i.*;

public interface IImportContext {

	Compilation compilation();

	Context getParent();

	LookupResultList lookup(String name, int level, LookupResultList Result, ISearchList alreadySearched, boolean one);

	OS_Module module();

}

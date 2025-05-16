package tripleo.elijah.contexts;

import tripleo.elijah.lang.i.*;

public interface IFuncExprContext extends IFunctionContext {

	Context getParent();

	LookupResultList lookup(String name, int level, LookupResultList Result, ISearchList alreadySearched, boolean one);

}

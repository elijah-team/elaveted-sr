package tripleo.graph;

import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah_elevated_durable.lang_model.*;
import tripleo.paths.*;

public interface CK_ObjectTree {
	void asseverate(Object o, Asseverate asseveration);

	void asseverate(Asseveration asseveration);

	EIT_InputTree getInputTree();

	EIT_ModuleList getModuleList();

	/// fixme I am guessing that this needs attention
	void addSystemNode(String aPath, Object aObject);
}

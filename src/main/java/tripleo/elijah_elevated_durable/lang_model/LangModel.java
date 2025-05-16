package tripleo.elijah_elevated_durable.lang_model;

import tripleo.elijah.lang.i.*;
import tripleo.elijah_durable_elevated.lang.impl.*;

public interface LangModel {
	void assertUnderstanding(OS_Element aElement, EN_Understanding aUnderstanding);

	void assertUnderstanding(EN_Name aName, EN_Understanding aUnderstanding);

	EN_Name createName(String aName, OS_NamedElement aElement);

	void register(AliasStatementImpl aAliasStatement);
}

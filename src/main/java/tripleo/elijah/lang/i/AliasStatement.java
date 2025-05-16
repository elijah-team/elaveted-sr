package tripleo.elijah.lang.i;

import tripleo.elijah_elevated_durable.lang_model.*;

public interface AliasStatement extends OS_Element, ModuleItem, ClassItem, FunctionItem, OS_NamedElement {
	Qualident getExpression();

	@Override
	void serializeTo(SmallWriter sw);

	void setExpression(Qualident aXy);

	void setName(IdentExpression aI1);

	void resolveLangModel(LangModel aEDLLangModel);
}

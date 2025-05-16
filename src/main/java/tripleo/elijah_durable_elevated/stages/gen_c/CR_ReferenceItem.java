package tripleo.elijah_durable_elevated.stages.gen_c;

import tripleo.elijah.nextgen.outputstatement.*;
import tripleo.elijah_durable_elevated.stages.gen_fn.*;
import tripleo.elijah_durable_elevated.stages.instructions.*;
import tripleo.elijah_fluffy.util.*;

public interface CR_ReferenceItem {
	String getArg();

	CReference.Connector getConnector();

	GenerateC_Item getGenerateCItem();

	InstructionArgument getInstructionArgument();

	Eventual<GenerateC_Item> getPrevious();

	CReference.Reference getReference();

	Operation2<EG_Statement> getStatement();

	BaseTableEntry getTableEntry();

	String getText();

	void setArg(String aArg);

	void setConnector(CReference.Connector aConnector);

	void setGenerateCItem(GenerateC_Item aGenerateCItem);

	void setInstructionArgument(InstructionArgument aInstructionArgument);

	void setPrevious(Eventual<GenerateC_Item> aPrevious);

	void setReference(CReference.Reference aReference);

	void setStatement(Operation2<EG_Statement> aStatement);

	void setText(String aText);
}

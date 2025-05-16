package tripleo.elijah_elevated_durable.names_impl;

import tripleo.elijah_durable_elevated.stages.gen_fn.*;
import tripleo.elijah_durable_elevated.stages.instructions.*;
import tripleo.elijah_elevated_durable.lang_model.*;

public class EN_DeduceUsage implements EN_Usage {
	private final InstructionArgument backlink;
	private final BaseEvaFunction     evaFunction;
	private       BaseTableEntry      bte;

	public EN_DeduceUsage(final InstructionArgument aBacklink, BaseEvaFunction evaFunction,
			BaseTableEntry aTableEntry) {
		backlink = aBacklink;
		this.evaFunction = evaFunction;
		this.bte = aTableEntry;
	}

	public BaseTableEntry getBte() {
		return bte;
	}

	public void setBte(BaseTableEntry aBte) {
		bte = aBte;
	}
}

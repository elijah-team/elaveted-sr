package tripleo.elijah_elevated_durable.comp;

import org.jetbrains.annotations.*;
import tripleo.elijah.compiler_model.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah_durable_elevated.nextgen.inputtree.*;
import tripleo.elijah_elevated_durable.backbone.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.graph.*;
import tripleo.paths.*;

public class EDL_ObjectTree implements CK_ObjectTree {
	private final EDL_Compilation compilation;
	private final EIT_ModuleList  moduleList;

	public EDL_ObjectTree(final EDL_Compilation aCompilation) {
		compilation = aCompilation;
		moduleList  = new EIT_ModuleList_();
	}

	@Override
	public void asseverate(Object o, @NotNull Asseverate asseveration) {
		switch (asseveration) {
		case CI_SPECCED -> {
			final LCM_Just__CI_SPECCED event = new LCM_Just__CI_SPECCED(((CM_Ez) o).spec());
			compilation.lcm().push(event);
		}
		case CI_PARSED ->  {
			final LCM_Just__CI_PARSED event = new LCM_Just__CI_PARSED(((CM_Ez)o).spec());
			compilation.lcm().push(event);
		}
		case CI_HASHED -> {
			final CM_Ez t = (CM_Ez) o;
			final LCM_Just__CI_HASHED event = new LCM_Just__CI_HASHED(t.spec(), Operation.success(t.hash()), t.sourceFile(), compilation);
			compilation.lcm().push(event);
		}
		case CI_CACHED, EZ_PARSED -> {
			throw new NotImplementedException("Unimplemented case: " + asseveration);
		}
		case ELIJAH_PARSED -> {
			final LCM_Just__ELIJAH_PARSED event = new LCM_Just__ELIJAH_PARSED((CM_Module) o, compilation);
			compilation.lcm().push(event);
		}
		default -> {
			//NotImplementedException.raise_stop();
			throw new IllegalArgumentException("Unexpected value: " + asseveration);
		}
		}
	}

	private CompilationEnclosure getCompilationEnclosure() {
		return this.compilation.getCompilationEnclosure();
	}

	@Override
	public void asseverate(final Asseveration asseveration) {
		asseveration.onLogProgress(compilation.getCompilationEnclosure());
	}

	@Override
	public EIT_InputTree getInputTree() {
		return compilation.getInputTree();
	}

	@Override
	public EIT_ModuleList getModuleList() {
		return moduleList;
	}

	@Override
	public void addSystemNode(final String aPath, final Object aObject) {
		throw new UnintendedUseException("good. good.");
	}
}

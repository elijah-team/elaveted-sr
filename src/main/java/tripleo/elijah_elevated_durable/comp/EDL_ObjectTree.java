package tripleo.elijah_elevated_durable.comp;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.compiler_model.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah_durable_elevated.nextgen.inputtree.*;
import tripleo.elijah_elevated_durable.backbone.*;
import tripleo.elijah_fluffy.anno.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.graph.*;
import tripleo.paths.*;

public class EDL_ObjectTree implements CK_ObjectTree , Postable {
	private @ElLateInit EDL_Compilation compilation;
	private @ElLateInit  EIT_ModuleList  moduleList;

	public EDL_ObjectTree(final EDL_Compilation aCompilation) {
		compilation = aCompilation;
		moduleList  = new EIT_ModuleList_();
	}

	public EDL_ObjectTree() {
		// NOTE Relies on post
	}

	@Override
	public void asseverate(Object o, @NotNull Asseverate asseveration) {
		checkDefault();
		switch (asseveration) {
		case CI_SPECCED -> {
			final EzSpec               t     = ((CM_Ez) o).spec();
			final LCM_Just__CI_SPECCED event = new LCM_Just__CI_SPECCED(t);
			compilation.lcm().push(event);
		}
		case CI_PARSED -> {
			final EzSpec              t     = ((CM_Ez) o).spec();
			final LCM_Just__CI_PARSED event = new LCM_Just__CI_PARSED(t);
			compilation.lcm().push(event);
		}
		case CI_HASHED -> {
			final CM_Ez               t     = (CM_Ez) o;
			final LCM_Just__CI_HASHED event = LCM_Just__CI_HASHED.create(t, compilation);
			compilation.lcm().push(event);
		}
		case CI_CACHED, EZ_PARSED -> {
			throw new NotImplementedException("Unimplemented case: " + asseveration);
		}
		case ELIJAH_PARSED -> {
			final CM_Module               t     = (CM_Module) o;
			final LCM_Just__ELIJAH_PARSED event = new LCM_Just__ELIJAH_PARSED(t, compilation);
			compilation.lcm().push(event);
		}
		default -> {
			//NotImplementedException.raise_stop();
			throw new IllegalArgumentException("Unexpected value: " + asseveration);
		}
		}
	}

	private void checkDefault() {
		// fixme: no guava w/o lsp
		assert (null != compilation);
		assert (null != moduleList);
	}

	@Override
	public void asseverate(final Asseveration asseveration) {
		checkDefault();
		asseveration.onLogProgress(compilation.getCompilationEnclosure());
	}

	@Override
	public EIT_InputTree getInputTree() {
		checkDefault();
		return compilation.getInputTree();
	}

	@Override
	public EIT_ModuleList getModuleList() {
		checkDefault();
		return moduleList;
	}

	@Override
	public void addSystemNode(final String aPath, final Object aObject) {
		checkDefault();
		//throw new UnintendedUseException("good. good.");
		System.err.println(String.format("[TRACE] addSystemNode:: path: %s object: %s ", aPath, aObject));
	}

	private CompilationEnclosure getCompilationEnclosure() {
		checkDefault();
		return this.compilation.getCompilationEnclosure();
	}

	@Override
	public void accept(final Compilation aCompilation) {
		if (aCompilation instanceof EDL_Compilation) {
			compilation = (EDL_Compilation) aCompilation;
			if (compilation.hasClojureSupport()) {}else {
				moduleList  = new EIT_ModuleList_();
			}
		} else {
			throw new AssertionError();
		}
	}
}

package tripleo.elijah_elevated_durable.compiler_model;

import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.nextgen.inputtree.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.compiler_model.*;
import tripleo.elijah.g.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.world.i.*;
import tripleo.elijah_durable_elevated.world.i.LivingRepo;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_fluffy.util.*;

import java.io.*;

public class EDL_CM_Module implements CM_Module {
	private final Eventual<EDL_ICompilation> compilationP = new Eventual<>();
	private final Eventual<OS_Module>        moduleP      = new Eventual<>();
	private       Operation2<OS_Module>      moduleOperation;
	private       ElijahSpec                 spec;
	private       WorldModule                worldModule;
	private       EDL_ICompilation           compilation;
	private       EIT_ModuleInput            moduleInput;

	public EDL_CM_Module() {
		this.compilationP.then(Scomp -> {
			moduleP.then((final OS_Module Smodule) -> {
				worldModule = Scomp.con().createWorldModule(Smodule);
			});
		});
	}

	@Override
	public void advise(final ElijahSpec aSpec) {
		spec = aSpec;
	}

	@Override
	public void advise(final Operation2<OS_Module> aModuleOperation) {
		if (moduleOperation == null) {
			moduleOperation = aModuleOperation;
			moduleP.resolve(aModuleOperation.success());
		} else {
			if (aModuleOperation.success() == moduleOperation.success()) {
				assert aModuleOperation.success() != null; // specify verify
				// Assume first branch has evaluated already...
				// FIXME ... so why again
				//  there's a thing for that (___)
			} else {
				System.err.println("240930-0043 mo is not null " + this + " and asking for "+aModuleOperation.success());
			}
		}
	}

	@Override
	public GWorldModule adviseCreator(final Compilation aCompilation) {
		if (this.compilation == null) {
			this.compilation = (EDL_ICompilation) aCompilation;
			this.compilationP.resolve(this.compilation);
		}

		return worldModule;
	}

	@Override
	public void adviseWorld(final GLivingRepo aWorld) {
		((LivingRepo) aWorld).addModule2(worldModule);
	}

	@Override
	public void advise(final LibraryStatementPart aLsp) {
		final OS_Module mm = worldModule.module();

		if (mm.getLsp() != null) {
			assert false;
		} else {
			mm.setLsp(aLsp);
		}
	}

	@Override
	public void advise(final PreludeProvider preludeProvider) {
		final OS_Module             mm      = worldModule.module();
		final Operation2<OS_Module> pl      = preludeProvider.getOperation();

		// NOTE Go. infectious. tedious. also slightly lazy
		if (pl.mode() == Mode.SUCCESS) {
			final OS_Module prelude = pl.success();
			mm.setPrelude(prelude);
		} else {
			throw new ProgramIsWrongIfYouAreHere("PreludeProvider:!->failure: "+pl.failure());
		}
	}

	@Override
	public InputStream s() {
		throw new UnintendedUseException();
	}

	@Override
	public void set(final OS_Module aModule) {

	}

	@Override
	public EIT_ModuleInput getEITInput() {
		if (this.moduleInput == null) {
			if (this.compilation != null) {
				// FIXME 12/07 assuming these two are together
				this.moduleInput = this.compilation.con().createModuleInput(this.worldModule.module());
			}
		}
		return this.moduleInput;
	}

	@Override
	public String toString() {
		final String moduleOperationS;
		if (moduleOperation != null) {
			moduleOperationS = String.format("%s %s", moduleOperation.mode(), moduleOperation.success());
		} else {
			moduleOperationS = "NULL";
		}

		return "CM_Module_{" +
				"moduleOperation=" + moduleOperationS +
				", spec=" + spec +
				", worldModule=" + worldModule +
				'}';
	}
}

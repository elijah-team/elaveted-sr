package tripleo.elijah_elevated_durable.comp;

import clojure.lang.*;
import org.jdeferred2.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.g.*;
import tripleo.elijah_durable_elevated.comp.*;
import tripleo.elijah_durable_elevated.comp.internal.*;
import tripleo.elijah_elevated_durable.backbone.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.elijah_prolific.v.*;
import tripleo.graph.*;

import java.util.*;

public class EDL_CompilerController implements CompilerController {
	private final ICompilationAccess3    ca3;

	private final List<EventualRegister> allRegisters = new ArrayList<>();
	ICompilationBus     cb;
	List<CompilerInput> inputs;
	private       EDL_ICompilation         c;
	private final Eventual<IPersistentMap> configP =new Eventual<>();

	public EDL_CompilerController(final ICompilationAccess3 aCa3) {
		ca3 = aCa3;
	}

	public void _setInputs(final Compilation aCompilation, final List<CompilerInput> aInputs) {
		c      = (EDL_ICompilation) aCompilation;
		inputs = aInputs;
	}

	@Override
	public void setEnclosure(final GCompilationEnclosure aCompilationEnclosure) {
		final CompilationEnclosure ce = (CompilationEnclosure) aCompilationEnclosure;
		_setInputs(ce.getCompilation(), ce.getCompilerInput());
	}

	@Override
	public void printUsage() {
		tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon.println_out_2("Usage: eljc [--showtree] [-sE|O] <directory or .ez file names>");
	}

	@Override
	public Operation<Ok> processOptions() {
		final OptionsProcessor             op                   = new ApacheOptionsProcessor();
		final CompilerInstructionsObserver cio                  = new CompilerInstructionsObserver(c);
		final CompilationEnclosure         compilationEnclosure = c.getCompilationEnclosure();

		compilationEnclosure.setCompilationAccess(c.con().createCompilationAccess());
		compilationEnclosure.setCompilationBus(cb = c.con().createCompilationBus());

		c._cis().set_cio(cio);

		return op.process(c, inputs, cb); // TODO 09/08 Make this more complicated
	}

	@Override
	public void runner() {
		runner(new _DefaultCon());
	}

	public void hook(final EDL_CompilationRunner aCr) {

	}

	@Override
	public void runner(final @NotNull Con con) {
		if (false) c.____m();

		c._cis().subscribeTo(c);

		final CompilationEnclosure ce = c.getCompilationEnclosure();

		final ICompilationAccess compilationAccess = ce.getCompilationAccess();
		assert compilationAccess != null;

		final ICompilationRunner icr = con.newCompilationRunner(compilationAccess);
		final EDL_CompilationRunner  cr  = (EDL_CompilationRunner) icr;

		ce.setCompilationRunner(cr);

		hook(cr);

//		var inputTree = c.getInputTree();
//
//		for (CompilerInput input : inputs) {
//			if (input.isNull()) // README filter out args
//				inputTree.addNode(input);
//		}

		cb.add(new EDL_CB_FindCIs(cr, inputs));
		cb.add(new EDL_CB_FindStdLibProcess(ce, cr));

//		for (CompilerInput input : inputs) {
//			input.
//		}

		((EDL_CompilationBus) cb).runProcesses();

		c.getFluffy().checkFinishEventuals();

		allRegisters.stream().map(r -> r.maybeCheckFinishEventuals());
		V.exit();
	}

	public static class _DefaultCon implements Con {
		@Override
		public EDL_CompilationRunner newCompilationRunner(final ICompilationAccess compilationAccess) {
			final CR_State          crState = new CR_State(compilationAccess);
			final EDL_CompilationRunner cr      = new EDL_CompilationRunner(compilationAccess, crState);

			crState.setRunner(cr);

			return cr;
		}
	}

	@Override
	public void addToAllRegisters(EventualRegister aEventualRegister) {
		allRegisters.add(aEventualRegister);
	}

	@Override
	public void setConfig(final @NotNull IPersistentMap aConfig) {
		this.configP.resolve(aConfig);
	}

	@Override
	public void onConfig(final DoneCallback<IPersistentMap> cb) {
		this.configP.then(cb);
	}

	@Override
	public CompilationInterfaceRevised revised() {
		return this.c.revised();
	}

	@Override
	public CompilationInterfaceRevised2 revised2() {
		return this.c.revised2();
	}

	@Override
	public int errorCount() {
		return this.c.errorCount();
	}
}

package tripleo.elijah_elevated_durable.comp;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.nextgen.inputtree.*;
import tripleo.elijah.comp.nextgen.pw.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.comp_model.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah.world.i.*;
import tripleo.elijah_durable_elevated.comp.i.*;
import tripleo.elijah_durable_elevated.comp.internal.*;
import tripleo.elijah_durable_elevated.comp.nextgen.*;
import tripleo.elijah_durable_elevated.comp.nextgen.pw.*;
import tripleo.elijah_durable_elevated.lang.impl.*;
import tripleo.elijah_durable_elevated.nextgen.inputtree.*;
import tripleo.elijah_durable_elevated.nextgen.outputtree.*;
import tripleo.elijah_durable_elevated.util.*;
import tripleo.elijah_durable_elevated.world.i.LivingRepo;
import tripleo.elijah_elevated_durable.world_impl.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.graph.*;
import tripleo.paths.*;

import java.util.*;

class EDL_CompFactory implements CompFactory {
	private final EDL_Compilation compilation;
	private       CM_UleLog       _log;

	public EDL_CompFactory(EDL_Compilation aCompilation) {
		compilation = aCompilation;
	}

	@Contract(" -> new")
	@Override
	public @NotNull ICompilationAccess createCompilationAccess() {
		return new EDL_CompilationAccess(compilation);
	}

	@Contract(" -> new")
	@Override
	public @NotNull ICompilationBus createCompilationBus() {
		return new EDL_CompilationBus(Objects.requireNonNull(compilation.getCompilationEnclosure()));
	}

	@Contract("_ -> new")
	@Override
	public @NotNull EIT_ModuleInput createModuleInput(final OS_Module aModule) {
		return new EIT_ModuleInputImpl(aModule, compilation);
	}

	@Contract("_ -> new")
	@Override
	public @NotNull Qualident createQualident(final @NotNull List<String> sl) {
		final Qualident R = new QualidentImpl();
		// README 10/13 avoid inclination to
		for (final String s : sl) {
			R.append(Helpers0.string_to_ident(s));
		}
		return R;
	}

	@Contract(" -> new")
	@Override
	public CK_ObjectTree createObjectTree() {
		return new EDL_ObjectTree(compilation);
	}

	@Contract("_ -> new")
	@Override
	public CY_ElijahSpecParser defaultElijahSpecParser(final ElijahCache elijahCache) {
		return new CY_ElijahSpecParser() {
			@Override
			public Operation2<OS_Module> parse(ElijahSpec spec) {
				final EDL_ICompilation      c  = compilation;
				final Operation2<OS_Module> om = CX_realParseElijjahFile2.realParseElijjahFile2(spec, elijahCache, c);
				return om;
			}
		};
	}

	@Override
	public WorldModule createWorldModule(OS_Module aModule) {
		return new DefaultWorldModule(aModule, compilation.getCompilationEnclosure());
	}

	@Override
	public PW_PushWorkQueue createWorkQueue() {
		short choice = 2;

		return (new PW_PushWorkQueue[]{
				new PW_PushWorkQueue__JC(),
				new PW_PushWorkQueue_Blocking(),
				new PW_PushWorkQueue_Concurrent()
		})[choice - 1];
	}

	@Override
	public Startable askConcurrent(final StartableI aRunnable) {
		final Thread thread = new Thread(aRunnable::run);
		thread.setName(aRunnable.getThreadName());
		return new Startable() {
			@Override
			public void start() {
				thread.start();
			}

			@Deprecated
			@Override
			public Thread stealThread() {
				return thread;
			}

			@Override
			public boolean isSignalled() {
				return false;
			}
		};
	}

	@Override
	public CM_UleLog getULog() {
		if (_log == null) {
			_log = new CM_UleLog() {
				@Override
				public void info(String string) {
					SimplePrintLoggerToRemoveSoon.println2(string);
				}

				@Override
				public void asv(final int code, final Object... aObjects) {
					SimplePrintLoggerToRemoveSoon.println2("{{ULE}} " + code + " " + Arrays.asList(aObjects));
				}
			};
		}
		return _log;
	}

	@Override
	public EOT_OutputTree createOutputTree() {
		return new EOT_OutputTreeImpl();
	}

	@Override
	public EIT_InputTree createInputTree() {
		return new EIT_InputTreeImpl();
	}

	@Override
	public CX_ParseElijahFile.ElijahSpecReader defaultElijahSpecReader(final CP_Path aLocalPrelude) {
		return new EDL_ElijahSpecReader(aLocalPrelude, compilation);
	}

	@NotNull
	@Override
	public CK_Monitor createCkMonitor() {
		return new EDL_CK_Monitor();
	}

	@NotNull
	@Override
	public PW_CompilerController createPwController(EDL_Compilation aCompilation) {
		return new PW_CompilerController(aCompilation);
	}

	@NotNull
	@Override
	public EDL_Finally createFinally() {
		return new EDL_Finally();
	}

	@NotNull
	@Override
	public LivingRepo getLivingRepo() {
		return new DefaultLivingRepo();
	}

	@Override
	public @NotNull CompilerInputMaster createCompilerInputMaster() {
		return new CompilerInputMaster() {
			private final List<CompilerInputListener> listeners = new ArrayList<>();

			@Override
			public void addListener(final CompilerInputListener compilerInputListener) {
				listeners.add(compilerInputListener);
			}

			@Override
			public void notifyChange(final CompilerInput compilerInput, final CompilerInput.CompilerInputField compilerInputField) {
				for (CompilerInputListener listener : listeners) {
					listener.baseNotify(compilerInput, compilerInputField);
				}
			}
		};
	}

	@Override
	public ILazyCompilerInstructions createLazyCompilerInstructions(final CompilerInput aCompilerInput) {
		return ILazyCompilerInstructions_.of(aCompilerInput, compilation.getCompilationClosure());
	}
}

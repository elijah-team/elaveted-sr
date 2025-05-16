package tripleo.elijah_elevated_durable.comp;

import lombok.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah_durable_elevated.*;
import tripleo.elijah_durable_elevated.comp.internal.*;
import tripleo.elijah_elevated_durable.*;
import tripleo.elijah_elevated_durable.backbone.*;
import tripleo.elijah_elevated_durable.compilation_bus.*;
import tripleo.elijah_fluffy.util.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.*;

@SuppressWarnings("CommentedOutCode")
public class EDL_CompilationBus implements ICompilationBus {
	private final          CB_Monitor     _monitor;
	@Getter
	private final @NotNull CompilerDriver _compilerDriver;
	private final @NotNull IProgressSink  _defaultProgressSink;
	private final @NotNull EDL_ICompilation  c;
	private final @NotNull Queue<CB_Process> pq;
	private final @NotNull List<CB_Process>  alreadyP;

	public EDL_CompilationBus(final @NotNull CompilationEnclosure ace) {
		c                    = (@NotNull EDL_ICompilation) ace.getCompilationAccess().getCompilation();
		pq                   = new ConcurrentLinkedQueue<>();
		alreadyP             = new ArrayList<>();
		_monitor             = new EDL_CompilationRunner.__CompRunner_Monitor();
		_defaultProgressSink = new EDL_ProgressSink();
		_compilerDriver      = new EDL_CompilerDriver(this);
		ace.setCompilerDriver(_compilerDriver);
	}

	@Override
	public void add(final @NotNull CB_Action action) {
		System.err.println("DefaultCompilationBus::add (Action) "+action.name());
		pq.add(new SingleActionProcess(action, "default processName CB_FindStdLibProcess"));
	}

	@Override
	public void add(final @NotNull CB_Process aProcess) {
		System.err.println("DefaultCompilationBus::add (Process) "+aProcess.name());
		pq.add(aProcess);
	}

	@Override
	public IProgressSink defaultProgressSink() {
		return _defaultProgressSink;
	}

	@SuppressWarnings("SuspiciousGetterSetter")
	@Override
	public CompilerDriver getCompilerDriver() {
		// 24/01/04 back and forth
		return this._compilerDriver;
	}

	@Override
	public void inst(final @NotNull ILazyCompilerInstructions aLazyCompilerInstructions) {
		_defaultProgressSink.note(IProgressSink.Codes.LazyCompilerInstructions_inst,
				ProgressSinkComponent.CompilationBus_, -1, new Object[] { aLazyCompilerInstructions.get() });
	}

	@Override
	public void option(final @NotNull CompilationChange aChange) {
		aChange.apply(c);
	}

	@Override
	public List<CB_Process> processes() {
		return pq.stream().toList();//_processes;
	}

	@SuppressWarnings("SuspiciousGetterSetter")
	@Override
	public CB_Monitor getMonitor() {
		return _monitor;
	}

	@Override // eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
	public void addCompilerChange(Class<?> class1) {
		if (class1.isInstance(CompilationChange.class)) {
			try {
				final CompilationChange compilationChange = (CompilationChange) class1
						.getDeclaredConstructor(new Class[] {}).newInstance();
				c.getCompilationEnclosure().getCompilationBus().option(compilationChange);
			} catch (InstantiationException | IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				throw new Error();
			}
		}
	}

	@Override
	public void runOneProcess(final CB_Process aP) {
		throw new UnintendedUseException("integration error.");
	}

	@SuppressWarnings("SpellCheckingInspection")
	public void runProcesses() {
		final DCB_Startable s   = new DCB_Startable(this, pq, _defaultProgressSink);

		//try {
			final Startable task = this.c.con().askConcurrent(s);

			if (DebugFlags.isDebugging()) {
				task.start();
				System.err.println("240923-0107 Wait a little (Awaitility) in DCB");
				//await()
				//		//.atMost(5000, TimeUnit.SECONDS)
				//		.atMost(1, TimeUnit.SECONDS)
				//		.until(task::isSignalled);
				try {
					//noinspection MagicNumber
					Thread.sleep(5000);
				} catch (InterruptedException aE) {
					System.err.println("9997-119 Thread interrupted");
				}
            } else {
				task.start();
				//await().atMost(250, TimeUnit.MILLISECONDS).until(task::isSignalled);
			}
		//} catch (ConditionTimeoutException cte) {
		//	//throw new RuntimeException(cte);
		//	System.err.println("9997-109 Awaitility timeout in DCB");
		//}

		for (final CB_Process process : pq) {
			logProgress(INTEGER_MARKER_CODES.DEFAULT_COMPILATION_BUS__RUN_PROCESS__EXECUTE_LOG, process.name());
			execute_process(this, process);
		}
	}

	private void logProgress(@SuppressWarnings("SameParameterValue") final int code, final String message) {
		SimplePrintLoggerToRemoveSoon.println_out_4(code + " " + message);
	}

	private void execute_process(final EDL_CompilationBus ignoredADefaultCompilationBus, final CB_Process aProcess) {
		// CompilationUnitTree
		// Compilation.Cheat.executeCB_Action(aProcess);
		if (alreadyP.contains(aProcess))
			throw new Error();
		alreadyP.add(aProcess);
	}
}

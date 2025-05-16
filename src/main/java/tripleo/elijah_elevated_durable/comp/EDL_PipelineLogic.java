/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_elevated_durable.comp;

import com.google.common.collect.*;
import io.reactivex.rxjava3.annotations.*;
import org.jdeferred2.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.logging.*;
import tripleo.elijah.stages.logging.ElLog.*;
import tripleo.elijah.world.i.*;
import tripleo.elijah_durable_elevated.comp.i.extra.*;
import tripleo.elijah_durable_elevated.comp.internal.*;
import tripleo.elijah_durable_elevated.stages.deduce.*;
import tripleo.elijah_durable_elevated.stages.gen_fn.*;
import tripleo.elijah_elevated_durable.backbone.*;
import tripleo.elijah_elevated_durable.comp_notation.*;
import tripleo.elijah_elevated_durable.world_impl.*;
import tripleo.elijah_fluffy.diagnostic.*;
import tripleo.elijah_fluffy.util.*;

import java.util.*;
import java.util.function.*;

/**
 * Created 12/30/20 2:14 AM
 */
public class EDL_PipelineLogic implements PipelineLogic {
	public final @NotNull  GeneratePhase            generatePhase;
	public final @NotNull  DeducePhase              dp;
	final @NonNull         ModMap                   modMap     = new ModMap();
	private final          ICompilationAccess       ca;
	private final @NonNull ModuleCompletableProcess mcp        = new ModuleCompletableProcess();
	private final @NonNull EDL_IPipelineAccess pa;
	private final          List<Eventual<?>>   _eventuals = new ArrayList<>();
	//	private final @NonNull EIT_ModuleList           mods   = new EIT_ModuleList();
	public                 GDM_Pipeline      pl         = new GDM_Pipeline() {
		@Override
		public GDM_Product submit(final Object aMod) {
			final EDL_PipelineLogic pipelineLogic = EDL_PipelineLogic.this;

			CompletableProcess<GDP_Module> result = null;
			if (aMod instanceof WorldModule wm) {
				final WorldModule   mod = wm;
				final List<EvaNode> lgc = new ArrayList<>();
				pipelineLogic.dp.deduceModule(mod,
											  Collections.unmodifiableList(lgc),
											  pipelineLogic.getVerbosity());
				result = new GDP_ModuleCompletableProcess(lgc);
				return GDM_Product.of(result);
			}
			return null; // maybe null/void cp??
		}
	};

	public EDL_PipelineLogic(final IPipelineAccess aPa, final @NotNull ICompilationAccess ca0) {
		pa = (EDL_IPipelineAccess) aPa;

		// TODO annotation time, or use clj
		pa.install_notate(Provenance.PipelineLogic__nextModule, GN_PL_Run2.class, GN_PL_Run2_Env.class);

		this.ca = ca0;
		ca.setPipelineLogic(this);
		generatePhase = new GeneratePhase(ca.testSilence(), pa, this);
		dp            = new DeducePhase(ca, pa, this);

		pa.getCompilationEnclosure().addModuleListener(new PL_ModuleListener(this, pa));

		ca.getCompilation().addToAllRegisters(this);
	}

	@Override
	public Verbosity getVerbosity() {
		// 24/01/04 back and forth
		return ca.testSilence();
	}

	@Override
	public ModuleCompletableProcess _mcp() {
		return mcp;
	}

	@Override
	public @NonNull IPipelineAccess _pa() {
		return pa;
	}

	@Override
	public void addLog(ElLog aLog) {
		_pa().addLog(aLog);
	}

	@Override
	public void checkFinishEventuals() {
		int y = 0;
		for (Eventual<?> eventual : _eventuals) {
			if (eventual.isResolved()) {
			} else {
				tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon.println_err_4("[PipelineLogic::checkEventual] failed for " + eventual.description());
			}
		}
	}

	@Override
	public @NotNull String _host() {
		return "PIPELINE-LOGIC";
	}

	@Override
	@NotNull
	public GenerateFunctions getGenerateFunctions(@NotNull OS_Module mod) {
		return generatePhase.getGenerateFunctions(mod);
	}

	//@Override
	public Eventual<DeducePhase.GeneratedClasses> handle(final GN_PL_Run2.@NotNull GenerateFunctionsRequest rq) {
		final OS_Module          mod = rq.mod();
		final DefaultWorldModule wm  = rq.worldModule();

		assert wm != null;

		final Eventual<DeducePhase.GeneratedClasses> p = new Eventual<>();
		p.register(this);
		modMap.put(mod, p);

		return p;
	}

	@Override
	public <P> void register(final Eventual<P> e) {
		_eventuals.add(e);
	}

	@Override
	public Operation<Ok> maybeCheckFinishEventuals() {
		assert false;
		return null;
	}

	class ModMap {
		private final Map<OS_Module, Eventual<DeducePhase.GeneratedClasses>>                    modMap = new HashMap<>();
		private final Multimap<OS_Module, DoneCallback<Eventual<DeducePhase.GeneratedClasses>>> mmme   = ArrayListMultimap
				.create();

		public void put(OS_Module mod, Eventual<DeducePhase.GeneratedClasses> p) {
			boolean y = modMap.get(mod) == null;
			modMap.put(mod, p);
			if (!y) return;
			var x = mmme.get(mod);
			for (DoneCallback<Eventual<DeducePhase.GeneratedClasses>> callback : x) {
				callback.onDone(p);
			}
		}

		public void then(OS_Module mod, DoneCallback<Eventual<DeducePhase.GeneratedClasses>> p) {
			mmme.put(mod, p);

			var x = modMap.get(mod);
			if (x != null) {
				p.onDone(x);
			} else {
				var e = new Eventual<DeducePhase.GeneratedClasses>();
				e.then(lgc -> p.onDone(e));
				//modMap.put(mod, e);
				put(mod, e);
			}
		}
	}

	public final class ModuleCompletableProcess implements CompletableProcess<WorldModule> {
		@Override
		public void add(final @NotNull WorldModule aWorldModule) {
			{
				var mod = aWorldModule.module();
				System.err.printf("7070 %s %d%n", mod.getFileName(), mod.entryPoints().size());
			}

			final CompilationEnclosure  ce            = pa.getCompilationEnclosure();
			final Consumer<WorldModule> worldConsumer = ce::noteAccept; // FIXME not data...
			final GN_PL_Run2_Env        pl_run2       = new GN_PL_Run2_Env(EDL_PipelineLogic.this, aWorldModule, ce, worldConsumer);

			pa.notate(Provenance.PipelineLogic__nextModule, pl_run2);
		}

		@Override
		public void complete() {
			dp.finish();
		}

		@Override
		public void error(final Diagnostic d) {
//			throw new UnintendedUseException();
		}

		@Override
		public void preComplete() {

		}

		@Override
		public void start() {

		}
	}

}

//
//
//

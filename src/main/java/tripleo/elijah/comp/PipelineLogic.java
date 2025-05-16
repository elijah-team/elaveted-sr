package tripleo.elijah.comp;

import io.reactivex.rxjava3.annotations.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.g.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.logging.*;
import tripleo.elijah_durable_elevated.comp.i.extra.*;
import tripleo.elijah_durable_elevated.stages.deduce.*;
import tripleo.elijah_durable_elevated.stages.gen_fn.*;
import tripleo.elijah_durable_elevated.util.*;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_elevated_durable.comp_notation.*;
import tripleo.elijah_fluffy.diagnostic.*;
import tripleo.elijah_fluffy.util.*;

import java.util.*;

public interface PipelineLogic extends EventualRegister, GPipelineLogic {
	ElLog.Verbosity getVerbosity();

	EDL_PipelineLogic.ModuleCompletableProcess _mcp();

	@NonNull
	IPipelineAccess _pa();

	void addLog(ElLog aLog);

	@Override
	void checkFinishEventuals();

	@NotNull
	GenerateFunctions getGenerateFunctions(@NotNull OS_Module mod);

	Eventual<DeducePhase.GeneratedClasses> handle(GN_PL_Run2.@NotNull GenerateFunctionsRequest rq);

	@Override
	<P> void register(Eventual<P> e);

	public interface GDM_Pipeline {
		GDM_Product submit(final Object aMod);
	}

	public interface GDM_Product {
		static GDM_Product of(Object aGDPModuleCompletableProcess) {
			return new GDM_Product() {
				@Override
				public List<EvaNode> getNodes() {
					if (aGDPModuleCompletableProcess instanceof GDP_ModuleCompletableProcess gdp)
						return gdp.getNodes();
					else return Helpers0.List_of();
				}

				@Override
				public CompletableProcess<GDP_Module> getProcess() {
					if (aGDPModuleCompletableProcess instanceof GDP_ModuleCompletableProcess gdp)
						return gdp;
					else return null;
				}
			};
		}

		List<EvaNode> getNodes();

		CompletableProcess<PipelineLogic.GDP_Module> getProcess();
	}

	public interface GDP_Module {
		OS_Module getSource();

		// TODO 24/01/22  need EvaModule?

		//promise??
		List<EvaNode> getGeneratedProducts();

		List<EvaNode> getDeducedProducts();
	}

	public static class GDP_ModuleCompletableProcess implements CompletableProcess<GDP_Module> {
		private final List<EvaNode> nodes; // you won!!

		public GDP_ModuleCompletableProcess(List<EvaNode> aNodes) {
			nodes = aNodes;
		}

		@Override
		public void add(final GDP_Module item) {
			throw new UnintendedUseException("not expected, remove after tests");
		}

		@Override
		public void complete() {
			throw new UnintendedUseException("not expected, remove after tests");
		}

		@Override
		public void error(final Diagnostic d) {
			throw new UnintendedUseException("not expected, remove after tests");
		}

		@Override
		public void preComplete() {
			throw new UnintendedUseException("not expected, remove after tests");
		}

		@Override
		public void start() {
			throw new UnintendedUseException("not expected, remove after tests");
		}

		public List<EvaNode> getNodes() {
			return this.nodes;
		}
	}
}

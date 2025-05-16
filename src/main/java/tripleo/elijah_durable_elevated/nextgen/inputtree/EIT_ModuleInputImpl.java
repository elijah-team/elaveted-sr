package tripleo.elijah_durable_elevated.nextgen.inputtree;

import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.nextgen.inputtree.*;
import tripleo.elijah.g.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.composable.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.nextgen.model.*;
import tripleo.elijah.world.i.*;
import tripleo.elijah_durable_elevated.comp.*;
import tripleo.elijah_durable_elevated.stages.deduce.nextgen.*;
import tripleo.elijah_durable_elevated.stages.gen_c.*;
import tripleo.elijah_durable_elevated.stages.gen_fn.*;
import tripleo.elijah_durable_elevated.stages.gen_generic.*;
import tripleo.elijah_durable_elevated.stages.gen_generic.pipeline_impl.*;
import tripleo.elijah_durable_elevated.work.*;
import tripleo.elijah_elevated_durable.backbone.*;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_elevated_durable.comp_notation.*;
import tripleo.elijah_elevated_durable.lang_model.*;
import tripleo.elijah_fluffy.util.*;

import java.util.*;
import java.util.function.*;

public class EIT_ModuleInputImpl implements EIT_ModuleInput {
	private final EDL_ICompilation c;
	private final OS_Module        module;

	@Contract(pure = true)
	public EIT_ModuleInputImpl(final OS_Module aModule, final EDL_ICompilation aC) {
		module = aModule;
		c      = aC;
	}

	@Override
	public @NotNull SM_Module computeSourceModel() {
		final SM_Module sm = new SM_Module() {
			@Override
			public @NotNull List<SM_ModuleItem> items() {
				final List<SM_ModuleItem> items = new ArrayList<>();
				for (final ModuleItem item : module.getItems()) {
					items.add(new SM_ModuleItem() {
						@Override
						public ModuleItem _carrier() {
							return item;
						}
					});
				}
				return items;
			}
		};
		return sm;
	}

	@Override
	public void doGenerate(final GModuleGenerationRequest r0) {
		final ModuleGenerationRequest rq = (ModuleGenerationRequest) r0;
		doGenerate(rq.getEvaNodeList(), rq.work(), rq.getGenerateResultConsumer(), rq.getCompilationEnclosure());
	}

	private void doGenerate(final List<EvaNode> nodes,
						   final WorkManager wm,
						   final @NotNull Consumer<GenerateResult> resultConsumer,
						   final @NotNull CompilationEnclosure ce) {
		{
			final LibraryStatementPart lsp  = module.getLsp();
			final CompilerInstructions ci   = lsp.getInstructions();
			final Optional<String>     s    = ci.genLang();

			ci.addUnderstandingListener(LangOfModule.class, new UnderstandingListener(){
				@Override
				public void onUnderstanding(final DR_Ident.Understanding aU, final EN_Understanding aENU) {
					throw new UnintendedUseException("implement me");
				}
			});
		}

		// 0. get lang
		final String lang = langOfModule();

		// 1. find Generator (GenerateFiles) eg. GenerateC final
		final WorldModule             mod = ce.getCompilation().world().findModule(module);
		final OutputFileFactoryParams p   = new OutputFileFactoryParams(mod, ce);

		var resultSink = new DefaultGenerateResultSink(c.pa());
		var gr         = new Old_GenerateResult();
		var wl         = new EDP_WorkList();
		var nodes1     = EvaPipeline.processLgc(nodes);
		var gnis_env   = new GN_GenerateNodesIntoSinkEnv(nodes1,
														 resultSink,
														 ce.getPipelineLogic().getVerbosity(),
														 gr,
														 ce);
		var gnis       = new GN_GenerateNodesIntoSink(gnis_env);
		var gmr        = new GM_GenerateModuleRequest(gnis, mod, gnis_env);
		var gmgm       = new GM_GenerateModule(gmr);
		var env        = new GenerateResultEnv(resultSink, gr, wm, wl, gmgm);

		final GenerateFiles           generateFiles = OutputFileFactory.create(lang, p, env);

		// 2. query results
		final GenerateResult gr2 = generateFiles.resultsFromNodes(nodes, wm, ((GenerateC) generateFiles).resultSink, env);

		// 3. #drain workManager -> README part of workflow. may change later as appropriate
		wm.drain();

		// 4. tail process results
		resultConsumer.accept(gr2);
	}

	private @Nullable String langOfModule() {
		final LibraryStatementPart lsp  = module.getLsp();
		final CompilerInstructions ci   = lsp.getInstructions();
		final Optional<String>     s    = ci.genLang();
		if (s.isPresent()) {
			return s.get();
		} else
			return null;
	}

	@Override
	public @NotNull EIT_InputType getType() {
		return EIT_InputType.ELIJAH_SOURCE;
	}

	public interface LangOfModule {
		String langString();
		boolean isPresent();
		IComposable location(); // ??
	}

	public interface UnderstandingListener {
		void onUnderstanding(DR_Ident.Understanding aU, EN_Understanding aENU);
	}
}

package tripleo.elijah_elevated_durable.comp;

import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah_durable_elevated.comp.internal.*;
import tripleo.elijah_elevated_durable.backbone.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.elijah_potential.comp.xxinternal.*;
import tripleo.graph.*;

import java.util.*;

import static tripleo.elijah_fluffy.util.Helpers.*;

public class EDL_CD_CompilationRunnerStart implements CD_CompilationRunnerStart {
	private       CK_Steps        steps;
	private final List<CK_Action> stepActions = new ArrayList<>();

	private List<Operation<Ok>>     crActionResultList;

	private CK_AbstractStepsContext stepContext;

	@Override
	public void start(final @NotNull CompilerInstructions aRootCI,
					  final @NotNull CR_State crState,
					  final @NotNull CB_Output out) {
		//final @NotNull CK_Monitor monitor = compilationEnclosure.getDefaultMonitor();

		stepContext = new CD_CRS_StepsContext(crState, out);

		// TODO 10/20 remove k2 ??
		final CK_ProcessInitialAction k2 = new CK_ProcessInitialAction(aRootCI);
		final CK_AlmostComplete       k3 = new CK_AlmostComplete();
		final CK_RunBetterAction      k4 = new CK_RunBetterAction();

		stepActions.addAll(List_of(k2, k3, k4));

		final List<CR_Action> crActionList = new ArrayList<>();

		for (final CR_Action action : crActionList) {
			stepActions.add(new EDL_CD_CRS_CK_Action(this, action));
		}

		steps = () -> stepActions;

		crActionResultList = new ArrayList<>(crActionList.size());

		final @NotNull CompilationEnclosure compilationEnclosure = crState.getCompilationEnclosure();
		compilationEnclosure.runStepsNow(steps, stepContext);
	}

	public List<Operation<Ok>> getCrActionResultList() {
		// 24/01/04 back and forth
		return this.crActionResultList;
	}

	public CK_AbstractStepsContext getStepContext() {
		// antilombok
		return this.stepContext;
	}
}

//
//
//

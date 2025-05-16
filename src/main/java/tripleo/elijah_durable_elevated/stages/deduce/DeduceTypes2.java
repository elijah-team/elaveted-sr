/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_elevated.stages.deduce;

import org.jdeferred2.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang2.*;
import tripleo.elijah.stages.logging.*;
import tripleo.elijah_durable_elevated.lang.impl.*;
import tripleo.elijah_durable_elevated.lang.types.*;
import tripleo.elijah_durable_elevated.stages.deduce.declarations.*;
import tripleo.elijah_durable_elevated.stages.deduce.nextgen.*;
import tripleo.elijah_durable_elevated.stages.deduce.post_bytecode.*;
import tripleo.elijah_durable_elevated.stages.deduce.tastic.*;
import tripleo.elijah_durable_elevated.stages.gen_fn.*;
import tripleo.elijah_durable_elevated.stages.instructions.*;
import tripleo.elijah_durable_elevated.stages.inter.*;
import tripleo.elijah_durable_elevated.stages.logging.*;
import tripleo.elijah_durable_elevated.work.*;
import tripleo.elijah_elevated_durable.backbone.*;
import tripleo.elijah_elevated_durable.lang_model.*;
import tripleo.elijah_elevated_durable.names_impl.*;
import tripleo.elijah_fluffy.util.*;

import java.util.*;
import java.util.function.*;

/**
 * Created 9/15/20 12:51 PM
 */
public class DeduceTypes2 implements GDeduceTypes2 {
	private static final   String                   PHASE = "DeduceTypes2";
	public final @NotNull  ElLog                    LOG;
	public final @NotNull  OS_Module                module;
	public final @NotNull  DeducePhase              phase;
	public final @NotNull  WorkManager              wm;
	private final          DeduceTypes2Injector     __inj;
	private final          Zero                     _p_zero;
	private final          ITasticMap               _p_tasticMap;
	private final          DeduceCentral            _p_central;
	private final          Map<OS_Element, DG_Item> _map_dgs;
	private final @NotNull List<Runnable>           onRunnables;
	private final          List<FunctionInvocation> functionInvocations; // TODO never used
	private final          List<IDeduceResolvable>  _pendingResolves;
	private final          ErrSink                  errSink;
	private final          PromiseExpectations      expectations;
	private final          List<DE3_Active>         _actives;
	private final          DeduceCreationContext    _defaultCreationContext;
	private final @NotNull List<DT_External>        externals;
	private final Map<OS_Type, EB_Type> ebTypes = new HashMap<>();

	public DeduceTypes2(@NotNull OS_Module module, @NotNull DeducePhase phase) {
		this(module, phase, EDL_ElLog.Verbosity.VERBOSE);
	}

	public DeduceTypes2(@NotNull OS_Module aModule, @NotNull DeducePhase aDeducePhase, EDL_ElLog.Verbosity aVerbosity) {
		__inj                   = new DeduceTypes2Injector();
		wm                      = _inj().new_WorkManager();
		_p_tasticMap            = _inj().new_TasticMap();
		_p_central              = _inj().new_DeduceCentral(this);
		onRunnables             = _inj().new_ArrayList__Runnable();
		_pendingResolves        = _inj().new_ArrayList__IDeduceResolvable();
		expectations            = _inj().new_PromiseExpectations(this);
		_actives                = _inj().new_ArrayList__DE3_Active();
		_defaultCreationContext = _inj().new_DefaultDeduceCreationContext(this);
		externals               = _inj().new_LinkedList__DT_External();
		functionInvocations     = _inj().new_ArrayList__FunctionInvocation();
		_map_dgs                = _inj().new_HashMap_DGS();
		_p_zero                 = _inj().new_Zero(this);

		this.module  = aModule;
		this.phase   = aDeducePhase;
		this.errSink = aModule.getCompilation().getErrSink();
		this.LOG     = _inj().new_ElLog(aModule.getFileName(), aVerbosity, PHASE);
		//
		aDeducePhase.addLog(LOG);
		//
		IStateRunnable.ST.register(phase);
		DeduceElement3_VariableTableEntry.ST.register(phase);

		phase.waitOn(this);
	}

	public DeduceTypes2Injector _inj() {
		return this.__inj;
	}

	public CompilationEnclosure _ce() {
		return _phase()._compilation().getCompilationEnclosure();
	}

	public @NotNull DeducePhase _phase() {
		return phase;
	}

	public ErrSink _errSink() {
		return errSink;
	}

	public @NotNull ElLog _LOG() {
		return LOG;
	}

	public @NotNull Zero _zero() {
		return _p_zero;
	}

	public DeduceElement3_IdentTableEntry _zero_getIdent(final IdentTableEntry aIdentTableEntryBte,
														 final BaseEvaFunction aGf, final DeduceTypes2 aDt2) {
		return _p_zero.getIdent(aIdentTableEntryBte, aGf, aDt2);
	}

	public void activePTE(@NotNull ProcTableEntry pte, ClassInvocation classInvocation) {
		// TODO Auto-generated method stub
		_actives.add(_inj().new_DE3_ActivePTE(this, pte, classInvocation));
	}

	public void addExternal(final DT_External aExt) {
		externals.add(aExt);
	}

	public void addResolvePending(final IDeduceResolvable aResolvable, final IDeduceElement_old aDeduceElement,
								  final Holder<OS_Element> aHolder) {
		assert !hasResolvePending(aResolvable);

		_pendingResolves.add(aResolvable);
	}

	public boolean hasResolvePending(final IDeduceResolvable aResolvable) {
		final boolean b = _pendingResolves.contains(aResolvable);
		return b;
	}

	public void assign_type_to_idte(@NotNull IdentTableEntry ite, @NotNull BaseEvaFunction generatedFunction,
									@NotNull Context aFunctionContext, @NotNull Context aContext) {

		final DeduceElement3_IdentTableEntry x = ite.getDeduceElement3(this, generatedFunction);
		x.assign_type_to_idte(aFunctionContext, aContext);
	}

	public DeduceCreationContext creationContext() {
		return _defaultCreationContext;
	}

	public void deduce_generated_constructor(final @NotNull EvaConstructor generatedFunction) {
		var ce = _phase().ca().getCompilation().getCompilationEnclosure();
		var mt = ce.addModuleThing(generatedFunction.module());

		final @NotNull ConstructorDef fd = (ConstructorDef) generatedFunction.getFD();
		deduce_generated_function_base(generatedFunction, fd, (ModuleThing) mt);
	}

	public void deduce_generated_function_base(final @NotNull BaseEvaFunction generatedFunction,
											   final @NotNull FunctionDef fd,
											   final @NotNull ModuleThing ignoredAMt) {
		fix_tables(generatedFunction);

		final Context fd_ctx = fd.getContext();
		//
		{
			ProcTableEntry        pte        = generatedFunction.fi.pte;
			final @NotNull String pte_string = getPTEString(pte);
			LOG.info("** deduce_generated_function " + fd.name() + " " + pte_string);// +"
			// "+((OS_Container)((FunctionDef)fd).getParent()).name());
		}
		//
		//
		for (final @NotNull Instruction instruction : generatedFunction.instructions()) {
			final Context context = generatedFunction.getContextFromPC(instruction.getIndex());
//			LOG.info("8006 " + instruction);
			switch (instruction.getName()) {
			case E:
				onEnterFunction(generatedFunction, context);
				break;
			case X:
				onExitFunction(generatedFunction, fd_ctx, context);
				break;
			case ES:
				break;
			case XS:
				break;
			case AGN:
				do_assign_normal(generatedFunction, fd_ctx, instruction, context);
				break;
			case AGNK:
				do_agnk(generatedFunction, instruction);
				break;
			case AGNT:
				break;
			case AGNF:
				LOG.info("292 Encountered AGNF");
				break;
			case JE:
				LOG.info("296 Encountered JE");
				break;
			case JNE:
				break;
			case JL:
				break;
			case JMP:
				break;
			case CALL:
				do_call(generatedFunction, fd, instruction, context);
				break;
			case CALLS:
				do_calls(generatedFunction, fd_ctx, instruction);
				break;
			case RET:
				break;
			case YIELD:
				break;
			case TRY:
				break;
			case PC:
				break;
			case CAST_TO:
				// README potentialType info is already added by MatchConditional
				break;
			case DECL:
				// README for GenerateC, etc: marks the spot where a declaration should go.
				// Wouldn't be necessary if we had proper Range's
				break;
			case IS_A:
				implement_is_a(generatedFunction, instruction);
				break;
			case NOP:
				break;
			case CONSTRUCT:
				implement_construct(generatedFunction, instruction, context);
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + instruction.getName());
			}
		}
		__post_vte_list_001(generatedFunction);
		__post_vte_list_002(generatedFunction, fd_ctx);
		__post_deferred_calls(generatedFunction, fd_ctx);
	}

	public void fix_tables(final @NotNull BaseEvaFunction evaFunction) {
		for (VariableTableEntry variableTableEntry : evaFunction.vte_list) {
			variableTableEntry._fix_table(this, evaFunction);
		}
		for (IdentTableEntry identTableEntry : evaFunction.idte_list) {
			identTableEntry._fix_table(this, evaFunction);
		}
		for (TypeTableEntry typeTableEntry : evaFunction.tte_list) {
			typeTableEntry._fix_table(this, evaFunction);
		}
		for (ProcTableEntry procTableEntry : evaFunction.prte_list) {
			procTableEntry._fix_table(this, evaFunction);
		}
	}

	@NotNull
	public String getPTEString(final @Nullable ProcTableEntry aProcTableEntry) {
		String pte_string;
		if (aProcTableEntry == null)
			pte_string = "[]";
		else {
			pte_string = aProcTableEntry.getLoggingString(this);
		}
		return pte_string;
	}

	public void onEnterFunction(final @NotNull BaseEvaFunction generatedFunction1, final Context aContext) {
		final DT_Function generatedFunction = new _DT_Function(this, generatedFunction1);

		for (VariableTableEntry variableTableEntry : generatedFunction.vte_list()) {
			variableTableEntry.setDeduceTypes2(this, aContext, generatedFunction.get());
		}
		for (IdentTableEntry identTableEntry : generatedFunction.idte_list()) {
			identTableEntry.setDeduceTypes2(this, aContext, generatedFunction.get());
			// identTableEntry._fix_table(this, generatedFunction);
		}
		for (ProcTableEntry procTableEntry : generatedFunction.prte_list()) {
			procTableEntry.setDeduceTypes2(this, aContext, generatedFunction.get(), errSink);
		}
		//
		// resolve all cte expressions
		//
		for (final @NotNull ConstantTableEntry cte : generatedFunction.cte_list()) {
			resolve_cte_expression(cte, aContext);
		}

		//
		// add proc table listeners
		//
		generatedFunction.add_proc_table_listeners();

		//
		// resolve ident table
		//
		generatedFunction.resolve_ident_table(aContext);

		//
		// resolve arguments table
		//
		generatedFunction.resolve_arguments_table(aContext);
	}

	public void onExitFunction(final @NotNull BaseEvaFunction generatedFunction,
							   final Context aFd_ctx,
							   final Context aContext) {
		//
		// resolve var table. moved from `E'
		//
		for (@NotNull VariableTableEntry vte : generatedFunction.vte_list) {
			vte.resolve_var_table_entry_for_exit_function();
		}
		for (@NotNull Runnable runnable : onRunnables) {
			runnable.run();
		}
//					LOG.info("167 "+generatedFunction);
		//
		// ATTACH A TYPE TO VTE'S
		// CONVERT USER TYPES TO USER_CLASS TYPES
		//
		for (final @NotNull VariableTableEntry vte : generatedFunction.vte_list) {
//                                              LOG.info("704 "+vte.type.attached+" "+vte.potentialTypes());
			final DeduceElement3_VariableTableEntry vte_de = vte.getDeduceElement3();
			vte_de.setDeduceTypes2(this, generatedFunction);
			vte_de.mvState(null, DeduceElement3_VariableTableEntry.ST.EXIT_CONVERT_USER_TYPES);
		}
		__checkVteList(generatedFunction);
		//
		// ATTACH A TYPE TO IDTE'S
		//
		for (@NotNull final IdentTableEntry ite : generatedFunction.idte_list) {
			final DeduceElement3_IdentTableEntry ite_de = ite.getDeduceElement3(this, generatedFunction);
			ite_de._ctxts(aFd_ctx, aContext);
			ite_de.mvState(null, DeduceElement3_IdentTableEntry.ST.EXIT_GET_TYPE);
		}
		{
			// TODO why are we doing this?
			final Resolve_each_typename ret = _inj().new_Resolve_each_typename(phase, this, errSink);
			for (final TypeTableEntry typeTableEntry : generatedFunction.tte_list) {
				ret.action(typeTableEntry);
			}
		}
		{
			final @NotNull WorkManager  workManager = wm;// _inj().new_WorkManager();
			@NotNull final Dependencies deps        = _inj().new_Dependencies(/* this, *//* phase, this, errSink */workManager, this);
			deps.subscribeTypes(generatedFunction.dependentTypesSubject());
			deps.subscribeFunctions(generatedFunction.dependentFunctionSubject());
//                                              for (@NotNull GenType genType : generatedFunction.dependentTypes()) {
//                                                      deps.action_type(genType, workManager);
//                                              }
//                                              for (@NotNull FunctionInvocation dependentFunction : generatedFunction.dependentFunctions()) {
//                                                      deps.action_function(dependentFunction, workManager);
//                                              }
			final int x = workManager.totalSize();

			// FIXME 06/14
			workManager.drain();

			phase.addDrs(generatedFunction, generatedFunction.drs);

			phase.doneWait(this, generatedFunction);
		}

		//
		// RESOLVE FUNCTION RETURN TYPES
		//
		resolve_function_return_type(generatedFunction);

		__on_exit__post_vte_something(generatedFunction, aFd_ctx);

		//
		// LOOKUP FUNCTIONS
		//
		{
			@NotNull
			WorkList wl = _inj().new_WorkList();

			for (@NotNull
			ProcTableEntry pte : generatedFunction.prte_list) {
				final DeduceElement3_ProcTableEntry de3_pte = convertPTE(generatedFunction, pte);
				de3_pte.lfoe_action(DeduceTypes2.this, wl, wm::addJobs, new Consumer<DeduceElement3_ProcTableEntry.LFOE_Action_Results>() {
					@Override
					public void accept(final DeduceElement3_ProcTableEntry.LFOE_Action_Results aLFOEActionResults) {
						int y = 2;
					}
				});
			}

			wm.addJobs(wl);
			// wm.drain();
		}

		checkEvaClassVarTable(generatedFunction);

		expectations.check();

		phase.addActives(_actives);

		for (IdentTableEntry identTableEntry : generatedFunction.idte_list) {
			generatedFunction.drs.add(_inj().new_DR_Ident(identTableEntry, generatedFunction, this));
		}
		for (VariableTableEntry variableTableEntry : generatedFunction.vte_list) {
			generatedFunction.drs.add(DR_Ident.create(variableTableEntry, generatedFunction));
		}

		phase.addDrs(generatedFunction, generatedFunction.drs);

		for (DT_External external : externals) {
			// external.onTargetModule(tm -> {phase.modulePromise(tm, external::actualise);}); //[T1160118]
			phase.modulePromise(external.targetModule(), external::actualise);
		}
	}

	public void do_assign_normal(final @NotNull BaseEvaFunction generatedFunction, final @NotNull Context aFd_ctx,
								 final @NotNull Instruction instruction, final @NotNull Context aContext) {
		// TODO doesn't account for __assign__
		final InstructionArgument agn_lhs = instruction.getArg(0);
		if (agn_lhs instanceof IntegerIA lhs_integerIA) {
			final @NotNull VariableTableEntry vte = generatedFunction.getVarTableEntry(lhs_integerIA.getIndex());

			OS_Element name = null;
			{
				final OS_Element[] el1 = new OS_Element[1];

				switch (vte.getVtt()) {
				case ARG -> {
					vte.elementPromise(el0 -> el1[0] = el0, null);
					name = el1[0];
				}
				case VAR -> {
					vte.elementPromise(el0 -> el1[0] = el0, null);
					name = el1[0];
				}
				}
			}

			if (name != null) {
				int y = 2;
			}

			final InstructionArgument i2 = instruction.getArg(1);
			if (i2 instanceof IntegerIA) {
				final @NotNull VariableTableEntry vte2 = generatedFunction.getVarTableEntry(to_int(i2));
				vte.addPotentialType(instruction.getIndex(), vte2.getTypeTableEntry());
			} else if (i2 instanceof final @NotNull FnCallArgs fca) {
				final @Nullable ITastic fcat = tasticFor(fca);
				assert fcat != null;
				fcat.do_assign_call(generatedFunction, aContext, vte, instruction, name);
			} else if (i2 instanceof ConstTableIA) {
				if (vte.getTypeTableEntry().getAttached() != null) {
					// TODO check types
				}
				final @NotNull ConstantTableEntry cte = generatedFunction
						.getConstTableEntry(((ConstTableIA) i2).getIndex());
				if (cte.type.getAttached() == null) {
					LOG.info("Null type in CTE " + cte);
				}
//		vte.type = cte.type;
				vte.addPotentialType(instruction.getIndex(), cte.type);
				DebugPrint.addPotentialType(vte, cte);
			} else if (i2 instanceof IdentIA identIA) {
				@NotNull
				IdentTableEntry idte = identIA.getEntry();

				var de3_ite = idte.getDeduceElement3();

				de3_ite.dan(generatedFunction, instruction, aContext, vte, identIA, idte, this);
			} else if (i2 instanceof ProcIA) {
				throw new NotImplementedException();
			} else
				throw new NotImplementedException();
		} else if (agn_lhs instanceof IdentIA arg) {
			final @NotNull IdentTableEntry agn_lhs_ite = arg.getEntry();
			final InstructionArgument      agn_rhs_ia  = instruction.getArg(1);
			if (agn_rhs_ia instanceof IntegerIA) {
				final @NotNull VariableTableEntry vte2 = generatedFunction.getVarTableEntry(to_int(agn_rhs_ia));
				agn_lhs_ite.addPotentialType(instruction.getIndex(), vte2.getTypeTableEntry());
			} else if (agn_rhs_ia instanceof final @NotNull FnCallArgs fca) {
				tasticFor(agn_rhs_ia).do_assign_call(generatedFunction, aFd_ctx, agn_lhs_ite, null, instruction);
			} else if (agn_rhs_ia instanceof IdentIA identIA) {
				if (agn_lhs_ite.getResolvedElement() instanceof VariableStatementImpl) {
					do_assign_normal_ident_deferred(generatedFunction, aFd_ctx, agn_lhs_ite);
				}
				@NotNull
				IdentTableEntry agn_rhs_ite = identIA.getEntry();
				do_assign_normal_ident_deferred(generatedFunction, aFd_ctx, agn_rhs_ite);
				agn_lhs_ite.addPotentialType(instruction.getIndex(), agn_rhs_ite.type);
			} else if (agn_rhs_ia instanceof ConstTableIA) {
				do_assign_constant(generatedFunction, instruction, agn_lhs_ite, (ConstTableIA) agn_rhs_ia);
			} else if (agn_rhs_ia instanceof ProcIA) {
				throw new NotImplementedException();
			} else
				throw new NotImplementedException();
		}
	}

	private void do_agnk(final @NotNull BaseEvaFunction generatedFunction, final @NotNull Instruction instruction) {
		final @NotNull IntegerIA          arg  = (IntegerIA) instruction.getArg(0);
		final @NotNull VariableTableEntry vte  = generatedFunction.getVarTableEntry(arg.getIndex());
		final InstructionArgument         i2   = instruction.getArg(1);
		final @NotNull ConstTableIA       ctia = (ConstTableIA) i2;

		{
			if (vte.getTypeTableEntry().getAttached() != null) {
				// TODO check types
			}
			final @NotNull ConstantTableEntry cte = generatedFunction.getConstTableEntry(ctia.getIndex());
			if (cte.type.getAttached() == null) {
				LOG.info("Null type in CTE " + cte);
			}
//		vte.type = cte.type;
			vte.addPotentialType(instruction.getIndex(), cte.type);
			DebugPrint.addPotentialType(vte, cte);
		}
	}

	private void do_call(final @NotNull BaseEvaFunction generatedFunction, final @NotNull FunctionDef fd,
						 final @NotNull Instruction instruction, final @NotNull Context context) {
		final int                     pte_num = ((ProcIA) instruction.getArg(0)).index();
		final @NotNull ProcTableEntry pte     = generatedFunction.getProcTableEntry(pte_num);
//				final InstructionArgument i2 = (instruction.getArg(1));
		{
			final @NotNull IdentIA identIA = (IdentIA) pte.expression_num;

			final IdentTableEntry identTableEntry = identIA.getEntry();
			var                   idnt            = generatedFunction.getIdent(identTableEntry);

			{
				ENU_ResolveToFunction rtf = null;
				var                   x   = identTableEntry.getIdent().getName();

				for (EN_Understanding understanding : x.getUnderstandings()) {
					if (understanding instanceof ENU_ResolveToFunction rtf1) {
						rtf = rtf1;
					}
				}
				for (EN_Usage usage : x.getUsages()) {
					if (usage instanceof EN_DeduceUsage edu) {
						final ProcTableEntry callable_pte = ((IdentTableEntry) edu.getBte())._callable_pte();
						var                  e            = callable_pte.__debug_expression;
						if (e instanceof DotExpression de) {
							var r = de.getRight();
							if (r instanceof IdentExpression ie) {
								ie.getName().addUnderstanding(_inj().new_ENU_FunctionInvocation(callable_pte));
							}
						}
					}
				}
			}

			var          xx = generatedFunction._getIdentIAResolvable(identIA);
			final String x  = xx.getNormalPath(generatedFunction, identIA);
			LOG.info("298 Calling " + x);
			final Resolve_Ident_IA.DeduceElementIdent dei = identIA.getEntry().getDeduceElement();
			resolveIdentIA_(context, dei, generatedFunction, new FoundElement(phase) {

				@SuppressWarnings("unused")
				final String xx = x;

				@Override
				public void foundElement(OS_Element e) {
					found_element_for_ite(generatedFunction, identIA.getEntry(), e, context, central());
//							identIA.getEntry().setCallablePTE(pte); // TODO ??

					pte.setStatus(BaseTableEntry.Status.KNOWN, _inj().new_ConstructableElementHolder(e, identIA));
					if (fd instanceof DefFunctionDef) {
						final IInvocation invocation = getInvocation((EvaFunction) generatedFunction);
						forFunction(newFunctionInvocation((FunctionDef) e, pte, invocation, phase), new ForFunction() {
							@Override
							public void typeDecided(@NotNull GenType aType) {
								@Nullable
								InstructionArgument x = generatedFunction.vte_lookup("Result");
								assert x != null;
								((IntegerIA) x).getEntry().getTypeTableEntry().setAttached(gt(aType));
							}
						});
					}
				}

				@Override
				public void noFoundElement() {
					errSink.reportError("370 Can't find callsite " + x);
					// TODO don't know if this is right
					@NotNull
					IdentTableEntry entry = identIA.getEntry();
					if (entry.getStatus() != BaseTableEntry.Status.UNKNOWN)
						entry.setStatus(BaseTableEntry.Status.UNKNOWN, null);
				}
			});
		}
	}

	private void do_calls(final @NotNull BaseEvaFunction generatedFunction, final @NotNull Context fd_ctx,
						  final @NotNull Instruction instruction) {
		final int                     i1  = to_int(instruction.getArg(0));
		final InstructionArgument     i2  = (instruction.getArg(1));
		final @NotNull ProcTableEntry fn1 = generatedFunction.getProcTableEntry(i1);
		{
			final int pc = instruction.getIndex();
			if (generatedFunction.deferred_calls.contains(pc)) {
				LOG.err("Call is deferred "/* +gf.getInstruction(pc) */ + " " + fn1);
			} else {
				Implement_Calls_ ic = _inj().new_Implement_Calls_(generatedFunction, fd_ctx, i2, fn1, pc, this);
				ic.action();
			}
		}
		/*
		 * if (i2 instanceof IntegerIA) { int i2i = to_int(i2); VariableTableEntry vte =
		 * generatedFunction.getVarTableEntry(i2i); int y =2; } else throw new
		 * NotImplementedException();
		 */
	}

	private void implement_is_a(final @NotNull BaseEvaFunction gf, final @NotNull Instruction instruction) {
		final IntegerIA testing_var_  = (IntegerIA) instruction.getArg(0);
		final IntegerIA testing_type_ = (IntegerIA) instruction.getArg(1);
		final Label     target_label  = ((LabelIA) instruction.getArg(2)).label;

		final VariableTableEntry testing_var    = gf.getVarTableEntry(testing_var_.getIndex());
		final TypeTableEntry     testing_type__ = gf.getTypeTableEntry(testing_type_.getIndex());

		GenType genType = testing_type__.getGenType();
		if (genType.getResolved() == null) {
			try {
				genType.setResolved(resolve_type(genType.getTypeName(), gf.getFD().getContext()).getResolved());
			} catch (ResolveError aResolveError) {
//				aResolveError.printStackTrace();
				errSink.reportDiagnostic(aResolveError);
				return;
			}
		}
		if (genType.getCi() == null) {
			genType.genCI(genType.getNonGenericTypeName(), this, errSink, phase);
		}
		if (genType.getNode() == null) {
			if (genType.getCi() instanceof ClassInvocation) {
				WlGenerateClass gen = _inj().new_WlGenerateClass(
						getGenerateFunctions(module),
						(ClassInvocation) genType.getCi(),
						phase.getGeneratedClasses(),
						phase.getCodeRegistrar()
																);

				gen.setConsumer(genType::setNode);

				gen.run(null);
			} else if (genType.getCi() instanceof NamespaceInvocation) {
				WlGenerateNamespace gen = _inj().new_WlGenerateNamespace(getGenerateFunctions(module),
																		 (NamespaceInvocation) genType.getCi(), phase.getGeneratedClasses(), phase.getCodeRegistrar());
				gen.run(null);
				genType.setNode(gen.getResult());
			}
		}
		// EvaNode testing_type = testing_type__.resolved();
		// assert testing_type != null;
		assert testing_type__.isResolved();
	}

	void implement_construct(BaseEvaFunction generatedFunction, Instruction instruction, final Context aContext) {
		final @NotNull Implement_construct ic = _inj().new_Implement_construct(this, generatedFunction, instruction);
		try {
			ic.action(aContext);
		} catch (FCA_Stop e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void __post_vte_list_001(final @NotNull BaseEvaFunction generatedFunction) {
		for (final @NotNull VariableTableEntry vte : generatedFunction.vte_list) {
			vte.typeResolvePromise().then(gt -> {
				var xx = vte.resolvedType();

				if (xx instanceof EvaClass evaClass) {
					if (gt.getCi() == null) {
						gt.setCi(evaClass.ci);
					}

					gt.setResolved(evaClass.getKlass().getOS_Type());
					gt.setTypeName(gt.getResolved());

					vte.getTypeTableEntry().setAttached(gt);
				} else if (xx instanceof EvaConstructor evaConstructor) {
					if (gt.getCi() == null) {
//						gt.ci = evaConstructor.ci;
					}

					gt.setResolved(evaConstructor.fi.getClassInvocation().getKlass().getOS_Type());
					gt.setTypeName(gt.getResolved());

					vte.getTypeTableEntry().setAttached(gt);
				}
			});
		}
	}

	private void __post_vte_list_002(final @NotNull BaseEvaFunction generatedFunction, final Context fd_ctx) {
		for (final @NotNull VariableTableEntry vte : generatedFunction.vte_list) {
			if (vte.getTypeTableEntry().getAttached() == null) {
				int potential_size = vte.potentialTypes().size();
				if (potential_size == 1)
					vte.getTypeTableEntry().setAttached(getPotentialTypesVte(vte).get(0).getAttached());
				else if (potential_size > 1) {
					// TODO Check type compatibility
					LOG.err("703 " + vte.getName() + " " + vte.potentialTypes());
					errSink.reportDiagnostic(_inj().new_CantDecideType(vte, vte.potentialTypes()));
				} else {
					// potential_size == 0
					// Result is handled by phase.typeDecideds, self is always valid
					if (/* vte.getName() != null && */ !(vte.getVtt() == VariableTableType.RESULT
							|| vte.getVtt() == VariableTableType.SELF))
						errSink.reportDiagnostic(_inj().new_CantDecideType(vte, vte.potentialTypes()));
				}
			} else if (vte.getVtt() == VariableTableType.RESULT) {

				int           state    = 0;
				final OS_Type attached = vte.getTypeTableEntry().getAttached();
				if (attached.getType() == OS_Type.Type.USER) {
					try {
						// FIXME 07/03 HACK

						if (attached.getTypeName() instanceof RegularTypeName rtn) {
							if (rtn.getName().equals("Unit")) {
								state = 1;
							}
						} else if (attached instanceof OS_UnitType) {
							state = 1;
						}

						if (state == 0)
							vte.getTypeTableEntry().setAttached(resolve_type(attached, fd_ctx));
					} catch (ResolveError aResolveError) {
						aResolveError.printStackTrace();
						assert false;
					}
				}
			}
		}
	}

	private void __post_deferred_calls(final @NotNull BaseEvaFunction generatedFunction,
									   final @NotNull Context fd_ctx) {
		//
		// NOW CALCULATE DEFERRED CALLS
		//
		for (final Integer deferred_call : generatedFunction.deferred_calls) {
			final Instruction instruction = generatedFunction.getInstruction(deferred_call);

			final int                     i1  = to_int(instruction.getArg(0));
			final InstructionArgument     i2  = (instruction.getArg(1));
			final @NotNull ProcTableEntry fn1 = generatedFunction.getProcTableEntry(i1);
			{
//				generatedFunction.deferred_calls.remove(deferred_call);
				Implement_Calls_ ic = _inj().new_Implement_Calls_(generatedFunction, fd_ctx, i2, fn1,
																  instruction.getIndex(), this);
				ic.action();
			}
		}
	}

	private void resolve_cte_expression(@NotNull ConstantTableEntry cte, Context aContext) {
		final IExpression initialValue = cte.initialValue;
		switch (initialValue.getKind()) {
		case NUMERIC:
			resolve_cte_expression_builtin(cte, aContext, BuiltInTypes.SystemInteger);
			break;
		case STRING_LITERAL:
			resolve_cte_expression_builtin(cte, aContext, BuiltInTypes.String_);
			break;
		case CHAR_LITERAL:
			resolve_cte_expression_builtin(cte, aContext, BuiltInTypes.SystemCharacter);
			break;
		case IDENT: {
			final OS_Type a = cte.getTypeTableEntry().getAttached();
			if (a != null) {
				assert a.getType() != null;
				if (a.getType() == OS_Type.Type.BUILT_IN && a.getBType() == BuiltInTypes.Boolean) {
					assert BuiltInTypes.isBooleanText(cte.getName());
				} else
					throw new NotImplementedException();
			} else {
				assert false;
			}
			break;
		}
		default: {
			LOG.err("8192 " + initialValue.getKind());
			throw new NotImplementedException();
		}
		}
	}

	private void __checkVteList(final @NotNull BaseEvaFunction generatedFunction) {
		for (final @NotNull VariableTableEntry vte : generatedFunction.vte_list) {
			__checkVteList_each(vte);
		}
	}

	void resolve_function_return_type(@NotNull BaseEvaFunction generatedFunction) {
		final DeduceElement3_Function f = _p_zero.get(DeduceTypes2.this, generatedFunction);

		final GenType gt = f.resolve_function_return_type_int(errSink);
		if (gt != null)
			// phase.typeDecided((EvaFunction) generatedFunction, gt);
			generatedFunction.resolveTypeDeferred(gt);
	}

	private void __on_exit__post_vte_something(final @NotNull BaseEvaFunction generatedFunction,
											   final Context aFd_ctx) {
		int y = 2;
		for (VariableTableEntry variableTableEntry : generatedFunction.vte_list) {
			final @NotNull Collection<TypeTableEntry> pot = variableTableEntry.potentialTypes();
			if (pot.size() == 1 && variableTableEntry.getGenType().isNull()) {
				final OS_Type x = pot.iterator().next().getAttached();
				if (x != null)
					if (x.getType() == OS_Type.Type.USER_CLASS) {
						try {
							final @NotNull GenType yy = resolve_type(x, aFd_ctx);
							// HACK TIME
							if (yy.getResolved() == null && yy.getTypeName().getType() == OS_Type.Type.USER_CLASS) {
								yy.setResolved(yy.getTypeName());
								yy.setTypeName(null);
							}

							yy.genCIForGenType2(this);
							variableTableEntry.resolveType(yy);
							variableTableEntry.resolveTypeToClass(yy.getNode());
//								variableTableEntry.dlv.type.resolve(yy);
						} catch (ResolveError aResolveError) {
							aResolveError.printStackTrace();
						}
					}
			}
		}
	}

	@NotNull
	private DeduceElement3_ProcTableEntry convertPTE(final @NotNull BaseEvaFunction generatedFunction,
													 final @NotNull ProcTableEntry pte) {
		final DeduceElement3_ProcTableEntry de3_pte = pte.getDeduceElement3(DeduceTypes2.this, generatedFunction);
		return de3_pte;
	}

	private void checkEvaClassVarTable(final @NotNull BaseEvaFunction generatedFunction) {
		// for (VariableTableEntry variableTableEntry : generatedFunction.vte_list) {
		// variableTableEntry.setDeduceTypes2(this, aContext, generatedFunction);
		// }
		for (IdentTableEntry identTableEntry : generatedFunction.idte_list) {
			identTableEntry.getDeduceElement3(this, generatedFunction).mvState(
					null,
					DeduceElement3_IdentTableEntry.ST.CHECK_EVA_CLASS_VAR_TABLE);
			// identTableEntry.setDeduceTypes2(this, aContext, generatedFunction);

		}
	}

	public static int to_int(@NotNull final InstructionArgument arg) {
		if (arg instanceof IntegerIA)
			return ((IntegerIA) arg).getIndex();
		if (arg instanceof ProcIA)
			return ((ProcIA) arg).index();
		if (arg instanceof IdentIA)
			return ((IdentIA) arg).getIndex();
		throw new NotImplementedException();
	}

	public @Nullable ITastic tasticFor(Object o) {
		if (_p_tasticMap.containsKey(o)) {
			return _p_tasticMap.get(o);
		}

		ITastic r = null;

		if (o instanceof FnCallArgs) {
			r = _inj().new_FT_FnCallArgs(this, (FnCallArgs) o);
			_p_tasticMap.put(o, r);
		}

		return r;
	}

	public void do_assign_normal_ident_deferred(final @NotNull BaseEvaFunction generatedFunction,
												final @NotNull Context aContext, final @NotNull IdentTableEntry aIdentTableEntry) {
		if (aIdentTableEntry.type == null) {
			// TODO 08/28 making a type with a null type
			aIdentTableEntry.makeType(generatedFunction, TypeTableEntry.Type.TRANSIENT, (OS_Type) null);
		}

		final DR_Ident ident = aIdentTableEntry.__gf.getIdent(aIdentTableEntry);
		ident.try_resolve_normal(aContext);
		final DT_ResolveObserver resolveObserver = aIdentTableEntry
				.getDeduceElement3()
				.create_do_assign_normal_ident_deferred__DT_ResolveObserver(aIdentTableEntry, generatedFunction);
		ident.addResolveObserver(resolveObserver);
	}

	private void do_assign_constant(final @NotNull BaseEvaFunction generatedFunction,
									final @NotNull Instruction instruction, final @NotNull IdentTableEntry idte,
									final @NotNull ConstTableIA i2) {
		if (idte.type != null && idte.type.getAttached() != null) {
			// TODO check types
		}
		final @NotNull ConstantTableEntry cte = generatedFunction.getConstTableEntry(i2.getIndex());
		if (cte.type.getAttached() == null) {
			LOG.err("*** ERROR: Null type in CTE " + cte);
		}
		// idte.type may be null, but we still addPotentialType here
		idte.addPotentialType(instruction.getIndex(), cte.type);
	}

	public void resolveIdentIA_(@NotNull Context context, @NotNull Resolve_Ident_IA.DeduceElementIdent dei,
								BaseEvaFunction generatedFunction, @NotNull FoundElement foundElement) {
		@NotNull
		Resolve_Ident_IA ria = _inj().new_Resolve_Ident_IA(
				_inj().new_DeduceClient3(this),
				context,
				dei,
				generatedFunction,
				foundElement,
				errSink);
		ria.action();
	}

	public void found_element_for_ite(BaseEvaFunction generatedFunction,
									  @NotNull IdentTableEntry ite,
									  @Nullable OS_Element aElement,
									  Context ctx,
									  final DeduceCentral central) {
		if (aElement != ite.getResolvedElement()) {
			tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon
					.println_err_2(String.format("2571 Setting FoundElement for ite %s to %s when it is already %s",
												 ite, aElement, ite.getResolvedElement()));
		}

		var env = _inj().new_DT_Env(LOG, errSink, central);

		@NotNull Found_Element_For_ITE fefi = _inj().new_Found_Element_For_ITE(generatedFunction,
																			   ctx,
																			   env,
																			   _inj().new_DeduceClient1(this));
		fefi.action(ite);
	}

	public @NotNull DeduceCentral central() {
		return _p_central;
	}

	public IInvocation getInvocation(@NotNull EvaFunction generatedFunction) {
		final ClassInvocation     classInvocation = generatedFunction.fi.getClassInvocation();
		final NamespaceInvocation ni;
		if (classInvocation == null) {
			ni = generatedFunction.fi.getNamespaceInvocation();
			return ni;
		} else
			return classInvocation;
	}

	public void forFunction(@NotNull FunctionInvocation gf, @NotNull ForFunction forFunction) {
		phase.forFunction(this, gf, forFunction);
	}

	@NotNull
	public FunctionInvocation newFunctionInvocation(FunctionDef aFunctionDef, ProcTableEntry aPte,
													@NotNull IInvocation aInvocation, @NotNull DeducePhase aDeducePhase) {
		@NotNull
		FunctionInvocation fi = aDeducePhase.newFunctionInvocation(aFunctionDef, aPte, aInvocation);
		// TODO register here
		return fi;
	}

	public OS_Type gt(@NotNull GenType aType) {
		return aType.getResolved() != null ? aType.getResolved() : aType.getTypeName();
	}

	@NotNull
	public GenType resolve_type(final @NotNull OS_Type type, final Context ctx) throws ResolveError {
		// return ResolveType.resolve_type2(module, type, ctx, LOG, this);
		return ResolveType.resolve_type(module, type, ctx, LOG, this);
	}

	public @NotNull GenerateFunctions getGenerateFunctions(@NotNull OS_Module aModule) {
		return phase.getGeneratePhase().getGenerateFunctions(aModule);
	}

	@NotNull
	List<TypeTableEntry> getPotentialTypesVte(@NotNull VariableTableEntry vte) {
		return _inj().new_ArrayList__TypeTableEntry(vte.potentialTypes());
	}

	private void resolve_cte_expression_builtin(@NotNull ConstantTableEntry cte, Context aContext,
												BuiltInTypes aBuiltInType) {
		final OS_Type a = cte.getTypeTableEntry().getAttached();
		if (a == null || a.getType() != OS_Type.Type.USER_CLASS) {
			try {
				cte.getTypeTableEntry().setAttached(resolve_type(_inj().new_OS_BuiltinType(aBuiltInType), aContext));
			} catch (ResolveError resolveError) {
				tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon.println_out_2("117 Can't be here");
				resolveError.printStackTrace(); // TODO print diagnostic
			}
		}
	}

	private void __checkVteList_each(final @NotNull VariableTableEntry vte) {
		if (vte.getVtt() == VariableTableType.ARG) {
			final TypeTableEntry vteType = vte.getTypeTableEntry();

			if (vteType.getGenType() instanceof ForwardingGenType fgt)
				fgt.unsparkled();

			if (vteType.getGenType() != null) {
				var vte_genType = vte.getGenType();
				if (vte_genType.getNode() != null)
					return;
			}

			final OS_Type attached = vteType.getAttached();
			if (attached != null) {
				if (attached.getType() == OS_Type.Type.USER) {
					// throw new AssertionError();
					errSink.reportError("369 ARG USER type (not deduced) " + vte);
				}
			} else {
				// 08/13 errSink.reportError("457 ARG type not deduced/attached " + vte);
			}
		}
	}

	public void deduceClasses(final @NotNull List<EvaNode> lgc) {
		for (EvaNode evaNode : lgc) {
			if (!(evaNode instanceof final @NotNull EvaClass evaClass))
				continue;

			deduceOneClass(evaClass);
		}
	}

	public void deduceOneClass(final @NotNull EvaClass aEvaClass) {
		for (EvaContainer.VarTableEntry entry : aEvaClass.varTable) {
			final OS_Type vt      = entry.varType;
			GenType       genType = GenType.makeFromOSType(vt, aEvaClass.ci.genericPart(), this, phase, LOG, errSink);
			if (genType != null) {
				if (genType.getNode() != null) {
					entry.resolve(genType.getNode());
				} else {
					int y = 2; // 05/22
				}
			}

			NotImplementedException.raise();

		}
	}

	public void deduceFunctions(final @NotNull Iterable<EvaNode> lgf) {
		for (final EvaNode evaNode : lgf) {
			if (evaNode instanceof @NotNull final EvaFunction generatedFunction) {
				deduceOneFunction(generatedFunction, phase);
			}
		}
		@NotNull
		List<EvaNode> generatedClasses = (phase.getGeneratedClasses().copy());
		// TODO consider using reactive here
		int size;
		do {
			size             = df_helper(generatedClasses, new dfhi_functions());
			generatedClasses = phase.getGeneratedClasses().copy();
		} while (size > 0);
		do {
			size             = df_helper(generatedClasses, new dfhi_constructors());
			generatedClasses = phase.getGeneratedClasses().copy();
		} while (size > 0);
	}

	public boolean deduceOneFunction(@NotNull EvaFunction aGeneratedFunction, @NotNull DeducePhase aDeducePhase) {
		var ce = aDeducePhase.ca().getCompilation().getCompilationEnclosure();
		var mt = ce.getModuleThing(aGeneratedFunction.module());

		if (aGeneratedFunction.deducedAlready) {
			return false;
		}

		deduce_generated_function(aGeneratedFunction, (ModuleThing) mt);

		((ModuleThing) mt).addFunction(aGeneratedFunction);

		aGeneratedFunction.deducedAlready = true;
		__post_dof_idte_register_resolved(aGeneratedFunction, aDeducePhase);
		__post_dof_result_type(aGeneratedFunction);
		aDeducePhase.addFunction(aGeneratedFunction, aGeneratedFunction.getFD());

		for (IdentTableEntry identTableEntry : aGeneratedFunction.idte_list) {
			aGeneratedFunction._idents.add(aGeneratedFunction.getIdent(identTableEntry));
		}
		for (VariableTableEntry variableTableEntry : aGeneratedFunction.vte_list) {
			aGeneratedFunction._idents.add(aGeneratedFunction.getIdent(variableTableEntry));
		}

		return true;
	}

//	private GeneratedNode makeNode(GenType aGenType) {
//		if (aGenType.ci instanceof ClassInvocation) {
//			final ClassInvocation ci = (ClassInvocation) aGenType.ci;
//			@NotNull GenerateFunctions gen = phase.generatePhase.getGenerateFunctions(ci.getKlass().getContext().module());
//			WlGenerateClass wlgc = _inj().new_WlGenerateClass(gen, ci, phase.generatedClasses);
//			wlgc.run(null);
//			return wlgc.getResult();
//		}
//		return null;
//	}

	/**
	 * Deduce functions or constructors contained in classes list
	 *
	 * @param aGeneratedClasses assumed to be a list of {@link EvaContainerNC}
	 * @param dfhi              specifies what to select for:<br>
	 *                          {@link dfhi_functions} will select all functions
	 *                          from {@code functionMap}, and <br>
	 *                          {@link dfhi_constructors} will select all
	 *                          constructors from {@code constructors}.
	 * @param <T>               generic parameter taken from {@code dfhi}
	 * @return the number of deduced functions or constructors, or 0
	 */
	<T> int df_helper(@NotNull List<EvaNode> aGeneratedClasses, @NotNull df_helper_i<T> dfhi) {
		int size = 0;
		for (EvaNode evaNode : aGeneratedClasses) {
			@NotNull
			EvaContainerNC generatedContainerNC = (EvaContainerNC) evaNode;
			final @Nullable df_helper<T> dfh = dfhi.get(generatedContainerNC);
			if (dfh == null)
				continue;
			@NotNull
			Collection<T> lgf2 = dfh.collection();
			for (final T generatedConstructor : lgf2) {
				if (dfh.deduce(generatedConstructor))
					size++;
			}
		}
		return size;
	}

	public void deduce_generated_function(final @NotNull EvaFunction generatedFunction,
										  final @NotNull ModuleThing aMt) {
		final @NotNull FunctionDef fd = generatedFunction.getFD();
		deduce_generated_function_base(generatedFunction, fd, aMt);
	}

	private static void __post_dof_idte_register_resolved(final @NotNull EvaFunction aGeneratedFunction,
														  final @NotNull DeducePhase aDeducePhase) {
		for (@NotNull
		IdentTableEntry identTableEntry : aGeneratedFunction.idte_list) {
			if (identTableEntry.getResolvedElement() instanceof final @NotNull VariableStatementImpl vs) {
				OS_Element el  = vs.getParent().getParent();
				OS_Element el2 = aGeneratedFunction.getFD().getParent();
				if (el != el2) {
					if (el instanceof ClassStatement || el instanceof NamespaceStatement)
						// NOTE there is no concept of gf here
						aDeducePhase.registerResolvedVariable(identTableEntry, el, vs.getName());
				}
			}
		}
	}

	private void __post_dof_result_type(final @NotNull EvaFunction aGeneratedFunction) {
		final @NotNull EvaFunction gf = aGeneratedFunction;

		@Nullable
		InstructionArgument result_index = gf.vte_lookup("Result");
		if (result_index == null) {
			// if there is no Result, there should be Value
			result_index = gf.vte_lookup("Value");
			// but Value might be passed in. If it is, discard value
			if (result_index != null) {
				@NotNull
				VariableTableEntry vte = ((IntegerIA) result_index).getEntry();
				if (vte.getVtt() != VariableTableType.RESULT) {
					result_index = null;
				}
			}
		}
		if (result_index != null) {
			@NotNull
			VariableTableEntry vte = ((IntegerIA) result_index).getEntry();
			if (vte.resolvedType() == null) {
				GenType b = vte.getGenType();
				OS_Type a = vte.getTypeTableEntry().getAttached();
				if (a != null) {
					// see resolve_function_return_type
					switch (a.getType()) {
					case USER_CLASS:
						dof_uc(vte, a);
						break;
					case USER:
						vte.getGenType().setTypeName(a);
						try {
							@NotNull
							GenType rt = resolve_type(a, a.getTypeName().getContext());
							if (rt.getResolved() != null && rt.getResolved().getType() == OS_Type.Type.USER_CLASS) {
								if (rt.getResolved().getClassOf().getGenericPart().size() > 0)
									vte.getGenType().setNonGenericTypeName(a.getTypeName()); // TODO might be wrong
								dof_uc(vte, rt.getResolved());
							}
						} catch (ResolveError aResolveError) {
							errSink.reportDiagnostic(aResolveError);
						}
						break;
					default:
						int y3 = 2;

						vte.typePromise().then(gt -> {
							int y4 = 2;

							if (vte.getStatus() == BaseTableEntry.Status.UNCHECKED) {
								// NOTE curious...
								int y5 = 25;

								final OS_Element resolvedElement = vte.getResolvedElement();

								if (resolvedElement == null) {
//									throw new AssertionError();
								} else {
									vte.setStatus(BaseTableEntry.Status.KNOWN,
												  _inj().new_GenericElementHolder(resolvedElement));
								}
							} else
								throw new IllegalStateException("Error");

						});

						if (vte.getVtt() == VariableTableType.RESULT) {
							final OS_Element resolvedElement = vte.getResolvedElement();

							if (resolvedElement == null) {
								// throw new AssertionError();
							} else {
								vte.setStatus(BaseTableEntry.Status.KNOWN,
											  _inj().new_GenericElementHolder(resolvedElement));
							}
						}

						break;
					}
				} else {
					// 08/31 Back to this...
					throw new NotImplementedException();
				}
			}
		}
	}

	private void dof_uc(@NotNull VariableTableEntry aVte, @NotNull OS_Type aA) {
		// we really want a ci from somewhere
		assert aA.getClassOf().getGenericPart().size() == 0;
		@Nullable
		ClassInvocation ci = _inj().new_ClassInvocation(aA.getClassOf(), null, new ReadySupplier_1<>(this));
		ci = phase.registerClassInvocation(ci);

		aVte.getGenType().setResolved(aA); // README assuming OS_Type cannot represent namespaces
		aVte.getGenType().setCi(ci);

		ci.resolvePromise().done(new DoneCallback<EvaClass>() {
			@Override
			public void onDone(@NotNull EvaClass result) {
				aVte.resolveTypeToClass(result);
			}
		});
	}

	public boolean deduceOneConstructor(@NotNull IEvaConstructor aEvaConstructor1, @NotNull DeducePhase aDeducePhase) {
		final EvaConstructor evaConstructor = (EvaConstructor) aEvaConstructor1;

		if (evaConstructor.deducedAlready)
			return false;

		final ICompilationAccess   compilationAccess = aDeducePhase.ca();
		final CompilationEnclosure ce                = (CompilationEnclosure) compilationAccess.getCompilation().getCompilationEnclosure();

		final DeduceElement3_Constructor ccc = new DeduceElement3_Constructor(evaConstructor, this, ce);

		final ModuleThing mt = ce.addModuleThing(evaConstructor.module());

		ccc.deduceOneConstructor(mt);

		final Eventual<DeduceElement3_Constructor> deduceElement3ConstructorEventual = evaConstructor.de3_Promise();
		deduceElement3ConstructorEventual.resolve(ccc);

		evaConstructor.deducedAlready = true;
		return true;
	}

	public DG_AliasStatement DG_AliasStatement(final AliasStatementImpl aE, final DeduceTypes2 aDt2) {
		if (_map_dgs.containsKey(aE)) {
			return (DG_AliasStatement) _map_dgs.get(aE);
		}

		final DG_AliasStatement R = _inj().new_DG_AliasStatement(aE, aDt2);
		_map_dgs.put(aE, R);
		return R;
	}

	public DG_ClassStatement DG_ClassStatement(final ClassStatement aClassStatement) {
		if (_map_dgs.containsKey(aClassStatement)) {
			return (DG_ClassStatement) _map_dgs.get(aClassStatement);
		}

		final DG_ClassStatement R = _inj().new_DG_ClassStatement(aClassStatement);
		_map_dgs.put(aClassStatement, R);
		return R;
	}

	public DG_FunctionDef DG_FunctionDef(final FunctionDef aFunctionDef) {
		if (_map_dgs.containsKey(aFunctionDef)) {
			return (DG_FunctionDef) _map_dgs.get(aFunctionDef);
		}

		final DG_FunctionDef R = _inj().new_DG_FunctionDef(aFunctionDef);
		_map_dgs.put(aFunctionDef, R);
		return R;
	}

	public String getFileName() {
		return module.getFileName();
	}

	@Nullable IInvocation getInvocationFromBacklink(@Nullable InstructionArgument aBacklink) {
		if (aBacklink == null) {
			return null;
		} else {
			// TODO implement me
			return null;
		}
	}

	@NotNull
	public List<TypeTableEntry> getPotentialTypesVte(@NotNull EvaFunction generatedFunction,
													 @NotNull InstructionArgument vte_index) {
		return getPotentialTypesVte(generatedFunction.getVarTableEntry(to_int(vte_index)));
	}

	boolean lookup_name_calls(final @NotNull Context ctx, final @NotNull String pn, final @NotNull ProcTableEntry pte) {
		final LookupResultList     lrl  = ctx.lookup(pn);
		final @Nullable OS_Element best = lrl.chooseBest(null); // TODO check arity and arg matching
		if (best != null) {
			pte.setStatus(BaseTableEntry.Status.KNOWN, _inj().new_ConstructableElementHolder(best, null)); // TODO why
			// include
			// if only
			// to be
			// null?
			return true;
		}
		return false;
	}

	void onFinish(Runnable r) {
		onRunnables.add(r);
	}

	public <B> @NotNull PromiseExpectation<B> promiseExpectation(ExpectationBase base, String desc) {
		final @NotNull PromiseExpectation<B> promiseExpectation = _inj().new_PromiseExpectation(base, desc, this);
		expectations.add(promiseExpectation);
		return promiseExpectation;
	}

	public void register_and_resolve(@NotNull VariableTableEntry aVte, @NotNull ClassStatement aKlass) {
		@Nullable
		ClassInvocation ci = _inj().new_ClassInvocation(aKlass, null, new ReadySupplier_1<>(this));
		ci = phase.registerClassInvocation(ci);
		ci.resolvePromise().done(aVte::resolveTypeToClass);
	}

	public void removeResolvePending(final IdentTableEntry aResolvable) {
		assert hasResolvePending(aResolvable);

		_pendingResolves.remove(aResolvable);
	}

	/* static */
	@NotNull
	GenType resolve_type(final @NotNull OS_Module module, final @NotNull OS_Type type, final Context ctx)
	throws ResolveError {
		return ResolveType.resolve_type(module, type, ctx, LOG, this);
	}

	//	@Deprecated
	public void resolveIdentIA_(
			@NotNull Context context,
			@NotNull IdentIA identIA,
			@NotNull BaseEvaFunction generatedFunction,
			@NotNull FoundElement foundElement) {
		final @NotNull Resolve_Ident_IA ria = _inj().new_Resolve_Ident_IA(
				_inj().new_DeduceClient3(this),
				context,
				identIA,
				generatedFunction,
				foundElement,
				errSink);
		ria.action();
	}

	public void resolveIdentIA2_(@NotNull Context context,
								 @NotNull IdentIA identIA,
								 @NotNull EvaFunction generatedFunction,
								 @NotNull FoundElement foundElement) {
		final @NotNull List<InstructionArgument> s = StaticEva._getIdentIAPathList(identIA);
		resolveIdentIA2_(context, identIA, s, generatedFunction, foundElement);
	}

	public void resolveIdentIA2_(@NotNull final Context ctx,
								 @Nullable IdentIA identIA,
								 @Nullable List<InstructionArgument> s,
								 @NotNull final BaseEvaFunction generatedFunction,
								 @NotNull final FoundElement foundElement) {
		@NotNull
		Resolve_Ident_IA2 ria2 = _inj().new_Resolve_Ident_IA2(this, errSink, phase, generatedFunction, foundElement);
		ria2.resolveIdentIA2_(ctx, identIA, s);
	}

	public DeduceElement3_ProcTableEntry zeroGet(final ProcTableEntry aPte, final BaseEvaFunction aEvaFunction) {
		return _p_zero.get(aPte, aEvaFunction, this);
	}

	public DeduceElement3_VariableTableEntry zeroGet(final VariableTableEntry aVte,
													 final BaseEvaFunction aGeneratedFunction) {
		return _p_zero.get(aVte, aGeneratedFunction);
	}

	public TypeName HACKMAYBE_resolve_TypeName(final TypeOfTypeNameImpl aTypeOfTypeName, final Context aCtx) throws ResolveError {
		//		tripleo.elijah.util.Stupidity.println_out_2(_typeOf.toString());
		final LookupResultList lrl  = DeduceLookupUtils.lookupExpression(aTypeOfTypeName.typeOf(), aCtx, this);
		final OS_Element       best = lrl.chooseBest(null);

		if (best instanceof VariableStatement) {
			return ((VariableStatement) best).typeName();
		} else {
			return null;
		}
	}

	public Aurum onResolvedExpression(final IExpression aDebugExpression, final DoneCallback<FluffyExpression> aFluffyExpression) {
		return null;
	}

	public void doLookupSimple(final @NotNull OS_Module aModule,
							   final String aName,
							   final DoneCallback<OS_Element> cb) {
		OS_Module prelude = aModule.prelude();
		if (prelude == null) // README Assume `module' IS prelude
			prelude = aModule;

		new EB_Lookup(this, prelude.getContext(), aName)
				.getEventual()
				.then(cb);
	}

	public EB_Type ebLookupType(final OS_Type aType) {
		final var dt2 = this;
		return ebTypes.computeIfAbsent(aType, (__$$) -> ResolveType.createEBType(aType, dt2));
	}

	@NotNull
	public DeferredMemberFunction deferred_member_function(@Nullable IInvocation aInvocation,
														   @NotNull FunctionDef aFunctionDef,
														   @NotNull FunctionInvocation aFunctionInvocation,
														   final OS_Element aOSElement) {
		if (aInvocation == null) {
			if (aOSElement instanceof NamespaceStatement) {
				aInvocation = phase.registerNamespaceInvocation((NamespaceStatement) aOSElement);
			} else if (aOSElement instanceof OS_SpecialVariable) {
				aInvocation = ((OS_SpecialVariable) aOSElement).getInvocation(this);
			}
		}
		DeferredMemberFunction dm = _inj().new_DeferredMemberFunction(aOSElement,
																	  aInvocation,
																	  aFunctionDef,
																	  this,
																	  aFunctionInvocation);
		phase.addDeferredMember(dm);
		return dm;
	}

	interface df_helper<T> {
		@NotNull
		Collection<T> collection();

		boolean deduce(T generatedConstructor);
	}

	interface df_helper_i<T> {
		@Nullable
		df_helper<T> get(EvaContainerNC generatedClass);
	}

	class df_helper_Constructors implements df_helper<IEvaConstructor> {
		private final EvaClass evaClass;

		public df_helper_Constructors(EvaClass aEvaClass) {
			evaClass = aEvaClass;
		}

		@Override
		public @NotNull Collection<IEvaConstructor> collection() {
			return evaClass.constructors.values();
		}

		@Override
		public boolean deduce(@NotNull IEvaConstructor aEvaConstructor) {
			return deduceOneConstructor(aEvaConstructor, phase);
		}
	}

	class df_helper_Functions implements df_helper<EvaFunction> {
		private final EvaContainerNC generatedContainerNC;

		public df_helper_Functions(EvaContainerNC aGeneratedContainerNC) {
			generatedContainerNC = aGeneratedContainerNC;
		}

		@Override
		public @NotNull Collection<EvaFunction> collection() {
			return generatedContainerNC.functionMap.values();
		}

		@Override
		public boolean deduce(@NotNull EvaFunction aGeneratedFunction) {
			return deduceOneFunction(aGeneratedFunction, phase);
		}
	}

	class dfhi_constructors implements df_helper_i<IEvaConstructor> {
		@Override
		public @Nullable df_helper_Constructors get(EvaContainerNC aGeneratedContainerNC) {
			if (aGeneratedContainerNC instanceof EvaClass) // TODO namespace constructors
				return new df_helper_Constructors((EvaClass) aGeneratedContainerNC);
			else
				return null;
		}
	}

	class dfhi_functions implements df_helper_i<EvaFunction> {
		@Override
		public @NotNull df_helper_Functions get(EvaContainerNC aGeneratedContainerNC) {
			return new df_helper_Functions(aGeneratedContainerNC);
		}
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//

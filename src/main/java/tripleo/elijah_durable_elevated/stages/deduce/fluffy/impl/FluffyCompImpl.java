package tripleo.elijah_durable_elevated.stages.deduce.fluffy.impl;

import com.google.common.collect.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.compiler_model.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_durable_elevated.*;
import tripleo.elijah_durable_elevated.comp.internal.*;
import tripleo.elijah_durable_elevated.entrypoints.*;
import tripleo.elijah_durable_elevated.lang.impl.*;
import tripleo.elijah_durable_elevated.stages.deduce.fluffy.i.*;
import tripleo.elijah_durable_elevated.util.*;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.graph.*;

import java.util.*;
import java.util.stream.*;

public class FluffyCompImpl implements FluffyComp, EventualRegister {
	private final EDL_Compilation              _comp;
	private final Map<OS_Module, FluffyModule> fluffyModuleMap = new HashMap<>();
	private final FluffyCompImplInjector       __inj           = new FluffyCompImplInjector();
	private final List<Eventual<?>>            _eventuals      = new ArrayList<>();
	private final Map<FX_Base, Eventual<CM_Base>> exchanges = new HashMap<>();
	private final DefaultEventualRegister         der;

	public FluffyCompImpl(final EDL_Compilation aComp) {
		_comp = aComp;

		_comp.addToAllRegisters(this);

		der = new DefaultEventualRegister() {

			@Override
			public @NotNull String _host() {
				// README being stupid, but i don't like this
				return "FluffyCompImpl";
			}

			/**
			 * What is the usage model for this?
			 */
			@Override
			public Operation<Ok> maybeCheckFinishEventuals() {
				return Operation.success(Ok.instance());
			}
		};
	}

	public static boolean isMainClassEntryPoint(@NotNull final OS_NamedElement input) {
		// TODO 08/27 Use understanding/~ processor for this
		final FunctionDef fd = (FunctionDef) input;
		return MainClassEntryPoint.is_main_function_with_no_args(fd);
	}

	@Override
	public void find_multiple_items(final @NotNull OS_Module aModule, OS_ModuleImpl.Complaint aComplaint) {
		final Multimap<String, ModuleItem> items_map = ArrayListMultimap.create(aModule.getItems().size(), 1);

		aModule.getItems().stream()
				.filter(Objects::nonNull)
				.filter(x -> !(x instanceof ImportStatement))
				.forEach(item -> {
					// README likely for member functions.
					// README Also note elijah has single namespace
					items_map.put(item.name(), item);
				});

		for (final String key : items_map.keys()) {
			boolean warn = false;

			final Collection<ModuleItem> moduleItems = items_map.get(key);
			if (moduleItems.size() == 1)
				continue;

			final Collection<ElObjectType> t = moduleItems.stream()
					.map(DecideElObjectType::getElObjectType)
					.collect(Collectors.toList());

			final Set<ElObjectType> st = new HashSet<ElObjectType>(t);
			if (st.size() > 1)
				warn = true;
			if (moduleItems.size() > 1) {
				if (!(moduleItems.iterator().next() instanceof NamespaceStatement) || st.size() != 1) {
					warn = true;
				}
			}

			//
			//
			//

			if (warn) {
				aComplaint.reportWarning(aModule, key);
			}
		}

	}

	@Override
	public FluffyModule module(final OS_Module aModule) {
		if (fluffyModuleMap.containsKey(aModule)) {
			return fluffyModuleMap.get(aModule);
		}

		final FluffyModuleImpl fluffyModule = _inj().new_FluffyModuleImpl(aModule, _comp);
		fluffyModuleMap.put(aModule, fluffyModule);
		return fluffyModule;
	}

	private FluffyCompImplInjector _inj() {
		return __inj;
	}

	@Override
	public void checkFinishEventuals() {
		if (DebugFlags.MakeSense2) {
			der.checkFinishEventuals();
		} else {
			int y = 2;
			for (Eventual<?> eventual : _eventuals) {
				if (eventual.isResolved()) {
					SimplePrintLoggerToRemoveSoon.println_err_4("[FluffyCompImpl::checkEventual] ok for " + eventual.description());
				} else {
					SimplePrintLoggerToRemoveSoon.println_err_4("[FluffyCompImpl::checkEventual] failed for " + eventual.description());
				}
			}
		}
	}

	@Override
	public <P> void register(final Eventual<P> e) {
//		der.register(e);
		_eventuals.add(e);
	}

	@Override
	public Operation<Ok> maybeCheckFinishEventuals() {
		assert false;
		return null;
	}

	@Override
	public Eventual<CK_SourceFile> exchange(final ILazyCompilerInstructions_.FX_Ez aFXEz) {
		//final CK_SourceFile cmBase = (CK_SourceFile) exchanges.computeIfAbsent(aFXEz, (__$$) -> {
		//	//noinspection UnnecessaryLocalVariable
		//	final CK_SourceFile sf = CK_SourceFileFactory.get(aFXEz.file(), CK_SourceFileFactory.K.SpecifiedEzFile);
		//	return sf;
		//});
		final Eventual<CM_Base> cmBase = exchanges.computeIfAbsent(aFXEz, (__$$) -> {
			return new Eventual<CM_Base>();
		});

		final Eventual<CK_SourceFile> exch = new Eventual<>();
		return exch;
	}

	@Override
	public @NotNull String _host() {
		return null;
	}

	//@Override
	public <P> void register1(final Eventual<P> e) {

	}

	static class FluffyCompImplInjector {
		public FluffyModuleImpl new_FluffyModuleImpl(final OS_Module aModule, final EDL_Compilation aComp) {
			return new FluffyModuleImpl(aModule, aComp);
		}
	}

}

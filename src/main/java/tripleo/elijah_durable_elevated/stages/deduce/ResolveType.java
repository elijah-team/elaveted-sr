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
import tripleo.elijah.contexts.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.logging.*;
import tripleo.elijah_durable_elevated.contexts.*;
import tripleo.elijah_durable_elevated.lang.impl.*;
import tripleo.elijah_durable_elevated.lang.types.*;
import tripleo.elijah_durable_elevated.stages.deduce.post_bytecode.*;
import tripleo.elijah_durable_elevated.stages.gen_fn.*;
import tripleo.elijah_fluffy.diagnostic.*;
import tripleo.elijah_fluffy.util.*;

import java.util.function.*;

/**
 * Created 11/18/21 10:51 PM
 */
public enum ResolveType {
	;

	private final ResolveTypeInjector __inj = new ResolveTypeInjector();

	public static @NotNull GenType resolve_type(final @NotNull OS_Module module,
												final @NotNull OS_Type type,
												final Context ctx,
												final @NotNull ElLog LOG,
												final @NotNull DeduceTypes2 dt2) throws ResolveError {
		@NotNull
		GenType R = new GenTypeImpl();
		if (type.getType() != OS_Type.Type.USER_CLASS)
			R.setTypeName(type);

		final EB_Type ebType = dt2.ebLookupType(type);
		assert ebType != null;
		ebType.resolveType(module, ctx, LOG, R);

		switch (type.getType()) {

		case BUILT_IN:
			//resolve_built_in(module, type, dt2, R);
			assert false;
			break;
		case USER:
			//resolve_user2(type, LOG, dt2, R::copy);
			assert false;
			break;
		case USER_CLASS:
			//R.setResolved(type);
			assert false;
			break;
		case FUNCTION:
			assert false;
			break;
		case FUNC_EXPR:
			//R.setResolved(type);// ((OS_FuncExprType)type).getElement();
			assert false;
			break;
		default:
			throw new IllegalStateException("565 Unexpected value: " + type.getType());
		}

		return R;
	}

	static void resolve_built_in(final @NotNull OS_Module module,
								 final @NotNull OS_Type type,
								 final @NotNull DeduceTypes2 dt2,
								 final @NotNull GenType aR) throws ResolveError {
		switch (type.getBType()) {
		case SystemInteger:
			_resolve_built_in__SystemInteger(module, type, dt2, aR);
			break;
		case String_:
			_resolve_built_in__String_(module, type, dt2, aR);
			break;
		case SystemCharacter:
			_resolve_built_in__SystemCharacter(module, type, dt2, aR);
			break;
		case Boolean:
			_resolve_built_in__Boolean(module, aR, dt2);
			break;
		default:
			throw new IllegalStateException("531 Unexpected value: " + type.getBType());
		}
	}

	private static void _resolve_built_in__SystemInteger(final @NotNull OS_Module module,
														 final @NotNull OS_Type type,
														 final @NotNull DeduceTypes2 dt2,
														 final @NotNull GenType aR) throws ResolveError {
		final String name = "SystemInteger";
		dt2.doLookupSimple(module, name, best -> {
			aR.setResolved(((ClassStatement) best).getOS_Type());
		});
	}

	private static void _resolve_built_in__String_(final @NotNull OS_Module module,
												   final @NotNull OS_Type type,
												   final @NotNull DeduceTypes2 dt2,
												   final @NotNull GenType aR) {
		@NotNull
		String typeName = type.getBType().name();
		assert typeName.equals("String_");
		OS_Module prelude = module.prelude();
		if (prelude == null) // README Assume `module' IS prelude
			prelude = module;

		// TODO not sure about String
		final EB_Lookup            l  = new EB_Lookup(dt2, prelude.getContext(), "ConstString");
		final Eventual<OS_Element> ev = l.getEventual();
		ev.then(new DoneCallback<OS_Element>() {
			@Override
			public void onDone(final OS_Element best) {
				aR.setResolved((((ClassStatement) best).getOS_Type()));
			}
		});
		ev.onFail(new FailCallback<Diagnostic>() {
			@Override
			public void onFail(final Diagnostic result) {
				//throw new DiagnosticException(result); // eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
				throw new NotImplementedException("lookup failed");
			}
		});
		return;
	}

	private static void _resolve_built_in__SystemCharacter(final @NotNull OS_Module module,
														   final @NotNull OS_Type type,
														   final @NotNull DeduceTypes2 dt2,
														   final @NotNull GenType aR) throws ResolveError {
		final String name = "SystemCharacter";
		dt2.doLookupSimple(module, name, best -> {
			aR.setResolved(((ClassStatement) best).getOS_Type());
		});
	}

	private static void _resolve_built_in__Boolean(final @NotNull OS_Module module,
												   final @NotNull GenType aR,
												   final @NotNull DeduceTypes2 aDeduceTypes2) {
		final String name = "Boolean";
		aDeduceTypes2.doLookupSimple(module, name, best -> {
			aR.setResolved(((ClassStatement) best).getOS_Type()); // TODO might change to Type
		});
	}

	static void resolve_user2(final @NotNull OS_Type type,
							  final @NotNull ElLog LOG,
							  final @NotNull DeduceTypes2 dt2,
							  final @NotNull Consumer<GenType> cgt) throws ResolveError {
		resolve_user(type, LOG, dt2, cgt);
	}

	private static void resolve_user(final @NotNull OS_Type type,
									 final @NotNull ElLog LOG,
									 final @NotNull DeduceTypes2 dt2,
									 final @NotNull Consumer<GenType> cgt) throws ResolveError {
		final GenType ggg = new GenTypeImpl();

		final TypeName tn1 = type.getTypeName();
		switch (tn1.kindOfType()) {
		case NORMAL: {
			final Qualident tn = ((NormalTypeName) tn1).getRealName();

			LOG.info("799 [resolving USER type named] " + tn);

			if (tn != null) {
				final LookupResultList lrl = DeduceLookupUtils.lookupExpression(tn, tn1.getContext(), dt2);

				@Nullable
				OS_Element best = lrl.chooseBest(null);
				while (best instanceof AliasStatementImpl) {
					best = DeduceLookupUtils._resolveAlias2((AliasStatementImpl) best, dt2);
				}

				if (best == null) {
					if (tn.asSimpleString().equals("Any")) {
						/* return */
						ggg.setResolved(new OS_AnyType()); // TODO not a class
					}
					throw new ResolveError(tn1, lrl);
				}

				if (best instanceof OS_TypeNameElementImpl) {
					/* return */
					ggg.setResolved(new OS_GenericTypeNameType((OS_TypeNameElement) best)); // TODO not a
					// class
				} else
					ggg.setResolved(((ClassStatement) best).getOS_Type());
			}
			break;
		}
		case FUNCTION:
		case GENERIC:
			throw new NotImplementedException();
		case TYPE_OF: {
			final TypeOfTypeName type_of = (TypeOfTypeName) tn1;
			final Qualident      q       = type_of.typeOf();
			if (q.parts().size() == 1 && q.parts().get(0).getText().equals("self")) {
				assert type_of.getContext() instanceof ClassContext;
				ggg.setResolved(((IClassContext) type_of.getContext()).getCarrier().getOS_Type());
			}
			int y = 2;

		}
//				throw new NotImplementedException();
		break;
		default:
			throw new IllegalStateException("414 Unexpected value: " + tn1.kindOfType());
		}

		cgt.accept(ggg);
	}

	private static void resolve_user(final @NotNull IDeduceElement3 aDeduceElement3, final @NotNull OS_Type type1,
									 final @NotNull ElLog LOG, final @NotNull Consumer<GenType> cgt) throws ResolveError {
		final @NotNull OS_Type type = aDeduceElement3.genType().getResolved();
		assert type1 == type;

		// final @NotNull ElLog LOG;
		final @NotNull DeduceTypes2 dt2 = aDeduceElement3.deduceTypes2();

		resolve_user(type, LOG, dt2, cgt);
	}

	@Nullable
	static EB_Type createEBType(final OS_Type aType, final DeduceTypes2 dt2) {
		EB_Type ebType = null;
		switch (aType.getType()) {
		case BUILT_IN -> {
			ebType = new EB_Type() {
				@Override
				public void resolveType(final OS_Module aModule, final Context aCtx, final ElLog LOG, final @NotNull GenType R) throws ResolveError {
					resolve_built_in(dt2.module, aType, dt2, R);
				}
			};
		}
		case FUNC_EXPR -> {
			ebType = new EB_Type() {
				@Override
				public void resolveType(final OS_Module aModule, final Context aCtx, final ElLog LOG, final @NotNull GenType aR) throws ResolveError {
					aR.setResolved(aType);// ((OS_FuncExprType)type).getElement();
				}
			};
		}
		case FUNCTION -> {
			ebType = new EB_Type() {
				@Override
				public void resolveType(final OS_Module aModule, final Context aCtx, final ElLog LOG, final @NotNull GenType aR) throws ResolveError {
					// README do nothing
					// TODO do nothing??
					int y = 2;
				}
			};
		}
		// FIXME !!??
		//case UNIT_TYPE -> {
		//	ebType = new EB_Type() {
		//	};
		//}
		case USER -> {
			ebType = new EB_Type() {
				@Override
				public void resolveType(final OS_Module aModule, final Context aCtx, final ElLog LOG, final @NotNull GenType R) throws ResolveError {
					resolve_user2(aType, LOG, dt2, R::copy);
				}
			};
		}
		case USER_CLASS -> {
			ebType = new EB_Type() {
				@Override
				public void resolveType(final OS_Module aModule, final Context aCtx, final ElLog LOG, final @NotNull GenType aR) throws ResolveError {
					aR.setResolved(aType);
				}
			};
		}
		}
		return ebType;
	}

	ResolveTypeInjector _inj() {
		return __inj;
	}

	static class ResolveTypeInjector {
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//

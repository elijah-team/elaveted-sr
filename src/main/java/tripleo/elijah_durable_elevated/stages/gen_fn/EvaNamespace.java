/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_elevated.stages.gen_fn;

import lombok.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.g.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.reactive.*;
import tripleo.elijah_durable_elevated.lang.impl.*;
import tripleo.elijah_durable_elevated.stages.gen_generic.*;
import tripleo.elijah_durable_elevated.util.*;
import tripleo.elijah_elevated_durable.world_impl.*;
import tripleo.elijah_fluffy.util.*;

import java.util.function.*;

/**
 * Created 12/22/20 5:39 PM
 */
public class EvaNamespace extends EvaContainerNC implements GNCoded, GEvaNamespace {
	private final OS_Module              module;
	@Getter
	private final NamespaceStatement namespaceStatement;
	private final _Reactive_EvaNamespace reactiveEvaNamespace = new _Reactive_EvaNamespace();
	public        DefaultLivingNamespace _living;

	public EvaNamespace(NamespaceStatement aNamespaceStatement, OS_Module aModule) {
		namespaceStatement = aNamespaceStatement;
		module = aModule;
	}

	public void addAccessNotation(AccessNotationImpl an) {
		throw new NotImplementedException();
	}

	public void createCtor0() {
		// TODO implement me
		FunctionDef fd = new FunctionDefImpl(namespaceStatement, namespaceStatement.getContext());
		fd.setName(Helpers0.string_to_ident("<ctor$0>"));
		Scope3Impl scope3 = new Scope3Impl(fd);
		fd.scope(scope3);
		for (VarTableEntry varTableEntry : varTable) {
			if (varTableEntry.initialValue != LangGlobals.UNASSIGNED) {
				IExpression left = varTableEntry.nameToken;
				IExpression right = varTableEntry.initialValue;

				IExpression e = ExpressionBuilder.build(left, ExpressionKind.ASSIGNMENT, right);
				scope3.add(new StatementWrapperImpl(e, fd.getContext(), fd));
			} else {
				if (getPragma("auto_construct")) {
					scope3.add(new ConstructStatementImpl(fd, fd.getContext(), varTableEntry.nameToken, null, null));
				}
			}
		}
	}

	private boolean getPragma(String auto_construct) { // TODO this should be part of ContextImpl
		return false;
	}

	@Override
	public void generateCode(final GenerateResultEnv aFileGen, final CodeGenerator aGgc) {
		throw new NotImplementedException();
	}

	@Override
	public int getCode() {
		return _living.getCode();
	}

	@Override
	public void setCode(final int aCode) {
		_living.setCode(aCode);
	}

	@Override
	public OS_Element getElement() {
		return getNamespaceStatement();
	}

	public NamespaceStatement getNamespaceStatement() {
		// 24/01/04 back and forth
		return this.namespaceStatement;
	}

	public DefaultLivingNamespace getLiving() {
		return _living;
	}

	public String getName() {
		return namespaceStatement.getName();
	}

//	@Override
//	public @NotNull Maybe<VarTableEntry> getVariable(String aVarName) {
//		for (VarTableEntry varTableEntry : varTable) {
//			if (varTableEntry.nameToken.getText().equals(aVarName))
//				return new Maybe<>(varTableEntry, null);
//		}
//		return new Maybe<>(null, _def_VarNotFound);
//	}

	@Override
	public @NotNull Role getRole() {
		return Role.NAMESPACE;
	}

	@Override
	public void register(final @NotNull ICodeRegistrar aCr) {
		aCr.registerNamespace(this);
	}

	@Override
	public String identityString() {
		return String.valueOf(namespaceStatement);
	}

	@Override
	public OS_Module module() {
		return module;
	}

	public Reactive reactive() {
		return reactiveEvaNamespace;
	}

	class _Reactive_EvaNamespace extends DefaultReactive {
		@Override
		public <T> void addListener(final Consumer<T> t) {
			throw new UnintendedUseException();
		}
	}
}

//
//
//

package tripleo.elijah_elevated_durable.parser;

import antlr.*;
import tripleo.elijah.contexts.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang2.*;
import tripleo.elijah_durable_elevated.contexts.*;
import tripleo.elijah_durable_elevated.lang.builder.*;
import tripleo.elijah_durable_elevated.lang.impl.*;
import tripleo.elijah_durable_elevated.lang.imports.*;
import tripleo.elijah_durable_elevated.lang.types.*;
import tripleo.elijah_elevated_durable.lang_model.*;
import tripleo.elijah_fluffy.util.*;

import java.util.*;

public class PConParser {
	private final Eventual<LangModel> _p_langModel = new Eventual<>();

	public AccessNotation new_AccessNotation() {
		return new AccessNotationImpl();
	}

	public InvariantStatementPart new_InvariantStatementPart(final IInvariantStatement aCr, final Token aI1) {
		return new InvariantStatementPartImpl(aCr, aI1);
	}

	public NamespaceContextImpl new_NamespaceContext(final Context aCur, final NamespaceStatement aCls) {
		return new NamespaceContextImpl(aCur, aCls);
	}

	public FunctionDef new_FunctionDef(final OS_Element aParent, final Context aCtx) {
		return new FunctionDefImpl(aParent, aCtx);
	}

	public NormalTypeName new_RegularTypeName(final Context aCur) {
		return new RegularTypeNameImpl(aCur);
	}

	public Postcondition new_Postcondition() {
		return new PostconditionImpl();
	}

	public IExpression new_GetItemExpression(final IExpression aEe, final IExpression aExpr) {
		return new GetItemExpressionImpl(aEe, aExpr);
	}

	public IExpression new_SetItemExpression(final GetItemExpression aEe, final IExpression aExpr) {
		return new SetItemExpressionImpl(aEe, aExpr);
	}

	public TypeCastExpression new_TypeCastExpression() {
		return new TypeCastExpressionImpl();
	}

	public Precondition new_Precondition() {
		return new PreconditionImpl();
	}

	public IExpression new_SubExpression(final IExpression aEe) {
		return new SubExpressionImpl(aEe);
	}

	public FuncExpr new_FuncExpr() {
		return new FuncExprImpl();
	}

	public IExpression new_ListExpression() {
		return new ListExpressionImpl();
	}

	public ExpressionList new_ExpressionList() {
		return new ExpressionListImpl();
	}

	public ModuleContext new_ModuleContext(final OS_Module aModule) {
		return new ModuleContextImpl(aModule);
	}

	public Context new_PackageContext(final Context aCur, final OS_Package aPkg) {
		return new PackageContext(aCur, aPkg);
	}

	public NamespaceStatement new_NamespaceStatement(final OS_Element aCont, final Context aCur) {
		return new NamespaceStatementImpl(aCont, aCur);
	}

	public Qualident new_Qualident() {
		return new QualidentImpl();
	}

	public QualidentList new_QualidentList() {
		return new QualidentListImpl();
	}

	public OS_Type new_OS_BuiltinType(final BuiltInTypes aBuiltInTypes) {
		return new OS_BuiltinType(aBuiltInTypes);
	}

	public IExpression new_TypeCheckExpression(final IExpression aEe, final TypeName aTn) {
		return new TypeCheckExpressionImpl(aEe, aTn);
	}

	public Scope3 new_Scope3(final OS_Element aParent) {
		return new Scope3Impl(aParent);
	}

	public SyntacticBlock new_SyntacticBlock(final OS_Element aParent) {
		return new SyntacticBlockImpl(aParent);
	}

	public SyntacticBlockContext new_SyntacticBlockContext(final SyntacticBlock aSb, final Context aCur) {
		return new SyntacticBlockContext(aSb, aCur);
	}

	public TypeAliasBuilder new_TypeAliasBuilder() {
		return new TypeAliasBuilder();
	}

	public TypeNameList new_TypeNameList() {
		return new TypeNameListImpl();
	}

	public TypeOfTypeName new_TypeOfTypeName(final Context aCur) {
		return new TypeOfTypeNameImpl(aCur);
	}

	public IExpression new_UnaryExpression(final ExpressionKind aExpressionKind, final IExpression aEe) {
		return new UnaryExpressionImpl(aExpressionKind, aEe);
	}

	public LoopContextImpl new_LoopContext(final Context aCur, final Loop aLoop) {
		return new LoopContextImpl(aCur, aLoop);
	}

	public WithStatement new_WithStatement(final OS_Element aParent) {
		return new WithStatementImpl(aParent);
	}

	public IWithContext new_WithContext(final WithStatement aWs, final Context aCur) {
		return new WithContext(aWs, aCur);
	}

	public AliasStatement new_AliasStatement(final OS_Element aCont) {
		final AliasStatementImpl res = new AliasStatementImpl(aCont);
		_p_langModel.then(Slm -> Slm.register(res));
		return res;
	}

	public AnnotationClause new_AnnotationClause() {
		return new AnnotationClauseImpl();
	}

	public AnnotationPart new_AnnotationPart() {
		return new AnnotationPartImpl();
	}

	public ClassHeader new_ClassHeader(final boolean aExtends, final List<AnnotationClause> aAs) {
		return new ClassHeaderImpl(aExtends, aAs);
	}

	public ClassStatement new_ClassStatement(final OS_Element aParent, final Context aCctx) {
		return new ClassStatementImpl(aParent, aCctx);
	}

	public IExpression new_StringExpression(final Token aS) {
		return new StringExpressionImpl(aS);
	}

	public IExpression new_CharLitExpression(final Token aC) {
		return new CharLitExpressionImpl(aC);
	}

	public IExpression new_NumericExpression(final Token aN) {
		return new NumericExpressionImpl(aN);
	}

	public IExpression new_FloatExpression(final Token aF) {
		return new FloatExpressionImpl(aF);
	}

	public DefFunctionDef new_DefFunctionDef(final OS_Element aParent, final Context aCtx) {
		return new DefFunctionDefImpl(aParent, aCtx);
	}

	public IExpression new_DotExpression(final IExpression aDotExpressionLeft, final IdentExpression aDotExpressionRight) {
		return new DotExpressionImpl(aDotExpressionLeft, aDotExpressionRight);
	}

	public FormalArgList new_FormalArgList() {
		return new FormalArgListImpl();
	}

	public FuncExprContextImpl new_FuncExprContext(final Context aCur, final FuncExpr aPc) {
		return new FuncExprContextImpl(aCur, aPc);
	}

	public FunctionBody new_FunctionBodyEmpty() {
		return new FunctionBodyEmptyImpl();
	}

	public FunctionBody new_FunctionBody() {
		return new FunctionBodyImpl();
	}

	public FunctionHeader new_FunctionHeader() {
		return new FunctionHeaderImpl();
	}

	public FuncTypeName new_FuncTypeName(final Context aCur) {
		return new FuncTypeNameImpl(aCur);
	}

	public GenericTypeName new_GenericTypeName(final Context aCur) {
		return new GenericTypeNameImpl(aCur);
	}

	public IdentExpression new_IdentExpression(final Token aR1, final String aFilename, final Context aCur) {
		return new IdentExpressionImpl(aR1, aFilename, aCur);
	}

	public IdentList new_IdentList() {
		return new IdentListImpl();
	}

	public ImportStatement new_RootedImportStatement(final OS_Element aEl) {
		return new RootedImportStatement(aEl);
	}

	public ImportContext new_ImportContext(final Context aCur, final ImportStatement aPc) {
		return new ImportContext(aCur, aPc);
	}

	public ImportStatement new_AssigningImportStatement(final OS_Element aEl) {
		return new AssigningImportStatement(aEl);
	}

	public ImportStatement new_QualifiedImportStatement(final OS_Element aEl) {
		return new QualifiedImportStatement(aEl);
	}

	public ImportStatement new_NormalImportStatement(final OS_Element aEl) {
		return new NormalImportStatement(aEl);
	}

	public IndexingStatement new_IndexingStatement(final OS_Module aModule) {
		return new IndexingStatementImpl(aModule);
	}

	public IndexingItem new_IndexingItem(final Token aI1, final ExpressionList aEl) {
		return new IndexingItemImpl(aI1, aEl);
	}

	public ProcedureCallExpression new_ProcedureCallExpression(final IExpression ee, final ExpressionList el) {
		final ProcedureCallExpression pce = new_ProcedureCallExpression();
		pce.identifier(ee);
		pce.setArgs(el);
		return pce;
	}

	public ProcedureCallExpression new_ProcedureCallExpression() {
		return new ProcedureCallExpressionImpl();
	}

	public void advise(final LangModel aLangModel) {
		_p_langModel.resolve(aLangModel);
	}
}

package tripleo.elijah.nextgen.query;

//import tripleo.elijah.lang.OS_Module;
//import tripleo.elijah.world.i.LivingNode;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_durable_elevated.world.i.*;
import tripleo.elijah_fluffy.util.Operation2;

import java.io.File;
import java.util.List;
import java.util.Map;

/// TODO diff btw and Entry and a Ref (oops, obvious), but also why we are not generating any of this
public interface QueryDatabase {

	/// Stolen from: tbd (obviously rdf or datalog)
	void addTriple(QueryRef aQrMod, Object aProperty, List<String> aStringList);

	/// Stolen from: tbd (why this has outputPath is beyond me)
	void load(CP_OutputPath aOutputPath, CP_Paths aPaths);

	/// Stolen from: tbd ()
	void adding_node(LivingNode aNode);

	/// Wrap an entity (here j.io.File) in something that has a raw "pointer" and an `id` of some sort: not impl.
	QueryDatabaseWrapper<File> wrap(File aFile);

	void addEntry(QueryDatabaseEntry aEntry);

	Operation2<String> getJson(String aKey);

	// TODO seems too simple
	enum QECiType {
		_Lsp,
		_Ci
	}

	// TODO Add QEExpressionType: seems like it needs a code generator/template fo to.el.lang
	enum QEElementType {

		_FunctionDef,
		_NamespaceStatement,
		_ClassStatement,
		_Member__what_is_this,
		_MemberVariable
	}

	enum QEExpressionType {
		_IExpression//
		, _FloatExpression//
		, _TypeCheckExpression//
		, _IBinaryExpression//
		, _BasicBinaryExpression//
		, _TypeNameExpression//
		, _ToExpression//
		, _SetItemExpression//
		, _DotExpression//
		, _UNASSIGNED//
		, _NumericExpression//
		, _CharLitExpression//
		, _AbstractExpression//
		// ,_TypeCheckExpression
		, _WrappedStatementWrapper //(tripleo.elijah.stages.gen_fn)
		, _SubExpression//
		, _VariableReference//
		, _ListExpression//
		, _TypeCastExpression//
		, _GetItemExpression//
		, _UnaryExpression//
		, _StringExpression//
		// Anonymous in build() in ExpressionBuilder
		, _IdentExpression//
		, _Qualident//
		// ,_TypeCastExpression
		, _ProcedureCallExpression//
		, _FuncExpr//
		;

		// IExpression expression(){return null;}
	}

	/// fixme
	interface CP_OutputPath {
	}

	/// fixme
	interface CP_Paths {
		CP_OutputPath getOutputPath();
	}

	/// Translate a path-separated string (want nix style /aa/"aa.ss. dd.ff"/qq)
	/// to json stored on disk  Operation2<String> getJson( String aKey);
	interface QueryRef {
		Object principal();

		String getResourceString();
	}

	interface QrMod extends QueryRef {
		@Override
		OS_Module principal();
	}

	// Query "Entries"
	sealed interface QueryDatabaseEntry permits QE_Entry,//
												QE_Run, //
												QE_Compilation, //
												QE_SourceTree, //
												QE_GitBranch {
	}

	non-sealed interface QE_Entry extends QueryDatabaseEntry {
		String getNameString();

		// one of: element/expression*/ci
		// also thinking java-something-type
		// ...
		@Nullable QEElementType getElementType();

		@Nullable QEExpressionType getExpressionType();

		@Nullable QECiType getCiType();
	}

	non-sealed interface QE_Run extends QueryDatabaseEntry {
		// being lazy. kinda fits, tho.
		List<String> getInputs();

		// being lazy.
		List<String> getMarkers();

		// being lazy.
		List<String> getLogs();

		// being lazy.
		List<String> getOutputTree();

		// fixme also something about test compilation
		//  could be test vs. compilation
	}

	// don't think, just type. (get rid of notes)
	// orig notes have yes to Run, no to Compilation (told you)
	// orig notes have test lib std perl-type
	non-sealed interface QE_Compilation extends QueryDatabaseEntry {
		String getStringId();

		int getIntId();

		// aka CP_Path
		Map<String, String> getRoots();
	}

	interface QE_SourceTreeFormat  {String asString();}

	/// I have no idea what this is
	/// correction: This represents an entry that can be pulled from a CK_*Connection
	non-sealed interface QE_SourceTree extends QueryDatabaseEntry {
		// getFormat? ie comp-hash vs integrated/embedded?
		QE_SourceTreeFormat format();

		// kind of fakeout git type thing
		List<?> tree();
	}

	/// This specifically references git because jgit (and current impl.)
	non-sealed interface QE_GitBranch extends QueryDatabaseEntry {
		byte export();

		String getName();

		// w/ special/addition/removals
		List<?> tree();

		// commit*/stacks*(is this a list of patch files?)/~*
		List<?> getPathRefs();
	}
}

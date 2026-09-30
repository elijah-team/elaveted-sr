package tripleo.elijah.nextgen.query;

import tripleo.elijah_durable_elevated.world.i.*;
import tripleo.elijah_fluffy.diagnostic.*;
import tripleo.elijah_fluffy.util.*;

import java.io.*;
import java.util.*;

public class QueryDatabaseImpl implements QueryDatabase {
	@Override
	public void load(final CP_OutputPath aOutputPath, final CP_Paths aPaths) {
		throw new ImplementMe();
	}

	@Override
	public void adding_node(final LivingNode aNode) {
		throw new ImplementMe();
	}

	@Override
	public void addTriple(final QueryRef aQrMod, final Object aProperty, final List<String> aStringList) {
		throw new ImplementMe();
	}

	@Override
	public QueryDatabaseWrapper<File> wrap(final File aFile) {
		return new QueryDatabaseWrapper<File>() {
			@Override
			public File raw() {
				return aFile;
			}
		};
	}

	@Override
	public void addEntry(QueryDatabaseEntry aEntry) {
		throw new ImplementMe();
	}


	@Override
	public Operation2<String> getJson(final String aKey) {
		return Operation2.failure(ElDiagnostic.withMessage("-1", "QueryDatabase::getJson", ElDiagnostic.Severity.WARN));
	}
}

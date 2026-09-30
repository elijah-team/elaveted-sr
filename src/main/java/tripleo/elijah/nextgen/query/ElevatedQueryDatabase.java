package tripleo.elijah.nextgen.query;

// import org.apache.jena.rdf.model.Model;
// import org.apache.jena.rdf.model.ModelFactory;
// import org.apache.jena.rdf.model.Resource;
// import org.apache.jena.riot.RDFDataMgr;
// import org.apache.jena.riot.RiotException;
// import org.apache.jena.riot.RiotNotFoundException;
// import org.apache.jena.vocabulary.VCARD;

import org.jdeferred2.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.lang.*;
//import tripleo.elijah.stages.deduce.fluffy.i.*;
//import tripleo.elijah.world.i.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_durable_elevated.stages.deduce.fluffy.i.*;
import tripleo.elijah_durable_elevated.world.i.*;
import tripleo.elijah_fluffy.anno.*;
import tripleo.elijah_fluffy.util.*;

import java.io.*;
import java.util.*;

public class ElevatedQueryDatabase implements QueryDatabase {
	private static int             mono   = 1;
	private final  Eventual<Model> modelP = new Eventual<>();
	private final String     inputFileName;
	private final FluffyComp fluffyComp;

	public ElevatedQueryDatabase(final FluffyComp aFluffyComp) {
		fluffyComp = aFluffyComp;
		/*private*/ /*final*/
		Model model;
		// model      = ModelFactory.createDefaultModel();
		// modelP.resolve(model);
		inputFileName = "QD-top-model.rdf";
	}

	public static QrMod modRef(OS_Module aMod) {
		return new QrMod() {
			@Override
			public OS_Module principal() {
				return aMod;
			}

			@Override
			public String getResourceString() {
				return "" + (++mono);
			}
		};
	}

	@Override
	public void addTriple(final QueryRef aQueryRef, final Object aProperty, final List<String> aStringList) {
		int y = 2;
		modelP.then(new DoneCallback<Model>() {
			@Override
			public void onDone(final Model model) {
				final Resource resource = model.createResource(aQueryRef.getResourceString());
				model.createResource(resource);
			}
		});
	}

	@Override
	public void load(final CP_OutputPath aOutputPath, final CP_Paths aPaths) {
		modelP.then(model -> {
			if (!new File(inputFileName).exists()) {
				__408_Writing_empty_top_model(inputFileName, model, aPaths, fluffyComp);
			} else {
				try (InputStream in = RDFDataMgr.open(inputFileName)) {
					if (in == null) {
						throw new NeverReached();
						// throw new IllegalArgumentException("417 File: " + inputFileName + " not found");
					}

					model.read(in, null);
				} catch (IOException exc) {
					System.err.println("403 " + exc);
				} catch (RiotException exc) {
					if (exc instanceof RiotNotFoundException rnfe) {
						throw new NeverReached();
						// __408_Writing_empty_top_model(inputFileName, model);
					} else {
						System.err.println("406 " + exc);
					}
				}
			}

			model.write(System.out);
			int y = 2;
		});
	}

	@Override
	public void adding_node(final LivingNode aNode) {
		int    y          = 2;
		String personURI  = "http://somewhere/JohnSmith";
		String givenName  = "John";
		String familyName = "Smith";
		String fullName   = givenName + " " + familyName;

		Model model = ModelFactory.createDefaultModel();

		Resource johnSmith = model.createResource(personURI)
				.addProperty(VCARD.FN, fullName)
				.addProperty(VCARD.N, model.createResource()
						.addProperty(VCARD.Given, givenName)
						.addProperty(VCARD.Family, familyName));


	}

	@Override
	public QueryDatabaseWrapper<File> wrap(final File aFile) {
		throw new ImplementMe();
	}

	@Override
	public void addEntry(final QueryDatabaseEntry aEntry) {
		throw new ImplementMe();
	}

	@Override
	public Operation2<String> getJson(final String aKey) {
		throw new ImplementMe();
	}

	private static void __408_Writing_empty_top_model(final String aInputFileName,
	                                                  final @NotNull Model aModel,
	                                                  final CP_Paths aPaths,
	                                                  final FluffyComp aFluffyComp) {
		throw new ImplementMe();
	}

	public Eventual<Model> getModelPromise() {
		return modelP;
	}

	interface Model {
		void read(InputStream aIn, Object aO);

		void write(PrintStream aOut);

		Resource createResource(String aPersonURI);

		Resource createResource();

		Resource createResource(Resource aResource);
	}

	interface RDFDataMgr {
		static InputStream open(String aInputFileName) {
			throw new ImplementMe();
		}
	}

	interface Resource {
		default Resource addProperty(Resource aFn, String aFullName) {
			return this/*??*/;
		}

		default Resource addProperty(Resource aN, Resource aResource){
			return this/*??*/;
		}
	}

	static class RiotNotFoundException extends RiotException {
	}

	interface ModelFactory {
		static Model createDefaultModel() {
			throw new ImplementMe();
		}
	}

	static class VCARD {
		public static final Resource N  = new Resource() {
		};
		static final        Resource FN = new Resource() {
		};
		static final        Resource Given = new Resource() {
		};
		static final        Resource Family = new Resource() {
		};
	}

	static class RiotException extends RuntimeException {
	}
}

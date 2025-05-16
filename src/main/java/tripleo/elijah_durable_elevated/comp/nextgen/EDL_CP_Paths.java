package tripleo.elijah_durable_elevated.comp.nextgen;

//import io.smallrye.mutiny.*;

import clojure.lang.*;
import org.jdeferred2.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.nextgen.*;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.paths.*;

import java.util.*;

@SuppressWarnings("CommentedOutCode")
public class EDL_CP_Paths implements CP_Paths {
	@SuppressWarnings("FieldCanBeLocal")
	private final          EDL_ICompilation _c;
	private final @NotNull CP_StdlibPath    stdlibRoot;
	private final          CP_OutputPath    outputRoot;
	private final @NotNull List<ER_Node>    outputNodes = new ArrayList<>();

	public EDL_CP_Paths(final EDL_ICompilation aC) {
		_c         = aC;
		outputRoot = new CP_OutputPath(_c);
		stdlibRoot = new CP_StdlibPath__(_c);
	}

	@Override
	public void addNode(CP_RootType t, final ER_Node aNode) {
//		if (aNode.getPath().)
		if (t == CP_RootType.OUTPUT) {
			outputNodes.add(aNode);
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public CP_Path outputRoot() {
		return outputRoot;
	}

	@Override
	public CP_Path preludeRoot() {
		throw new UnintendedUseException("TODO 12/07 implement me");
	}

	@Override
	public void renderNodes() {
		outputRoot._renderNodes(outputNodes);
	}

	@Override
	public void signalCalculateFinishParse() {
		outputRoot.signalCalculateFinishParse();
	}

	@Override
	public @NotNull CP_StdlibPath stdlibRoot() {
		return stdlibRoot;
	}

	@Override
	public CP_Path sourcesRoot() {
		throw new UnintendedUseException("TODO 12/07 implement me");
	}

	@Override
	public void subscribeCalculateFinishParse(CPX_CalculateFinishParse cp_OutputPath) {
		//__CPX_CalculateFinishParse
		//	.onItem()
		//	.invoke((x)->{
		//		cp_OutputPath.notify_CPX_CalculateFinishParse(x);
		//	});
		_c.onConfig(new DoneCallback<IPersistentMap>() {
			@Override
			public void onDone(final IPersistentMap result) {
				final String S = "CompilerController-deref";
				final Object atom0 = result.valAt(S, null);
				if (atom0 != null) {
					if (atom0 instanceof IAtom) {
						IAtom atom = (IAtom) atom0;
						int y=2;
					} else if (atom0 instanceof IFn fn) {
						int y=2;
					}
					int y=2;
				}
				//throw new UnintendedUseException("removed mutiny");
			}
		});
	}

	//Uni<Ok> __CPX_CalculateFinishParse = Uni.createFrom().publisher(__CPX_CalculateFinishParse__publisher());//.item(Ok.instance());
	//
	//private Publisher<Ok> __CPX_CalculateFinishParse__publisher() {
	//	return new Publisher<Ok>() {
	//		@Override
	//		public void subscribe(Subscriber<? super Ok> subscriber) {
	//			throw new UnintendedUseException("TODO 12/28 dpys, implement me");
	//		}
	//	};
	//}
}

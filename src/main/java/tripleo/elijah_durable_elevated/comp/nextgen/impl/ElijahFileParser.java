package tripleo.elijah_durable_elevated.comp.nextgen.impl;

import lombok.*;
import org.jdeferred2.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.compiler_model.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_durable_elevated.comp.nextgen.*;
import tripleo.elijah_elevated_durable.compiler_model.*;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_fluffy.anno.*;
import tripleo.elijah_fluffy.diagnostic.*;
import tripleo.elijah_fluffy.util.*;

import java.util.*;

public class ElijahFileParser {
	private final       Eventual<OS_Module>     ev;
	private final       Eventual<EDL_CM_Module> cmModP;
	@Getter
	private @ElLateInit Operation2<OS_Module>   op;
	private             ElijahCache             elijahCache;
	private             EDL_ICompilation        compilation;

	public ElijahFileParser() {
		ev     = new Eventual<>();
		cmModP = new Eventual<>();
		ev.then(Sci -> {
			op = Operation2.success(Sci);
		});
		ev.onFail(new FailCallback<Diagnostic>() {
			@Override
			public void onFail(final Diagnostic d) {
				op = Operation2.failure(d);
			}
		});
	}

	public void configure(final ElijahCache aElijahCache) {
		elijahCache = aElijahCache;
	}

	@SuppressWarnings("SuspiciousGetterSetter")
	public Eventual<OS_Module> getEventual() {
		return ev;
	}

	public Eventual<OS_Module> parse(final ElijahSpec spec, final EDL_ICompilation aCompilation) {
		if (compilation != null) {
			if (compilation != aCompilation) {
				throw new AssertionError();
			}
		} else {
			configure(aCompilation);
		}
		return parse(spec);
	}

	public void configure(final EDL_ICompilation aCompilation) {
		compilation = aCompilation;
		ev.then(Smod -> {
			final CM_Module cmm = compilation.megaGrande(Smod);
			cmModP.resolve((EDL_CM_Module) cmm);
		});
	}

	public Eventual<OS_Module> parse(final ElijahSpec aElijahSpec) {
		final String              absolutePath = aElijahSpec.getLongPath2();
		final Optional<OS_Module> early        = elijahCache.get(absolutePath);

		if (early.isPresent()) {
			ev.resolve(early.get());
			//ev // README will never reject
			return ev;
		}

		final Eventual<OS_Module> omP = CX_ParseElijahFile.parseAndCache2(aElijahSpec, elijahCache, absolutePath, compilation);
		omP.then(ev::resolve);
		omP.onFail(ev::reject);
		return ev; // NOTE not omP!
	}
}

/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_elevated.comp;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_elevated_durable.parser.*;
import tripleo.elijah_elevated_durable.parser.antlr2.*;
import tripleo.elijah_fluffy.util.*;

public class Out {
	private final @NotNull ParserClosure pc;

	public Out(final String fn, final @NotNull EDL_ICompilation aCompilation, final ElijjahParser aParser) {
		final PConParser pcon = new PConParser();
		pcon.advise(aCompilation.langModel());
		aParser.pcon = pcon;
		aParser.out  = this;
		pc           = new ParserClosureImpl(fn, aCompilation, pcon);
	}

	public @NotNull ParserClosure closure() {
		return pc;
	}

	//@edu.umd.cs.findbugs.annotations.SuppressFBWarnings("NM_METHOD_NAMING_CONVENTION")
	public void FinishModule() {
		pc.module().finish();
	}

	public @NotNull OS_Module module() {
		return pc.module();
	}

	/*
	 * Created on Sep 1, 2005 20:16:32
	 */
	public static class ParserClosureImpl implements tripleo.elijah.lang.i.ParserClosure {
		public final OS_Module module;
		//private final PConParser pcon;

		public ParserClosureImpl(final String fn, @NotNull final EDL_ICompilation compilation, final PConParser aPConParser) {
			//pcon = aPConParser;
			var c2 = (EDL_ICompilation) compilation;
			module = c2.moduleBuilder().withFileName(fn).addToCompilation().build();
		}

		@Override
		public OS_Package defaultPackageName(final Qualident aPackageName) {
			return module.pushPackageNamed(aPackageName);
		}

		@Override
		public @NotNull OS_Module module() {
			return module;
		}

		@Override
		public AliasStatement aliasStatement(final OS_Element aParent) {
			throw new UnintendedUseException("Not impl in elaveted");
		}

		@Override
		public ClassStatement classStatement(final OS_Element aParent, final Context ctx) {
			throw new UnintendedUseException("Not impl in elaveted");
		}

		@Override
		public NamespaceStatement namespaceStatement(final OS_Element aParent, final Context ctx) {
			throw new UnintendedUseException("Not impl in elaveted");
		}
	}

}

//
//
//

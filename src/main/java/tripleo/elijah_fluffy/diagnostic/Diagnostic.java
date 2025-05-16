/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */

package tripleo.elijah_fluffy.diagnostic;

import org.jetbrains.annotations.*;

import java.io.*;
import java.util.*;

/**
 * Created 12/26/20 5:31 AM
 */
public interface Diagnostic {
	static @NotNull Diagnostic withMessage(@NotNull String code, String string, @NotNull Severity severity) {
		return new Diagnostic() {

			@Override
			public String code() {
				return code;
			}

			@Override
			public @NotNull TextLocatable primary() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void report(@NotNull PrintStream stream) {
				// TODO Auto-generated method stub
				stream.printf("%s %s %n", code(), string);
			}

			@Override
			public @NotNull List<TextLocatable> secondary() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Severity severity() {
				return severity;
			}
		};
	}

	@Nullable
	String code();

	@NotNull
	TextLocatable primary();

	void report(PrintStream stream);

	@NotNull
	List<TextLocatable> secondary();

	@Nullable
	Severity severity();

	/**
	 * Want to
	 */
	default Object get() {
		return null;
	}

	enum Severity {
		INFO, LINT, WARN, FATAL, ERROR
	}
}

//
//
//

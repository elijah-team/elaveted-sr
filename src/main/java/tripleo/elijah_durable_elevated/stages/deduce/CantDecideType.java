/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_elevated.stages.deduce;

import org.jetbrains.annotations.*;
import tripleo.elijah_durable_elevated.lang.impl.*;
import tripleo.elijah_durable_elevated.stages.gen_fn.*;
import tripleo.elijah_fluffy.diagnostic.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

/**
 * Created 4/13/21 5:46 AM
 */
public class CantDecideType implements Diagnostic {
	private final @NotNull Collection<TypeTableEntry> types;
	private final          VariableTableEntry         vte;

	public CantDecideType(VariableTableEntry aVte, @NotNull Collection<TypeTableEntry> aTypes) {
		vte = aVte;
		types = aTypes;
	}

	@Override
	public @NotNull String code() {
		return "E1001";
	}

	private @NotNull String message() {
		return "Can't decide type";
	}

	@Override
	public @NotNull TextLocatable primary() {
		VariableStatementImpl vs;
		vs = (VariableStatementImpl) (vte.getResolvedElement());
		if (vs == null) {
			throw new AssertionError();
		}
		return vs;
	}

	@Override
	public void report(@NotNull PrintStream stream) {
		stream.printf("---[%s]---: %s%n", code(), message());
		// linecache.print(primary);
		for (TextLocatable sec : secondary()) {
			// linecache.print(sec)
		}
		stream.flush();
	}

	@Override
	public @NotNull List<TextLocatable> secondary() {
		final List<TextLocatable> c = types.stream()
				.map((TypeTableEntry input) -> {
					// return input.attached.getElement(); // TODO All elements should be Locatable
					// return (TypeName)input.attached.getTypename();
					return (TextLocatable) null;
		}).collect(Collectors.toList());

		return c;
	}

	@Override
	public @NotNull Severity severity() {
		return Severity.ERROR;
	}
}

//
//
//

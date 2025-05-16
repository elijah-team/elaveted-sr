/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_elevated.entrypoints;

import lombok.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.entrypoints.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_durable_elevated.lang.impl.*;
import tripleo.elijah_elevated_durable.lang_model.*;

import java.util.*;

/**
 * Created 6/14/21 7:28 AM
 */
public class MainClassEntryPoint implements EntryPoint {
	public @NotNull ClassStatement getKlass() {
		return klass;
	}

	private final @NotNull ClassStatement klass;
	private                FunctionDef    main_function;

	public MainClassEntryPoint(@NotNull ClassStatement aKlass) {
		final List<OS_NamedElement> main = aKlass.findFunction("main");
		for (OS_NamedElement classItem : main) {
			FunctionDef fd = (FunctionDef) classItem;
			boolean     return_type_is_null;
			final TypeName typeName = fd.returnType();
			if (typeName == null)
				return_type_is_null = true;
			else
				return_type_is_null = typeName.isNull();
			if (fd.getArgs().isEmpty() && return_type_is_null) {
				main_function = fd;
			}
		}
		if (main_function == null) {
			throw new IllegalArgumentException("Class does not define main");
		}
		klass = aKlass;
	}

	public static boolean is_main_function_with_no_args(@NotNull FunctionDef aFunctionDef) {
		switch (aFunctionDef.getSpecies()) {
		case REG_FUN:
		case DEF_FUN:
			if (aFunctionDef.name().equals("main")) {
				return !aFunctionDef.getArgs().iterator().hasNext();
			}
			break;
		default:
			throw new IllegalStateException("Error");
		}
		return false;
	}

	public static boolean isMainClass(@NotNull ClassStatement classStatement) {
		// TODO what about Library (for windows dlls) etc?
		final EN_Name enName = classStatement.getEnName();
		final boolean b1     = enName.sameName("Main");
		final boolean b2     = classStatement.getPackageName() == LangGlobals.default_package;
		final boolean b3     = b2 && b1;
		final boolean b4     = enName.hasUnderstanding(ENU_MainClass.class);
		if (b3 && !b4) {
			System.err.println("no understanding present when it should be");
		}
		return b3;
	}

	public FunctionDef getMainFunction() {
		return main_function;
	}

	//public @NotNull ClassStatement getKlass() {
	//	// 24/01/04 back and forth
	//	return this.klass;
	//}
}

//
//
//

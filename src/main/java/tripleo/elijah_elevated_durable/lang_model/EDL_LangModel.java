package tripleo.elijah_elevated_durable.lang_model;

import antlr.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_durable_elevated.lang.impl.*;
import tripleo.elijah_fluffy.util.*;

import java.util.*;

public class EDL_LangModel implements LangModel {
	private final Map<OS_NamedElement, EN_Name> names = new HashMap<>();

	@Override
	public void assertUnderstanding(final OS_Element aElement, final EN_Understanding aUnderstanding) {
		if (aElement instanceof IdentExpression) {
			final IdentExpression identExpression = (IdentExpression) aElement;
			identExpression.getName().addUnderstanding(aUnderstanding);
			return;
		}

		throw new NotImplementedException("Don't know what you want.");
	}

	public void assertUnderstanding(@NotNull EN_Name aName, EN_Understanding u) {
		aName.addUnderstanding(u);
	}

	@Override
	public EN_Name createName(final String aName, final OS_NamedElement aElement) {
		//noinspection UnnecessaryLocalVariable
		final EN_Name name = names.computeIfAbsent(aElement, (__$$) -> create(aName));
		return name;
	}

	@Override
	public void register(final AliasStatementImpl aAliasStatement) {
		aAliasStatement.resolveLangModel(this);
	}

	@Contract(value = "_ -> new", pure = true)
	public static @NotNull EN_Name create(@NotNull String name) {
		return new EDL_EN_Name(name);
	}

	@Contract(value = "_ -> new", pure = true)
	public static @NotNull EN_Name create(@NotNull Token aToken) {
		var name = aToken.getText();
		return new EDL_EN_Name(name);
	}
}

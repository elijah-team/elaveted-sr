package tripleo.elijah_elevated_durable.lang_model;

import org.jetbrains.annotations.*;
import tripleo.elijah_fluffy.util.*;

import java.util.*;

class EDL_EN_Name implements EN_Name {
	private final @NotNull String                 name;
	private @NotNull       List<EN_Usage>         usages;
	private @NotNull       List<EN_Understanding> understandings;
	private @NotNull       Eventual<EN_Type>      typePromise;

	public EDL_EN_Name(final @NotNull String aName) {
		name           = aName;
		usages         = new LinkedList<>();
		understandings = new LinkedList<>();
		typePromise    = new Eventual<>();
	}

	@Override
	public void addUnderstanding(final EN_Understanding u) {
		understandings.add(u);
	}

	@Override
	public void addUsage(EN_Usage aUsage) {
		usages.add(aUsage);
	}

	@Override
	public String getText() {
		return name;
	}

	@Override
	public Eventual<EN_Type> getType() {
		return typePromise;
	}

	@Override
	public List<EN_Understanding> getUnderstandings() {
		return understandings;
	}

	@Override
	public List<EN_Usage> getUsages() {
		return usages;
	}

	@Override
	public boolean hasUnderstanding(@SuppressWarnings("rawtypes") @NotNull Class className) {
		for (EN_Understanding und : understandings) {
			if (className.isInstance(und)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean sameName(final String aMain) {
		return Objects.equals(aMain, getText());
	}
}

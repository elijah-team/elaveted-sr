package tripleo.elijah_elevated_durable.lang_model;

import tripleo.elijah_fluffy.util.*;

import java.util.*;

public interface EN_Name {

	void addUnderstanding(EN_Understanding u);

	void addUsage(EN_Usage deduceUsage);

	String getText();

	Eventual<EN_Type> getType();

	List<EN_Understanding> getUnderstandings();

	List<EN_Usage> getUsages();

	boolean hasUnderstanding(@SuppressWarnings("rawtypes") Class className);

	boolean sameName(String aMain);
}

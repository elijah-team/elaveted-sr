package tripleo.elijah_elevated_durable.names_impl;

import org.jetbrains.annotations.*;
import tripleo.elijah_elevated_durable.lang_model.*;

public record EN_NameUsage(
		EN_Name theName,
		EN_NameUsageTarget nameUsageTarget
) implements EN_Usage {

	public interface EN_NameUsageTarget {
		void record(EN_Usage usage);
		boolean isRecorded(EN_Usage usage);
	}


	@Override
	public @NotNull String toString() {
		return "EN_NameUsage[" +
				"theName=" + theName + ", " +
				"nameUsageTarget=" + nameUsageTarget + ']';
	}

}

package tripleo.elijah_elevated_durable.comp;

public record LCM_Just__CI_HASHED(
		tripleo.elijah.comp.specs.EzSpec spec,
		tripleo.elijah_fluffy.util.Operation<String> hash,
		tripleo.graph.CK_SourceFile sourceFile,
		EDL_Compilation compilation
) implements LCM_Just {
}

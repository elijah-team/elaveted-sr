package tripleo.elijah_durable_elevated.comp.functionality.f291;

import tripleo.elijah.nextgen.*;
import tripleo.elijah.nextgen.outputstatement.*;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah_congenial.pipelines.*;
import tripleo.paths.*;

@SuppressWarnings({"UtilityClassCanBeEnum", "ClassWithOnlyPrivateConstructors", "NonFinalUtilityClass", "DuplicateBranchesInSwitch"})
public class U {
	private U() {
	}

	public static CP_Path getPathForOutputFile(final EOT_OutputFile outputFile,
											   final CP_Path outputRoot,
											   final String childPath) {
		CP_Path pp;
		switch (outputFile.getType()) {
		case SOURCES -> pp = outputRoot.child("code2").child(childPath);
		case LOGS -> pp = outputRoot.child("logs").child(childPath);
		case INPUTS, BUFFERS -> pp = outputRoot.child(childPath);
		case DUMP -> pp = outputRoot.child("dump").child(childPath);
		case BUILD -> pp = outputRoot.child(childPath);
		case SWW -> pp = outputRoot.child("sww").child(childPath);
		default -> throw new IllegalStateException("Unexpected value: " + outputFile.getType());
		}
		return pp;
	}

	public static void getPathForOutputFile2(final CP_Path outputRoot,
											 final String childPath,
											 final EOT_OutputType aOutputFileType,
											 final EG_Statement seq,
											 final CP_Paths paths,
											 final NextgenFactory nextgenFactory) {
		var           pp   = getPathForOutputFile(outputRoot, childPath, aOutputFileType);
		final ER_Node node = nextgenFactory.createERNode(pp, seq);
		paths.addNode(CP_RootType.OUTPUT, node);
	}

	private static CP_Path getPathForOutputFile(final CP_Path outputRoot, final String childPath, final EOT_OutputType aOutputFileType) {
		CP_Path pp;
		switch (aOutputFileType) {
		case SOURCES -> pp = outputRoot.child("code2").child(childPath); // "code"
		case LOGS -> pp = outputRoot.child("logs").child(childPath);
		case INPUTS, BUFFERS -> pp = outputRoot.child(childPath);
		case DUMP -> pp = outputRoot.child("dump").child(childPath);
		case BUILD -> pp = outputRoot.child(childPath);
		case SWW -> pp = outputRoot.child("sww").child(childPath);
		default -> throw new IllegalStateException("Unexpected value: " + aOutputFileType);
		}
		return pp;
	}
}

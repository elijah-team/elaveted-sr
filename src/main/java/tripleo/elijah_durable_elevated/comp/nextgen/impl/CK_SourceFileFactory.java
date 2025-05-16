package tripleo.elijah_durable_elevated.comp.nextgen.impl;

import org.jetbrains.annotations.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.graph.*;
import tripleo.paths.*;

// TODO 24/01/04 CM_Factory??
public enum CK_SourceFileFactory { ;
	public static CK_SourceFile get(final tripleo.wrap.File aFile, final K aK) {
		switch (aK) {
		case SpecifiedEzFile -> {return new CK_SourceFile__SpecifiedEzFile(aFile);}
		case SpecifiedElijahFile -> {return new CK_SourceFile__SpecifiedElijahFile(aFile);}
		default -> throw new NotImplementedException("[CK_SourceFileFactory::get] Unexpected value: " + aK);
		}
	}

	public static CK_SourceFile get(final CP_Path aPath, final K aK) {
		switch (aK) {
		case SpecifiedPathEzFile -> {return new CK_SourceFile__SpecifiedEzFile(aPath.toFile());}
		case SpecifiedPathElijahFile -> {return new CK_SourceFile__SpecifiedElijahFile(aPath.toFile());}
		default -> throw new NotImplementedException("[CK_SourceFileFactory::get] Unexpected value: " + aK);
		}
	}

	public static CK_SourceFile get(java.io.File f, K specifiedEzFile) {
		assert specifiedEzFile == K.SpecifiedEzFile;
		// TODO Auto-generated method stub
		return get(tripleo.wrap.File.wrap(f), f.toString(), specifiedEzFile);
	}

	public enum K {
		SpecifiedEzFile,
		ElaboratedEzFile,
		ElaboratedElijahFile,
		SpecifiedElijahFile,
		SpecifiedPathEzFile,
		SpecifiedPathElijahFile
	}

	public static CK_SourceFile get(final @NotNull tripleo.wrap.File directory, final String file_name, final K aK) {
		return switch (aK) {
		case SpecifiedEzFile -> new CK_SourceFile__SpecifiedEzFile(new tripleo.wrap.File(directory, file_name));
		case ElaboratedEzFile -> new CK_SourceFile__ElaboratedEzFile(directory, file_name);
		case ElaboratedElijahFile ->  new CK_SourceFile__ElaboratedElijahFile(directory, file_name);
			default -> throw new NotImplementedException("[CK_SourceFileFactory::get] Unexpected value: " + aK);
		};
	}
}

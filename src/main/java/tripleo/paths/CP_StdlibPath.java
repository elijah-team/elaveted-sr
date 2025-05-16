package tripleo.paths;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah_fluffy.util.Eventual;
import tripleo.wrap.File;

import java.nio.file.Path;

public interface CP_StdlibPath extends CP_Path, _CP_RootPath {
	@Override
	CP_Path child(String aSubPath);

	@Override
	@Nullable String getName();

	@Override
	@Nullable CP_Path getParent();

	@Override
	@NotNull Path getPath();

	@Override
	@NotNull Eventual<Path> getPathPromise();

	@Override
	@Nullable File getRootFile();

	@Override
	@NotNull _CP_RootPath getRootPath();

	@Override
	@Nullable CP_SubFile subFile(String aSubFile);

	@Override
	@NotNull File toFile();

	@Override
	String toString();
}

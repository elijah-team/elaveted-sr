package tripleo.advance_baeldung.learningplatform;

import java.io.*;
import java.nio.file.*;

public class MavenProcessBuilder extends MavenExecutorAdapter {
    private static final ProcessBuilder PROCESS_BUILDER = new ProcessBuilder();

    protected int execute(Path projectFolder, String compileGoal) throws IOException, InterruptedException {
        final Path   resolve = projectFolder.resolve(POM_XML);
        final String string  = resolve.toString();
        Process process = PROCESS_BUILDER
          .command(MVN, USE_CUSTOM_POM, string, compileGoal)
          .start();
        return process.waitFor();
    }
}

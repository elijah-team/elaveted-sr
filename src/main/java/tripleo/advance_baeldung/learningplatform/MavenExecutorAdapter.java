package tripleo.advance_baeldung.learningplatform;

import java.io.*;
import java.nio.file.*;

public abstract class MavenExecutorAdapter implements Maven {

    @Override
    public void compile(Path projectFolder) {
        int exitCode;
        try {
            exitCode = execute(projectFolder, COMPILE_GOAL);
        } catch (InterruptedException e) {
            throw new MavenCompilationException("Interrupted during compilation", e);
        } catch (IOException e) {
            throw new MavenCompilationException("Incorrect execution", e);
        }
        if (exitCode != OK) {
            throw new MavenCompilationException("Failure during compilation: " + exitCode);
        }
    }

    protected abstract int execute(Path projectFolder, String compileGoal)
      throws InterruptedException, IOException;

}

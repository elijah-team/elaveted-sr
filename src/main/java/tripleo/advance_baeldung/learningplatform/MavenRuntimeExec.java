package tripleo.advance_baeldung.learningplatform;

import java.io.*;
import java.nio.file.*;

public class MavenRuntimeExec extends MavenExecutorAdapter {
    @Override
    protected int execute(Path projectFolder, String compileGoal) throws InterruptedException, IOException {
        String[] arguments = {MVN, USE_CUSTOM_POM, projectFolder.resolve(POM_XML).toString(), COMPILE_GOAL};
        Process process = Runtime.getRuntime().exec(arguments);
        return process.waitFor();
    }
}

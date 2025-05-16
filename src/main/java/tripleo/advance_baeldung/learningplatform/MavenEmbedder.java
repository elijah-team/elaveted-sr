//package com.baeldung.learningplatform;
//
//import org.apache.maven.cli.*;
//
//import java.nio.file.*;
//
//public class MavenEmbedder implements Maven {
//
//    public static final String MVN_HOME = "maven.multiModuleProjectDirectory";
//
//    @Override
//    public void compile(Path projectFolder) {
//        MavenCli cli = new MavenCli();
//        System.setProperty(MVN_HOME, projectFolder.toString());
//        cli.doMain(new String[]{COMPILE_GOAL}, projectFolder.toString(), null, null);
//    }
//}

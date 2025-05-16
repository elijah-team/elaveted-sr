//package com.baeldung.learningplatform;
//
//import org.apache.maven.shared.invoker.*;
//
//import java.nio.file.*;
//import java.util.*;
//
//public class MavenInvoker implements Maven {
//
//    @Override
//    public void compile(Path projectFolder) {
//        InvocationRequest request = new DefaultInvocationRequest();
//        request.setPomFile(projectFolder.resolve(POM_XML).toFile());
//        request.setGoals(Collections.singletonList(Maven.COMPILE_GOAL));
//        Invoker invoker = new DefaultInvoker();
//        try {
//            InvocationResult result = invoker.execute(request);
//            if (result.getExitCode() != 0) {
//                throw new MavenCompilationException("Build failed", null);//result.getExecutionException());
//            }
//        } catch (MavenInvocationException e) {
//            throw new MavenCompilationException("Exception during Maven invocation", e);
//        }
//
//    }
//}

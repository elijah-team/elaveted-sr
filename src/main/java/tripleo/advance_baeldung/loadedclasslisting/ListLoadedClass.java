package tripleo.advance_baeldung.loadedclasslisting;

import com.google.common.collect.*;
import com.google.common.reflect.*;
import com.google.common.reflect.ClassPath.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class ListLoadedClass {

    public ImmutableSet<ClassInfo> listClassLoaded() throws IOException {
        return ClassPath.from(ListLoadedClass.class.getClassLoader())
                .getAllClasses();
    }

    public Set<Class> listClassLoaded(String packageName) throws IOException {
        return ClassPath.from(ClassLoader.getSystemClassLoader()).getAllClasses().stream()
                .filter(clazz -> clazz.getPackageName().equals(packageName))
                .map(ClassInfo::load)
                .collect(Collectors.toSet());
    }

    public Set<Class> findAllClassesUsingReflectionsLibrary(String packageName) {
//        Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
//        return reflections.getSubTypesOf(Object.class)
//               .stream()
//               .collect(Collectors.toSet());
        return Set.of();
    }

}

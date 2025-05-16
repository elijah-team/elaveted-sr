package tripleo.advance_baeldung.classloader;

import java.sql.*;
import java.util.*;

public class PrintClassLoader {

    public void printClassLoaders() throws ClassNotFoundException {

        System.out.println("Classloader of this class:" + PrintClassLoader.class.getClassLoader());
        System.out.println("Classloader of DriverManager:" + DriverManager.class.getClassLoader());
        System.out.println("Classloader of ArrayList:" + ArrayList.class.getClassLoader());

    }
}

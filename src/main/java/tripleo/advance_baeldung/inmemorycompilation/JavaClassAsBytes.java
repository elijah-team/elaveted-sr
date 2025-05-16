package tripleo.advance_baeldung.inmemorycompilation;

import javax.tools.*;
import java.io.*;
import java.net.*;

/**
 * Represents a Java class file (compiled byte-code)
 */
public class JavaClassAsBytes extends SimpleJavaFileObject {

    protected final ByteArrayOutputStream bos = new ByteArrayOutputStream();

    public JavaClassAsBytes(String name, Kind kind) {
        super(URI.create("string:///" + name.replace('.', '/') + kind.extension), kind);
    }

    public byte[] getBytes() {
        return bos.toByteArray();
    }

    @Override
    public OutputStream openOutputStream() {
        return bos;
    }
}

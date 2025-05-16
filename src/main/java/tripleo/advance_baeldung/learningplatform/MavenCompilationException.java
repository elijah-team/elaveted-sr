package tripleo.advance_baeldung.learningplatform;

public class MavenCompilationException extends RuntimeException {

    public MavenCompilationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MavenCompilationException(String message) {
        super(message);
    }
}

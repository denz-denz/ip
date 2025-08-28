package denz.exception;

/**
 * Exception thrown when an invalid index is encountered in the Denz application.
 */
public class IndexException extends DenzException {

    /**
     * Constructs an {@code IndexException} with the specified detail message.
     *
     * @param message the detail message describing the cause of the exception
     */
    public IndexException(String message) {
        super(message);
    }
}

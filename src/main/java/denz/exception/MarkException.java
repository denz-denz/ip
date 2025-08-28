package denz.exception;

/**
 * Exception thrown when there is an error marking or unmarking a task
 * in the Denz application.
 */
public class MarkException extends DenzException {

    /**
     * Constructs a {@code MarkException} with the specified detail message.
     *
     * @param message the detail message describing the cause of the exception
     */
    public MarkException(String message) {
        super(message);
    }
}

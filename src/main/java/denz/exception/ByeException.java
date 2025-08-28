package denz.exception;

/**
 * Represents an exception thrown when there is an error
 * with the {@code bye} command (e.g., invalid usage).
 */
public class ByeException extends DenzException {

    /**
     * Constructs a {@code ByeException} with the specified detail message.
     *
     * @param message the detail message describing the error
     */
    public ByeException(String message) {
        super(message);
    }
}

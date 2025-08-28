package denz.exception;

/**
 * Base exception class for all custom exceptions in the Denz application.
 */
public class DenzException extends Exception {

    /**
     * Constructs a {@code DenzException} with the specified detail message.
     *
     * @param message the detail message describing the cause of the exception
     */
    public DenzException(String message) {
        super(message);
    }
}

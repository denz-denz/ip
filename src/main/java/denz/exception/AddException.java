package denz.exception;

/**
 * Represents an exception thrown when there is an error
 * while adding a new task (e.g., missing description,
 * invalid date/time).
 */
public class AddException extends DenzException {

    /**
     * Constructs an {@code AddException} with the specified detail message.
     *
     * @param message the detail message describing the error
     */
    public AddException(String message) {
        super(message);
    }
}

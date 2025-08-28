package denz.exception;

/**
 * Represents an error that occurs when a task deletion operation fails.
 */
public class DeleteException extends DenzException {

    /**
     * Constructs a {@code DeleteException} with the specified detail message.
     *
     * @param message the detail message describing why the deletion failed
     */
    public DeleteException(String message) {
        super(message);
    }
}

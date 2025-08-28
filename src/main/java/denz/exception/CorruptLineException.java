package denz.exception;

/**
 * Represents an error that occurs when a line in the save file
 * cannot be parsed correctly (i.e., the line is corrupted).
 */
public class CorruptLineException extends IllegalArgumentException {

    /**
     * Constructs a {@code CorruptLineException} with the specified detail message.
     *
     * @param message the detail message describing the corrupted line
     */
    public CorruptLineException(String message) {
        super(message);
    }
}

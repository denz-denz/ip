package denz.exception;

/**
 * FindException class represents an error in filtering a list of task by keyword
 *
 */
public class FindException extends DenzException{
    public FindException(String message) {
        super(message);
    }
}

package denz.exception;
public class CorruptLineException extends IllegalArgumentException {
    public CorruptLineException(String message) {
        super(message);
    }
}

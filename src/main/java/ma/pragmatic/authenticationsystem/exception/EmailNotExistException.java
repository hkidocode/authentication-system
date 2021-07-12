package ma.pragmatic.authenticationsystem.exception;

public class EmailNotExistException extends RuntimeException {
    public EmailNotExistException(String message) {
        super(message);
    }
}

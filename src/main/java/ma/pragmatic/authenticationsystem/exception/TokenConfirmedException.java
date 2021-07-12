package ma.pragmatic.authenticationsystem.exception;

public class TokenConfirmedException extends RuntimeException {
    public TokenConfirmedException(String message) {
        super(message);
    }
}

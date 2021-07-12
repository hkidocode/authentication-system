package ma.pragmatic.authenticationsystem.exception;

public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException(String message) {
        super(message);
    }
}

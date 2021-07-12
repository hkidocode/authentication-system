package ma.pragmatic.authenticationsystem.exception;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(String message) {
        super(message);
    }
}

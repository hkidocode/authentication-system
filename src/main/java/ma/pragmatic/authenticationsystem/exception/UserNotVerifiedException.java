package ma.pragmatic.authenticationsystem.exception;

public class UserNotVerifiedException extends RuntimeException {
    public UserNotVerifiedException(String message) {
        super(message);
    }
}

package ma.pragmatic.authenticationsystem.exception;

public class EntityNotExistException extends RuntimeException {

    public EntityNotExistException(String message) {
        super(message);
    }
}

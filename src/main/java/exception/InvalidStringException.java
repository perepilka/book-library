package exception;

public class InvalidStringException extends RuntimeException {
    public InvalidStringException(String message) {
        super(message);
    }
}

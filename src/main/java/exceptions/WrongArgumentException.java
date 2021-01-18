package exceptions;

public class WrongArgumentException extends Exception {
    public WrongArgumentException(String errorMessage) {
        super(errorMessage);
    }
}

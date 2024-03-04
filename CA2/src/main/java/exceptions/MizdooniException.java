package exceptions;

public class MizdooniException extends Exception {

    public String getMessage() {
        return super.getMessage();
    }

    public MizdooniException(String message) {
        super(message);
    }
}

package ir.ut.ie.exceptions;

public class MizdooniException extends Exception {

    private final int statusCode;

    public int getHttpStatusCode(){
        return statusCode;
    }

    public MizdooniException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public MizdooniException(String message, Exception cause, int statusCode) {
        super(message, cause);
        this.statusCode = statusCode;
    }
}

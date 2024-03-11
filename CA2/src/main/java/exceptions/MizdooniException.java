package exceptions;

public class MizdooniException extends Exception {

    private int statusCode;
    public int getHttpStatusCode(){
        return statusCode;
    }
    public String getMessage() {
        return super.getMessage();
    }

    public MizdooniException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}

package ir.ut.ie.exceptions;

import org.springframework.http.HttpStatusCode;

;

public class MizdooniException extends Exception {

    private int statusCode;

    public HttpStatusCode getHttpStatusCode(){
        return HttpStatusCode.valueOf(statusCode);
    }

    public String getMessage() {
        return super.getMessage();
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

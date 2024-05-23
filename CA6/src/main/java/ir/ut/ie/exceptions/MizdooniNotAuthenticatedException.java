package ir.ut.ie.exceptions;

public class MizdooniNotAuthenticatedException extends MizdooniException {

    public MizdooniNotAuthenticatedException() {
        super("Failed to Authenticate User", 401);
    }

    public MizdooniNotAuthenticatedException(String message){
        super("Failed to Authenticate User: " + message, 401);
    }

}




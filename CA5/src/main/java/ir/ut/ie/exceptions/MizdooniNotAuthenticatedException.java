package ir.ut.ie.exceptions;;

public class MizdooniNotAuthenticatedException extends MizdooniException {

    public MizdooniNotAuthenticatedException() {
        super("Failed to Authenticate User", 401);
    }

}




package exceptions;

public class MizdooniNotAuthorizedException extends MizdooniException {

    public MizdooniNotAuthorizedException() {
        super("Authorization Error: Insufficient Privilege", 403);
    }

    protected MizdooniNotAuthorizedException(String message){
        super(message, 403);
    }

}

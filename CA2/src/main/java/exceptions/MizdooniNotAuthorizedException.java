package exceptions;

public class MizdooniNotAuthorizedException extends MizdooniException {

    public MizdooniNotAuthorizedException() {
        super("Authorization Error: Insufficient Privilege");
    }

    protected MizdooniNotAuthorizedException(String message){
        super(message);
    }

}

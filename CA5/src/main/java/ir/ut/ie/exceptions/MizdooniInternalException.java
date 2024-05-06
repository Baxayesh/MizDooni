package ir.ut.ie.exceptions;;

public class MizdooniInternalException extends MizdooniException {

    public MizdooniInternalException(String message) {
        super(message, 500);
    }

    public MizdooniInternalException(String message, Exception cause){
        super(message, cause, 500);
    }

}
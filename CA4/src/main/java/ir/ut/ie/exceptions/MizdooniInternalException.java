package ir.ut.ie.exceptions;;

public class MizdooniInternalException extends Exception {
    public MizdooniInternalException(String message) {
        super(message);
    }

    public MizdooniInternalException(String message, Exception cause){
        super(message, cause);
    }

}
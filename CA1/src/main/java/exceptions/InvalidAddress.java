package exceptions;
import static defines.Errors.INVALID_ADDRESS;
public class InvalidAddress extends MizdooniUserException {
    public InvalidAddress(){
        super(INVALID_ADDRESS);
    }
}

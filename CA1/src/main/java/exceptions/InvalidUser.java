package exceptions;
import static defines.Errors.INVALID_USER_DATA;
public class InvalidUser extends MizdooniUserException{
    public InvalidUser(){
        super(INVALID_USER_DATA);
    }
}

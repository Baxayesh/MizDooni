package ir.ut.ie.exceptions;;
import static ir.ut.ie.defines.Errors.USER_ALREADY_EXISTS;
public class UserAlreadyExits extends MizdooniUserException {
    public UserAlreadyExits(){
    super(USER_ALREADY_EXISTS);
}
}

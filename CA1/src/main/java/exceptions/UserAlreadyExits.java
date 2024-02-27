package exceptions;
import static defines.Errors.USER_ALREADY_EXISTS;
public class UserAlreadyExits extends Exception{
public UserAlreadyExits(){
    super(USER_ALREADY_EXISTS);
}
}

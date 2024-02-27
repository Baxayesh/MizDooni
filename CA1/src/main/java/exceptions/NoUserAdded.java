package exceptions;
import static defines.Errors.INVALID_USER_DATA;
public class NoUserAdded extends Exception{
    public NoUserAdded(){
        super(INVALID_USER_DATA);
    }
}

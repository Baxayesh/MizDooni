package exceptions;

import static defines.Errors.NOT_EXISTENT_USER;


public class NotExistentUser extends MizdooniException {

    public NotExistentUser() {
        super(NOT_EXISTENT_USER);
    }

}
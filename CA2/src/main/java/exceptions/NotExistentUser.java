package exceptions;

import static defines.Errors.NOT_EXISTENT_USER;


public class NotExistentUser extends MizdooniUserException {

    public NotExistentUser() {
        super(NOT_EXISTENT_USER);
    }

}
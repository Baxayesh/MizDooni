package ir.ut.ie.exceptions;

import static ir.ut.ie.defines.Errors.NOT_EXISTENT_USER;


public class NotExistentUser extends MizdooniNotFoundException {

    public NotExistentUser() {
        super(NOT_EXISTENT_USER);
    }

}
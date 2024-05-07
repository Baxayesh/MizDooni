package ir.ut.ie.exceptions;

import static ir.ut.ie.defines.Errors.INVALID_USER_DATA;
public class InvalidUser extends MizdooniUserException{
    public InvalidUser(){
        super(INVALID_USER_DATA);
    }
}

package ir.ut.ie.exceptions;

import static ir.ut.ie.defines.Errors.INVALID_ADDRESS;

public class InvalidAddress extends MizdooniUserException {
    public InvalidAddress(){
        super(INVALID_ADDRESS);
    }
}

package ir.ut.ie.exceptions;;

import static ir.ut.ie.defines.Errors.INCORRECT_PASSWORD;

public class IncorrectPassword extends Exception {
    public IncorrectPassword() {
        super(INCORRECT_PASSWORD);
    }
}


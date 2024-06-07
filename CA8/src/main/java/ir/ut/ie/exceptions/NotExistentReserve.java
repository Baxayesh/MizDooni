package ir.ut.ie.exceptions;

import static ir.ut.ie.defines.Errors.NOT_EXISTENT_RESERVE;

public class NotExistentReserve extends MizdooniNotFoundException {

    public NotExistentReserve() {
        super(NOT_EXISTENT_RESERVE);
    }

}
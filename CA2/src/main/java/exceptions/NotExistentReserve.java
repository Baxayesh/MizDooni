package exceptions;

import static defines.Errors.NOT_EXISTENT_RESERVE;

public class NotExistentReserve extends MizdooniNotFoundException {

    public NotExistentReserve() {
        super(NOT_EXISTENT_RESERVE);
    }

}
package exceptions;

import static defines.Errors.CANCELING_CANCELED_RESERVE;
import static defines.Errors.NOT_EXISTENT_RESERVE;

public class NotExistentReserve extends MizdooniUserException {

    public NotExistentReserve() {
        super(NOT_EXISTENT_RESERVE);
    }

}
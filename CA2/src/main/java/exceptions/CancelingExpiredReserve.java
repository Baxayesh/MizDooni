package exceptions;

import static defines.Errors.CANCELING_EXPIRED_RESERVE;

public class CancelingExpiredReserve extends MizdooniUserException {

    public CancelingExpiredReserve() {

        super(CANCELING_EXPIRED_RESERVE);
    }
}
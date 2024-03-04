package exceptions;

import static defines.Errors.CANCELING_CANCELED_RESERVE;

public class CancelingCanceledReserve extends MizdooniUserException {

    public CancelingCanceledReserve() {
        super(CANCELING_CANCELED_RESERVE);
    }
}

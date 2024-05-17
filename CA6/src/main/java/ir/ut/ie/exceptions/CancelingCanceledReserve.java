package ir.ut.ie.exceptions;

import static ir.ut.ie.defines.Errors.CANCELING_CANCELED_RESERVE;

public class CancelingCanceledReserve extends MizdooniUserException {

    public CancelingCanceledReserve() {
        super(CANCELING_CANCELED_RESERVE);
    }
}

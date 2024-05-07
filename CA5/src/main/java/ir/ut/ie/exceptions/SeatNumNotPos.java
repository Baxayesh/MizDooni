package ir.ut.ie.exceptions;

import static ir.ut.ie.defines.Errors.SEAT_NUM_NOT_POS;
public class SeatNumNotPos extends MizdooniUserException {
    public SeatNumNotPos(){
        super(SEAT_NUM_NOT_POS);
    }
}

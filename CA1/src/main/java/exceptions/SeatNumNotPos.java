package exceptions;
import static defines.Errors.SEAT_NUM_NOT_POS;
public class SeatNumNotPos extends Exception{
    public SeatNumNotPos(){
        super(SEAT_NUM_NOT_POS);
    }
}

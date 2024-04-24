package ir.ut.ie.exceptions;;

import static ir.ut.ie.defines.Errors.TIME_IS_NOT_ROUND;

public class TimeIsNotRound extends MizdooniUserException {

    public TimeIsNotRound() {
         super(TIME_IS_NOT_ROUND);
    }

}
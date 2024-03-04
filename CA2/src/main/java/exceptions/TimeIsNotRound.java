package exceptions;

import static defines.Errors.TIME_IS_NOT_ROUND;

public class TimeIsNotRound extends MizdooniUserException {

    public TimeIsNotRound() {
         super(TIME_IS_NOT_ROUND);
    }

}
package ir.ut.ie.exceptions;

import static ir.ut.ie.defines.Errors.TIME_BELONGS_TO_PAST;

public class TimeBelongsToPast extends MizdooniUserException {

    public TimeBelongsToPast(){
        super(TIME_BELONGS_TO_PAST);
    }

}

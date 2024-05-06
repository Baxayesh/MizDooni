package ir.ut.ie.exceptions;;

import static ir.ut.ie.defines.Errors.NOT_IN_WORK_HOUR;

public class NotInWorkHour extends MizdooniUserException {

    public NotInWorkHour(){
        super(NOT_IN_WORK_HOUR);
    }
}

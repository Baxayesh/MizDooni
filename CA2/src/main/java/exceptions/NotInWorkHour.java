package exceptions;

import static defines.Errors.NOT_IN_WORK_HOUR;

public class NotInWorkHour extends MizdooniUserException {

    public NotInWorkHour(){
        super(NOT_IN_WORK_HOUR);
    }
}

package exceptions;

import static defines.Errors.NOT_IN_WORK_HOUR;

public class NotInWorkHour extends MizdooniException {

    public NotInWorkHour(){
        super(NOT_IN_WORK_HOUR);
    }
}

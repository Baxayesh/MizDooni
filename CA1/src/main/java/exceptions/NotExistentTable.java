package exceptions;

import static defines.Errors.NOT_EXISTENT_TABLE;

public class NotExistentTable extends MizdooniException {

    public NotExistentTable(){
        super(NOT_EXISTENT_TABLE);
    }
}

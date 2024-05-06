package ir.ut.ie.exceptions;;

import static ir.ut.ie.defines.Errors.NOT_EXISTENT_TABLE;

public class NotExistentTable extends MizdooniNotFoundException {

    public NotExistentTable(){
        super(NOT_EXISTENT_TABLE);
    }
}

package ir.ut.ie.exceptions;

import static ir.ut.ie.defines.Errors.TABLE_IS_ALREADY_RESERVED;

public class TableIsReserved extends MizdooniUserException {

    public TableIsReserved(){
        super(TABLE_IS_ALREADY_RESERVED);
    }

}



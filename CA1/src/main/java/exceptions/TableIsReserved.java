package exceptions;

import static defines.Errors.TABLE_IS_ALREADY_RESERVED;

public class TableIsReserved extends MizdooniException {

    public TableIsReserved(){
        super(TABLE_IS_ALREADY_RESERVED);
    }

}



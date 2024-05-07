package ir.ut.ie.exceptions;

import static ir.ut.ie.defines.Errors.NOT_EXISTENT_RESTAURANT;

public class NotExistentRestaurant extends MizdooniNotFoundException {

    public NotExistentRestaurant(){
        super(NOT_EXISTENT_RESTAURANT);
    }

}
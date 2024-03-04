package exceptions;

import static defines.Errors.NOT_EXISTENT_RESTAURANT;

public class NotExistentRestaurant extends MizdooniNotFoundException {

    public NotExistentRestaurant(){
        super(NOT_EXISTENT_RESTAURANT);
    }

}
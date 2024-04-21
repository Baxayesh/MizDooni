package ir.ut.ie.exceptions;;

import static ir.ut.ie.defines.Errors.ALREADY_EXISTENT_RESTAURANT;
public class RestaurantAlreadyExists extends MizdooniUserException {
    public RestaurantAlreadyExists() {
        super(ALREADY_EXISTENT_RESTAURANT);
    }
}

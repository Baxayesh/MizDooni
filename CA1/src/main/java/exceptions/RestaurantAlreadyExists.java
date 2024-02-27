package exceptions;

import static defines.Errors.ALREADY_EXISTENT_RESTAURANT;
public class RestaurantAlreadyExists extends Exception{
    public RestaurantAlreadyExists() {
        super(ALREADY_EXISTENT_RESTAURANT);
    }
}

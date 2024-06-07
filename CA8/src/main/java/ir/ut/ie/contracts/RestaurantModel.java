package ir.ut.ie.contracts;

import ir.ut.ie.models.Restaurant;

import java.time.LocalTime;

public record RestaurantModel(
        String Name,
        LocalTime OpenTime,
        LocalTime CloseTime,
        String ManagerUsername,
        String Type,
        String Description,
        String ImageUri,
        RestaurantAddressModel restaurantAddress,
        RatingModel rating
) {

    public static RestaurantModel fromDomainObject(Restaurant model) {
        return new RestaurantModel(
            model.getName(),
            model.getOpenTime(),
                model.getCloseTime(),
                model.getManager().getUsername(),
                model.getType(),
                model.getDescription(),
                model.getImageUri(),
                RestaurantAddressModel.fromDomainObject(model.getRestaurantAddress()),
                RatingModel.fromDomainObject(model.getRating())
            );
    }

}
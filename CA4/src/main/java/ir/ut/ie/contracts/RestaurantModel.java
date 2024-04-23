package ir.ut.ie.contracts;

import ir.ut.ie.models.Restaurant;
import ir.ut.ie.models.Table;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public record RestaurantModel(
        String Name,
        LocalTime OpenTime,
        LocalTime CloseTime,
        String ManagerUsername,
        String Type,
        String Description,
        String ImageUri,
        Restaurant.Address restaurantAddress,
        RatingModel rating
) {

}
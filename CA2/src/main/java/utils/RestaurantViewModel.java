package utils;

import lombok.Getter;
import models.Restaurant;
import models.Review;
import org.jetbrains.annotations.NotNull;

@Getter
public class RestaurantViewModel implements Comparable<RestaurantViewModel> {

    final Restaurant Restaurant;
    final Rating Rating;

    public RestaurantViewModel(Restaurant restaurant, Rating rating) {
        Restaurant = restaurant;
        Rating = rating;
    }

    @Override
    public int compareTo(@NotNull RestaurantViewModel o) {
        if(this.Rating.getAverageOverallScore() < o.Rating.getAverageOverallScore()){
            return -1;
        }
        if(this.Restaurant.equals(o.getRestaurant()))
            return 0;
        return 1;
    }
}
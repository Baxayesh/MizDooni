package ir.ut.ie.utils;

import lombok.Getter;
import ir.ut.ie.models.Restaurant;
import ir.ut.ie.models.Review;

@Getter
public class RestaurantViewModel implements Comparable<RestaurantViewModel> {

    final Restaurant Restaurant;
    final Rating Rating;

    public RestaurantViewModel(Restaurant restaurant, Rating rating) {
        Restaurant = restaurant;
        Rating = rating;
    }

    @Override
    public int compareTo(RestaurantViewModel o) {
        if(this.Rating.getAverageOverallScore() < o.Rating.getAverageOverallScore()){
            return -1;
        }
        if(this.Restaurant.equals(o.getRestaurant()))
            return 0;
        return 1;
    }
}
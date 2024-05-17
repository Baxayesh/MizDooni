package ir.ut.ie.database;

import ir.ut.ie.exceptions.NotExistentRestaurant;
import ir.ut.ie.exceptions.RestaurantAlreadyExists;
import ir.ut.ie.models.Restaurant;
import ir.ut.ie.models.UserAddress;
import ir.ut.ie.utils.LocationModel;

public interface IRestaurantRepository {

    Restaurant[] getByManager(String owner);

    Restaurant get(String name) throws NotExistentRestaurant;

    void add(Restaurant restaurant) throws RestaurantAlreadyExists;

    boolean exists(String restaurantName);

    Restaurant[] searchByName(String name, int offset, int limit);

    Restaurant[] searchByType(String type, int offset, int limit);

    Restaurant[] searchByLocation(String location, int offset, int limit);

    LocationModel[] getLocations();

    String[] getTypes();

    Restaurant[] getBests(UserAddress location, int limit);

    Restaurant[] getBests(int limit);

}
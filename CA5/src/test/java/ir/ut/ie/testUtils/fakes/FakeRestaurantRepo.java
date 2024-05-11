package ir.ut.ie.testUtils.fakes;

import ir.ut.ie.database.IRestaurantRepository;
import ir.ut.ie.exceptions.NotExistentRestaurant;
import ir.ut.ie.exceptions.RestaurantAlreadyExists;
import ir.ut.ie.models.Restaurant;
import ir.ut.ie.models.UserAddress;
import ir.ut.ie.utils.LocationModel;

import java.util.HashMap;
import java.util.Map;

public class FakeRestaurantRepo implements IRestaurantRepository {

    Map<String, Restaurant> memory;

    public FakeRestaurantRepo(){
        memory = new HashMap<>();
    }

    @Override
    public Restaurant[] getByManager(String owner) {
        return new Restaurant[0];
    }

    @Override
    public Restaurant get(String name) throws NotExistentRestaurant {
        if(!exists(name))
            throw new NotExistentRestaurant();

        return memory.get(name);
    }

    @Override
    public void add(Restaurant restaurant) throws RestaurantAlreadyExists {
        if(exists(restaurant.getName()))
            throw new RestaurantAlreadyExists();

        memory.put(restaurant.getName(),restaurant);
    }

    @Override
    public boolean exists(String restaurantName) {
        return memory.containsKey(restaurantName);
    }

    @Override
    public Restaurant[] searchByName(String name, int offset, int limit) {
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public Restaurant[] searchByType(String type, int offset, int limit) {
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public Restaurant[] searchByLocation(String location, int offset, int limit) {
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public LocationModel[] getLocations() {
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public String[] getTypes() {
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public Restaurant[] getBests(UserAddress location, int limit) {
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public Restaurant[] getBests(int limit) {
        throw new RuntimeException("Not Implemented");
    }
}
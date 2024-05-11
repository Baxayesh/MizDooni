package ir.ut.ie.database;

import ir.ut.ie.exceptions.NotExistentRestaurant;
import ir.ut.ie.exceptions.RestaurantAlreadyExists;
import ir.ut.ie.models.Restaurant;
import ir.ut.ie.models.UserAddress;
import ir.ut.ie.utils.LocationModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
@Setter
@Getter
@NoArgsConstructor
public class RestaurantRepository implements IRestaurantRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public RestaurantRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Restaurant[] getByManager(String owner) {

        var query = entityManager.createQuery("from Restaurant r where " +
                "r.Manager.Username = :owner_pr", Restaurant.class);

        query.setParameter("owner_pr", owner);

        return query.getResultList().toArray(Restaurant[]::new);
    }

    @Override
    public Restaurant get(String name) throws NotExistentRestaurant {

        var restaurant = entityManager.find(Restaurant.class, name);

        if (restaurant == null)
            throw new NotExistentRestaurant();

        return restaurant;
    }

    @Override
    public void add(Restaurant restaurant) throws RestaurantAlreadyExists {

        if(exists(restaurant.getName()))
            throw new RestaurantAlreadyExists();

        entityManager.persist(restaurant);
        entityManager.persist(restaurant.getRestaurantAddress());
        entityManager.persist(restaurant.getRating());
    }

    @Override
    public boolean exists(String restaurantName) {
        var restaurant = entityManager.find(Restaurant.class, restaurantName);
        return restaurant != null;
    }

    @Override
    public Restaurant[] searchByName(String name, int offset, int limit) {

        var hql = "from Restaurant r where r.Name like :name_pr";
        var query = entityManager.createQuery(hql, Restaurant.class);
        query.setParameter("name_pr", "%" + name + "%");
        query.setFirstResult(offset);
        query.setMaxResults(limit);

        return query.getResultList().toArray(Restaurant[]::new);
    }

    @Override
    public Restaurant[] searchByType(String type, int offset, int limit) {

        var hql = "from Restaurant r where r.Type = :type_pr";
        var query = entityManager.createQuery(hql, Restaurant.class);
        query.setParameter("type_pr", type);
        query.setFirstResult(offset);
        query.setMaxResults(limit);

        return query.getResultList().toArray(Restaurant[]::new);
    }

    @Override
    public Restaurant[] searchByLocation(String location, int offset, int limit) {

        var hql = "from Restaurant r where r.RestaurantAddress.City = :location_pr or " +
                "r.RestaurantAddress.Country = :location_pr";
        var query = entityManager.createQuery(hql, Restaurant.class);
        query.setParameter("location_pr", location);
        query.setFirstResult(offset);
        query.setMaxResults(limit);

        return query.getResultList().toArray(Restaurant[]::new);
    }

    @Override
    public LocationModel[] getLocations() {

        var locations = new HashMap<String, List<String>>();

        var hql = "select distinct r.RestaurantAddress.Country, r.RestaurantAddress.City "
                + "from Restaurant r order by r.RestaurantAddress.Country, r.RestaurantAddress.City";
        var query = entityManager.createQuery(hql, String[].class);

        for (var result : query.getResultList()) {
            String country = result[0];
            String city = result[1];

            locations.computeIfAbsent(country, k -> new ArrayList<>()).add(city);
        }

        return toLocationModel(locations);
    }

    LocationModel[] toLocationModel(Map<String, List<String>> map){

        var results = new ArrayList<LocationModel>();

        map.forEach((country, cities) ->
            results.add(new LocationModel(country, cities.toArray(String[]::new)))
        );

        return results.toArray(LocationModel[]::new);
    }

    @Override
    public String[] getTypes() {
        return entityManager.createQuery("select distinct r.Type from Restaurant r",String.class)
                .getResultList().toArray(String[]::new);
    }

    @Override
    public Restaurant[] getBests(UserAddress location, int limit) {
        
        var hql = "select r from Restaurant r join r.Rating rt "
                + "where r.RestaurantAddress.Country = :country_pr and r.RestaurantAddress.City = :city_pr "
                + "order by rt.TotalOverallScore / rt.ReviewCount desc";
        var query = entityManager.createQuery(hql, Restaurant.class);

        query.setParameter("country_pr", location.getCountry());
        query.setParameter("city_pr", location.getCity());

        query.setMaxResults(limit);

        return query.getResultList().toArray(Restaurant[]::new);
    }

    @Override
    public Restaurant[] getBests(int limit) {
        
        var hql = "select r from Restaurant r join r.Rating rt "
                + "order by rt.TotalOverallScore / rt.ReviewCount desc";
        var query = entityManager.createQuery(hql, Restaurant.class);

        query.setMaxResults(limit);

        return query.getResultList().toArray(Restaurant[]::new);
    }

}
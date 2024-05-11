package ir.ut.ie.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import ir.ut.ie.database.Database;
import ir.ut.ie.exceptions.FailedToFetchData;
import ir.ut.ie.models.*;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Component
public class MizdooniInitializer {

    private static final String DATA_PROVIDER_ADDRESS = "http://91.107.137.117:55";

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    private Database database;
    private Mizdooni mizdooni;

    @PostConstruct
    @Transactional
    public void Initiate() {
        var em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        database = Database.createUsing(em);
        mizdooni = new Mizdooni(database);
        fetchUsers();
        var newRestaurants = fetchRestaurants();
        fetchTables(newRestaurants);
        fetchReviews();
        em.getTransaction().commit();
    }

    @SneakyThrows
    void fetchUsers(){
        for(var user : (ExternalServiceUserModel[])FetchValues("users", ExternalServiceUserModel.class)){
            if(!database.UserRepo.exists(user.username))
                mizdooni.addUser(
                        user.role,
                        user.username,
                        user.password,
                        user.email,
                        user.address.getCountry(),
                        user.address.getCity()
                );
        }
    }

    @SneakyThrows
    Set<String> fetchRestaurants(){

        var newRestaurants = new HashSet<String>();

        for (var restaurant :
                (ExternalServiceRestaurantModel[])FetchValues("restaurants", ExternalServiceRestaurantModel.class)) {
            if(!database.RestaurantRepo.exists(restaurant.name)){
                mizdooni.addRestaurant(
                        restaurant.name,
                        restaurant.managerUsername,
                        restaurant.type,
                        ParseTime(restaurant.startTime),
                        ParseTime(restaurant.endTime),
                        restaurant.description,
                        restaurant.address.getCountry(),
                        restaurant.address.getCity(),
                        restaurant.address.getStreet(),
                        restaurant.image
                );
                newRestaurants.add(restaurant.name);
            }

        }

        return newRestaurants;
    }

    @SneakyThrows
    void fetchTables(Set<String> newRestaurants){

        for (var table :
                (ExternalServiceTableModel[])FetchValues("tables", ExternalServiceTableModel.class)) {
            if(newRestaurants.contains(table.restaurantName))
                mizdooni.addTable(
                        table.restaurantName,
                        table.managerUsername,
                        table.seatsNumber
                );
        }
    }

    @SneakyThrows
    void fetchReviews(){
        for (var review :
                (ExternalServiceReviewModel[])FetchValues("reviews", ExternalServiceReviewModel.class)) {

            if(!database.ReviewRepo.exists(review.restaurantName, review.username)){
                var reviewModel = new Review(
                        mizdooni.findRestaurant(review.restaurantName),
                        mizdooni.findClient(review.username),
                        review.foodRate,
                        review.serviceRate,
                        review.ambianceRate,
                        review.overallRate,
                        review.comment
                );


                database.ReviewRepo.upsert(reviewModel);
            }
        }
    }

    LocalTime ParseTime(String input){
        var hour = Integer.parseInt(input.substring(0,2));
        var minute = Integer.parseInt(input.substring(3,5));

        return  LocalTime.of(hour, minute);
    }

    public Object[] FetchValues(String resource, Class<?> objectType) throws FailedToFetchData {

        ObjectMapper mapper = new ObjectMapper();
        TypeFactory typeFactory = mapper.getTypeFactory();

        try {
            var url = new URI(DATA_PROVIDER_ADDRESS + "/" + resource).toURL();

            return mapper.readValue(url, typeFactory.constructArrayType(objectType));

        } catch (Exception e) {
            throw new FailedToFetchData(e);
        }


    }


    record ExternalServiceUserModel(String username, String password, String role, String email, UserAddress address){}
    record ExternalServiceRestaurantModel(String name, String managerUsername, String type, String image, String description, String startTime, String endTime, RestaurantAddress address){ }
    record ExternalServiceTableModel(String managerUsername, String restaurantName, int seatsNumber, int tableNumber){}
    record ExternalServiceReviewModel(String username, String restaurantName, String comment, double ambianceRate, double foodRate, double serviceRate, double overallRate){ }
}

package ir.ut.ie.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import ir.ut.ie.database.Database;
import ir.ut.ie.exceptions.FailedToFetchData;
import ir.ut.ie.exceptions.KeyAlreadyExists;
import ir.ut.ie.models.*;
import lombok.SneakyThrows;

import java.net.URI;
import java.time.LocalTime;

public class MizdooniProvider {
    static Mizdooni instance;

    private static final String DATA_PROVIDER_ADDRESS = "http://91.107.137.117:55";

    public static Mizdooni GetInstance(){
        if(instance == null){
            ReloadInstance();
        }
        return instance;
    }

    public static void ReloadInstance() {
        var db = Database.CreateInMemoryDatabase();
        var instance = new Mizdooni(db);
        addUsers(instance);
        addRestaurants(instance);
        addTables(instance);
        addReviews(instance,db);
        MizdooniProvider.instance = instance;
    }

    @SneakyThrows
    static void addUsers(Mizdooni mizdooni){
        for(var user : (ExternalServiceUserModel[])FetchValues("users", ExternalServiceUserModel.class)){
            mizdooni.addUser(user.role, user.username, user.password, user.email, user.address.getCountry(), user.address.getCity());
        }
    }

    @SneakyThrows
    static void addRestaurants(Mizdooni mizdooni){

        for (var restaurant :
                (ExternalServiceRestaurantModel[])FetchValues("restaurants", ExternalServiceRestaurantModel.class)) {
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
        }

    }

    @SneakyThrows
    static void addTables(Mizdooni mizdooni){

        for (var table :
                (ExternalServiceTableModel[])FetchValues("tables", ExternalServiceTableModel.class)) {
            mizdooni.addTable(
                    table.restaurantName,
                    table.managerUsername,
                    table.seatsNumber
            );
        }
    }

    @SneakyThrows
    static void addReviews(Mizdooni mizdooni, Database database){
        for (var review :
                (ExternalServiceReviewModel[])FetchValues("reviews", ExternalServiceReviewModel.class)) {

            var reviewModel = new Review(
                    mizdooni.findRestaurant(review.restaurantName),
                    mizdooni.findClient(review.username),
                    review.foodRate,
                    review.serviceRate,
                    review.ambianceRate,
                    review.overallRate,
                    review.comment
            );


            try{
                database.Reviews.Add(reviewModel);
                mizdooni.findRestaurant(review.restaurantName()).Rating.ConsiderReview(reviewModel);
            }catch (KeyAlreadyExists ex){
                var oldReview = database.Reviews.Get(reviewModel.getKey());
                database.Reviews.Upsert(reviewModel);
                mizdooni.findRestaurant(review.restaurantName()).Rating.UpdateReview(oldReview, reviewModel);
            }

        }
    }

    static LocalTime ParseTime(String input){
        var hour = Integer.parseInt(input.substring(0,2));
        var minute = Integer.parseInt(input.substring(3,5));

        return  LocalTime.of(hour, minute);
    }

    public static Object[] FetchValues(String resource, Class<?> objectType) throws FailedToFetchData {

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

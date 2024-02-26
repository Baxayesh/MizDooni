package testUtils;

import database.Database;
import lombok.SneakyThrows;
import service.Mizdooni;
import utils.PairType;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MizdooniStubHelper {

    private final Mizdooni Service;

    private final Database Database;

    public Mizdooni Mizdooni(){
        return Service;
    }

    public Database Database(){
        return Database;
    }

    public MizdooniStubHelper(){
        Database = database.Database.CreateInMemoryDatabase();
        Service = new Mizdooni(Database);
    }


    public void AddAnonymousRestaurant(String restaurantName){
        var managerName = "anonymous_manager_of_"+restaurantName;
        AddAnonymousManager(managerName);
        Service.AddRestaurant(
                restaurantName,
                managerName,
                "",
                LocalTime.of(8,0),
                LocalTime.of(18,0),
                "",
                new model.Restaurant.Address("country","city","street")
        );
    }

    public void AddAnonymousRestaurant(String restaurantName, int openingHour, int closureHour){
        var managerName = "anonymous_manager_of_"+restaurantName;
        AddAnonymousManager(managerName);
        Service.AddRestaurant(
                restaurantName,
                managerName,
                "",
                LocalTime.of(openingHour,0),
                LocalTime.of(closureHour,0),
                "",
                new model.Restaurant.Address("country","city","street")
        );
    }

    public void AddAnonymousCustomer(String username){
        Service.AddUser(
                "customer",
                username,
                "password",
                username+ "@anon.com",
                new model.User.Address("country","city")
        );
    }

    public void AddAnonymousManager(String username){
        Service.AddUser(
                "manager",
                username,
                "password",
                username+ "@anon.com",
                new model.User.Address("country","city")
        );
    }


    @SneakyThrows
    public void AssertReviewExists(
            String issuerUsername,
            String restaurantName,
            double foodScore,
            double serviceScore,
            double ambianceScore,
            double overallScore,
            String comment,
            LocalDateTime creationTime
    ){
        var review = Database
                .Restaurants
                .Get(restaurantName)
                .getReviews()
                .get(issuerUsername);

        assertEquals(foodScore, review.getFoodScore());
        assertEquals(ambianceScore, review.getAmbianceScore());
        assertEquals(serviceScore, review.getServiceScore());
        assertEquals(overallScore, review.getOverallScore());
        assertEquals(comment,review.getComment());
        CustomAssertions.assertEquals(creationTime, review.getIssueTime(), 500);
    }
}

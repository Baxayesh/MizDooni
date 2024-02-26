package testUtils;

import database.Database;
import lombok.Getter;
import lombok.SneakyThrows;
import service.Mizdooni;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Getter
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


    public void AddAnonymousRestaurantToMizdooni(String restaurantName){
        var managerName = "anonymous_manager_of_"+restaurantName;
        AddAnonymousManagerToMizdooni(managerName);
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

    public void AddAnonymousCustomerToMizdooni(String username){
        Service.AddUser(
                "customer",
                username,
                "password",
                username+ "@anon.com",
                new model.User.Address("country","city")
        );
    }

    public void AddAnonymousManagerToMizdooni(String username){
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

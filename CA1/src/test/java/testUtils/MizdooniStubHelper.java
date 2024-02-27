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

    public MizdooniStubHelper(){
        Database = database.Database.CreateInMemoryDatabase();
        Service = new Mizdooni(Database);
    }


    @SneakyThrows
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

    @SneakyThrows
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

    @SneakyThrows
    public void AddAnonymousCustomer(String username){
        Service.AddUser(
                "client",
                username,
                "password",
                username+ "@anon.com",
                new model.User.Address("country","city")
        );
    }

    @SneakyThrows
    public void AddAnonymousManager(String username){
        Service.AddUser(
                "manager",
                username,
                "password",
                username + "@anon.com",
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

    @SneakyThrows
    public void AddAnonymousTable(String restaurant, int table) {

        var manager = Database.Restaurants.Get(restaurant).getManager().getUsername();

        Service.AddTable(
                table,
                restaurant,
                manager,
                1
        );
    }

    @SneakyThrows
    public void AddPreviousReserve(String reservee, String restaurant, int table, LocalDateTime reserveTime) {

        if(!Database.Users.Exists(reservee))
            AddAnonymousCustomer(reservee);

        Service.ReserveATable(
                reservee,
                restaurant,
                table,
                reserveTime
        );
    }

    @SneakyThrows
    public void AssertReserveRegistered(
            int reserveNumber,
            String username,
            String restaurant,
            int table,
            LocalDateTime reserveTime
    ) {
        var reserve = Database.Reserves.Get(new PairType<>(username,reserveNumber));

        assertEquals(restaurant, reserve.getTable().getRestaurant().getName());
        assertEquals(table, reserve.getTable().getTableNumber());
        assertEquals(reserveTime, reserve.GetReserveTime());
        assertTrue(reserve.IsActive());
    }
}

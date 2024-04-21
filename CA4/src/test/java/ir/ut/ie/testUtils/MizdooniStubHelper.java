package ir.ut.ie.testUtils;

import ir.ut.ie.database.Database;
import lombok.SneakyThrows;
import ir.ut.ie.models.Reserve;
import ir.ut.ie.service.Mizdooni;
import ir.ut.ie.utils.PairType;

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
        Database = ir.ut.ie.database.Database.CreateInMemoryDatabase();
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
                "country","city","street",
                ""
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
                "country","city","street",
                ""
        );
    }

    @SneakyThrows
    public void AddAnonymousCustomer(String username){
        Service.AddUser(
                "client",
                username,
                "password",
                username+ "@anon.com",
                "country",
                "city"
        );
    }

    @SneakyThrows
    public void AddAnonymousManager(String username){
        Service.AddUser(
                "manager",
                username,
                "password",
                username + "@anon.com",
                "country",
                "city"
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
                .Reviews
                .Get(new PairType<>(restaurantName, issuerUsername))
                ;

        assertEquals(foodScore, review.getFoodScore());
        assertEquals(ambianceScore, review.getAmbianceScore());
        assertEquals(serviceScore, review.getServiceScore());
        assertEquals(overallScore, review.getOverallScore());
        assertEquals(comment,review.getComment());
        CustomAssertions.assertEquals(creationTime, review.getIssueTime(), 500);
    }

    @SneakyThrows
    public void AddAnonymousTable(String restaurant, int table) {

        var manager = Database.Restaurants.Get(restaurant).getManagerUsername();

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
    public void AddPassedReserve(String reservee, String restaurant, int table) {

        Database.Reserves.Add(
                new Reserve(
                        Database.ReserveIdGenerator.GetNext(),
                        Database.Tables.Get(new PairType<>(restaurant, table)),
                        Database.Users.Get(reservee),
                        LocalDateTime.now().minusDays(1)
                )
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

package ir.ut.ie.service;

import ir.ut.ie.database.Database;
import ir.ut.ie.database.InMemoryRepo;
import ir.ut.ie.exceptions.KeyAlreadyExists;
import ir.ut.ie.exceptions.MizdooniInternalException;
import lombok.SneakyThrows;
import ir.ut.ie.models.EntityModel;
import ir.ut.ie.models.Review;
import ir.ut.ie.models.User;
//import org.xml.sax.helpers.ParserFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class MizdooniProvider {
    static Mizdooni instance;

    public static Mizdooni GetInstance(){
        if(instance == null){
            instance = CreateInstance();
        }
        return instance;
    }

    @SneakyThrows
    static Mizdooni CreateInstance(){
        var db = Database.CreateInMemoryDatabase();
        var instance = new Mizdooni(db);
        addUsers(instance);
        addRestaurants(instance);
        addTables(instance);
        addReserves(instance);
        addReviews(db);
        instance.Login("ali", "ali");
        return instance;
    }



    @SneakyThrows
    static void addUsers(Mizdooni mizdooni){
        mizdooni.AddUser("client", "ali","ali","ali@gmail.com","Iran","Tehran");
        mizdooni.AddUser("client", "reza", "reza", "reza@gmail.com", "Iran", "Rasht");
        mizdooni.AddUser("client", "mirza", "mirza", "mirza@gmail.com", "Shenzel-Penzel", "Victoria");
        mizdooni.AddUser("manager", "mamad", "mamad", "mamad@gmail.com", "Iran", "Kerman");
        mizdooni.AddUser("manager", "amirali", "amirali", "amirali@gmail.com", "Iran", "mashhad");
        mizdooni.AddUser("manager", "goly", "goly", "goly@gmail.com", "Iran", "mashhad");

    }

    @SneakyThrows
    static void addRestaurants(Mizdooni mizdooni){
        mizdooni.AddRestaurant("mamoly","mamad", "fastfood", LocalTime.of(8,0),
                LocalTime.of(18,0), "", "Iran", "Tehran", "Kargar");
        mizdooni.AddRestaurant("makhsos","goly", "sonaty", LocalTime.of(5,0),
                LocalTime.of(19,0), "", "Iran", "Tehran", "Golro");
        mizdooni.AddRestaurant("regimi","amirali", "vegetran", LocalTime.of(15,0),
                LocalTime.of(23,0), "", "Iran", "Rasht", "Kargar");
    }

    @SneakyThrows
    static void addTables(Mizdooni mizdooni){
        mizdooni.AddTable(1, "mamoly", "mamad", 6);
        mizdooni.AddTable(2, "mamoly", "mamad", 6);
        mizdooni.AddTable(3, "mamoly", "mamad", 4);
        mizdooni.AddTable(4, "mamoly", "mamad", 4);

        mizdooni.AddTable(1, "makhsos", "goly", 6);
        mizdooni.AddTable(2, "makhsos", "goly", 4);
        mizdooni.AddTable(3, "makhsos", "goly", 2);

        mizdooni.AddTable(1, "regimi", "amirali", 6);
        mizdooni.AddTable(2, "regimi", "amirali", 6);
        mizdooni.AddTable(3, "regimi", "amirali", 8);
        mizdooni.AddTable(4, "regimi", "amirali", 4);
    }

    @SneakyThrows
    static void addReserves(Mizdooni mizdooni){
        mizdooni.ReserveATable("ali", "mamoly", 3, ReserveTime(9, 1));
        mizdooni.ReserveATable("ali", "makhsos", 2, ReserveTime(14, 1));
        mizdooni.ReserveATable("reza", "makhsos", 1, ReserveTime(12, 1));
        mizdooni.ReserveATable("mirza", "regimi", 2, ReserveTime(18, 2));
    }

    @SneakyThrows
    static void addReviews(Database database){
        database.Reviews.Add(new Review("mamoly","ali", 5,4,4.5,3.75,"verry qood"));
        database.Reviews.Add(new Review("regimi","ali", 3.5,2.3,1,2.75,"nut so qood"));
        database.Reviews.Add(new Review("regimi","mirza", 2.0,1,1,2.5,"bad bad"));
        database.Reviews.Add(new Review("makhsos","reza", 4.5,4.5,4,5,"vary varry qoood"));
    }

    static LocalDateTime ReserveTime(int hour, int daysFromToday){
        var date = LocalDateTime.now().toLocalDate().plusDays(daysFromToday);
        var time = LocalTime.of(hour, 0);
        return  LocalDateTime.of(date, time);
    }
}

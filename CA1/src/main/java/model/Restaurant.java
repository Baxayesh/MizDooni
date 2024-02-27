package model;

import com.fasterxml.jackson.core.JsonProcessingException;
import exceptions.InvalidAddress;
import exceptions.NotInWorkHour;
import exceptions.TimeBelongsToPast;
import exceptions.TimeIsNotRound;
import lombok.Getter;
import lombok.Setter;
import ui.ConsoleMizdooni;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Getter
@Setter
public class Restaurant extends EntityModel<String> {

    private LocalTime OpenTime;
    private LocalTime CloseTime;
    private String ManagerUsername;
    private String Type;
    private String Description;
    private ArrayList<Integer> TableNumbers;
    private Dictionary<String, Review> Reviews;
    private Address restaurantAddress;

    public String getName(){
        return super.getKey();
    }

    public Restaurant(String name, LocalTime openTime, LocalTime closeTime, String managerUsername, String type, String description, Address address) {
        super(name);
        OpenTime = openTime;
        CloseTime = closeTime;
        ManagerUsername = managerUsername;
        Type = type;
        Description = description;
        TableNumbers = new ArrayList<>();
        restaurantAddress = address;
        Reviews = new Hashtable<>();
    }

    public void addRestaurant(String name, User managerUsername, String type, LocalTime startTime, LocalTime endTime, String description, String country, String city, String street) throws JsonProcessingException {
        // Validate restaurant name
        //TODO: FIX
        /*
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getName().equals(name)) {
                //return "Error: Restaurant name already exists.";
                Exception e = new RestaurantAlreadyExists();
                ConsoleMizdooni.printOutput(new Output(false, e.getMessage()));
            }
        }*/
        /*
        // Validate manager username
        boolean managerExists = false;
        for (User user : users) {
            if (user.getUsername().equals(managerUsername) && user.getRole() =) {
                managerExists = true;
                break;
            }
        }*/
        /*
        if (!managerExists) {
            return "Error: Manager username does not exist or is not a manager.";
        }*/

        // Validate start and end times
        if (!Pattern.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$", startTime.toString()) || !Pattern.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$", endTime.toString())) {
            //return "Error: Invalid start or end time format.";
            Exception e = new NotInWorkHour();
            ConsoleMizdooni.printOutput(new Output(false, e.getMessage()));
        }

        // Validate address
        if (country == null || country.isEmpty() || city == null || city.isEmpty() || street == null || street.isEmpty()) {
            //return "Error: Invalid address. Address must include country, city, and street.";
            Exception e = new InvalidAddress();
            ConsoleMizdooni.printOutput(new Output(false, e.getMessage()));
        }

        ConsoleMizdooni.printOutput(new Output(true,"Restaurant added successfully"));

    }





    void EnsureTimeIsRound(LocalTime time) throws TimeIsNotRound {
        var roundTime = LocalTime.of(time.getHour(), 0);

        if(! time.equals(roundTime)){
            throw new TimeIsNotRound();
        }
    }

    void EnsureTimeBelongsToFuture(LocalDateTime time) throws TimeBelongsToPast {
        if(time.isBefore(LocalDateTime.now())){
            throw new TimeBelongsToPast();
        }
    }

    void EnsureTimeIsInWorkHours(LocalTime time) throws NotInWorkHour {
        if(time.isBefore(OpenTime) || time.isAfter(CloseTime))
            throw new NotInWorkHour();
    }

    public void ValidateReserveTime(LocalDateTime reserveTime)
            throws TimeIsNotRound, TimeBelongsToPast, NotInWorkHour {

        EnsureTimeIsRound(reserveTime.toLocalTime());
        EnsureTimeBelongsToFuture(reserveTime);
        EnsureTimeIsInWorkHours(reserveTime.toLocalTime());
    }

    public Stream<Integer> getTableNumbers() {
        return TableNumbers.stream();
    }

    public void addReview(String issuer, Review review) {
        Reviews.put(issuer, review);
    }

    @Getter
    public static class Address {
        private final String country;
        private final String city;
        private final String street;

        public Address(String country, String city, String street) {
            this.country = country;
            this.city = city;
            this.street = street;
        }

    }
}


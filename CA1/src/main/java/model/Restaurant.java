package model;

import exceptions.*;
import lombok.Getter;
import lombok.Setter;
import utils.AvailableTable;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.regex.Pattern;

@Getter
@Setter
public class Restaurant extends EntityModel<String> {

    private LocalTime OpenTime;
    private LocalTime CloseTime;
    private User Manager;
    private String Type;
    private String Description;
    private ArrayList<Table> Tables;
    private Dictionary<String, Review> Reviews;

    //address
    private Address restaurantAddress;

    private List<Restaurant> restaurants;
    private List<User> users;

    public String getName(){
        return super.getKey();
    }

    public Restaurant(String name, LocalTime openTime, LocalTime closeTime, User manager, String type, String description, Address address) {
        super(name);
        OpenTime = openTime;
        CloseTime = closeTime;
        Manager = manager;
        Type = type;
        Description = description;
        Tables = new ArrayList<>();
        restaurantAddress = address;
        this.restaurants = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public String addRestaurant(String name, User managerUsername, String type, LocalTime startTime, LocalTime endTime, String description, String country, String city, String street) {
        // Validate restaurant name
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getName().equals(name)) {
                return "Error: Restaurant name already exists.";
            }
        }
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
            return "Error: Invalid start or end time format.";
        }

        // Validate address
        if (country == null || country.isEmpty() || city == null || city.isEmpty() || street == null || street.isEmpty()) {
            return "Error: Invalid address. Address must include country, city, and street.";
        }

        // Add restaurant to the list
        restaurants.add(new Restaurant(name, startTime, endTime, managerUsername, type, description, new Address(country, city, street)));

        return "{\"success\": true, \"data\": \"Restaurant added successfully.\"}";
    }

    public String addTable(int tableNumber, Restaurant restaurantName, User managerUsername, int seatsNumber) {
        // Check if the table number is unique
        for (Table existingTable : Tables) {
            if (existingTable.getTableNumber() == tableNumber) {
                return "Error: Table with the same number already exists.";
            }
        }

        // Check if the manager username exists (you'll need a method to verify this)
        if (!managerExists(managerUsername)) {
            return "Error: Restaurant manager username does not exist.";
        }

        // Check if the restaurant name exists (you'll need a method to verify this)
        if (!restaurantExists(restaurantName.getName())) {
            return "Error: Restaurant name does not exist.";
        }

        // Check if seatsNumber is a natural number
        if (seatsNumber <= 0) {
            return "Error: Seats number must be a positive integer.";
        }

        // Create a new table and add it to the list
        Table newTable = new Table(tableNumber, restaurantName, managerUsername, seatsNumber);
        Tables.add(newTable);

        return "Table added successfully.";
    }
    private boolean managerExists(User managerUsername) {
        return true;
    }

    private boolean restaurantExists(String restaurantName) {

        return true;
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

    Table FindTable(int tableNumber) throws NotExistentTable {
        for(var table : Tables){
            if(table.Is(tableNumber)){
                return table;
            }
        }

        throw new NotExistentTable();
    }

    public Reserve MakeReserve(int reserveNumber, User reservee, int tableNumber, LocalDateTime reserveTime)
            throws TableIsReserved, TimeIsNotRound, TimeBelongsToPast, NotInWorkHour, NotExistentTable {

        EnsureTimeIsRound(reserveTime.toLocalTime());
        EnsureTimeBelongsToFuture(reserveTime);
        EnsureTimeIsInWorkHours(reserveTime.toLocalTime());
        var table = FindTable(tableNumber);
        return table.MakeReserve(reserveNumber, reservee, reserveTime); // store history
    }

    public AvailableTable[] GetAvailableTables() {
        return
            Tables
            .stream()
            .map(Table::GetAvailableTimes)
            .filter(AvailableTable::HasAnyAvailableTime)
            .toArray(AvailableTable[]::new);
    }

    public void addReview(String issuer, Review review) {
        Reviews.put(issuer, review);
    }
    public static class Address {
        private String country;
        private String city;
        private String street;

        public Address(String country, String city, String street) {
            this.country = country;
            this.city = city;
            this.street = street;
        }
    }
}


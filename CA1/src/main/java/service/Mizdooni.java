package service;

import database.Database;
import exceptions.*;
import model.*;
import utils.AvailableTable;
import utils.PairType;
import utils.UserRole;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Mizdooni {

    Database Database;
    private static ObjectMapper mapper;

    public Mizdooni(Database database){
        Database = database;
        mapper = new ObjectMapper();
    }


    public void AddUser(
            String role,
            String username,
            String password,
            String email,
            User.Address address
    ) throws JsonProcessingException{
        var user = new User(role, username, password, email, address);
        user.addUser(role, username, password, email, address);

        try{
            Database.Users.Add(user);
        }
        catch (KeyAlreadyExists ex){

        throw new RuntimeException(ex);}

    }

    public void AddRestaurant(
            String name,
            User manager,
            String type,
            LocalTime openTime,
            LocalTime closeTime,
            String description,
            Restaurant.Address address
    ) throws JsonProcessingException{
        var restaurant = new Restaurant(name,openTime, closeTime, manager, type, description, address);
        restaurant.addRestaurant(name, manager, type, openTime, closeTime, description, address.getCountry(), address.getCity(), address.getStreet());
        try{
            Database.Restaurants.Add(restaurant);
        }catch (KeyAlreadyExists ex){
        throw new RuntimeException(ex);
        }

    }

    public void AddTable(
            int tableNumber,
            Restaurant restaurantName,
            User manager,
            int seatNumber
    ) throws JsonProcessingException
    {
        var table = new Table(tableNumber, restaurantName, manager, seatNumber);
        table.addTable(tableNumber, restaurantName, manager, seatNumber);

        try{
            Database.Tables.Add(table);
        }catch (KeyAlreadyExists ex){
            throw new RuntimeException(ex);
        }
    }

    public Reserve ReserveATable(
            String reserveeUsername,
            String restaurantName,
            int tableNumber,
            LocalDateTime reserveTime
    )
            throws
            NotExistentUser,
            NotExpectedUserRole,
            NotExistentRestaurant,
            TimeBelongsToPast,
            TableIsReserved,
            TimeIsNotRound,
            NotInWorkHour,
            NotExistentTable
    {

        var reservee = FindUser(reserveeUsername);
        EnsureUserIs(reservee, UserRole.Costumer);
        var restaurant = FindRestaurant(restaurantName);
        var reserveNumber = Database.ReserveIdGenerator.GetNext();
        var reserve = restaurant.MakeReserve(reserveNumber, reservee, tableNumber, reserveTime);

        try{
            Database.Reserves.Add(reserve);
        } catch (KeyAlreadyExists ex){
            throw new RuntimeException(ex);
        }

        return reserve;
    }

    public void CancelReserve(String username, int reserveNumber)
            throws
            NotExistentUser,
            NotExistentReserve,
            CancelingExpiredReserve,
            CancelingCanceledReserve
    {

        var reserve = FindReserve(username, reserveNumber);
        reserve.Cancel();
    }

    public Reserve[] GetActiveReserves(String username) throws NotExistentUser, NotExpectedUserRole {

        var user = FindUser(username);
        EnsureUserIs(user, UserRole.Costumer);

        return Database
                .Reserves
                .Search(reserve -> reserve.IsActive() && user.Is(reserve.getReserveeUsername()))
                .toArray(Reserve[]::new);

    }

    public AvailableTable[] GetAvailableTables(String restaurantName)
        throws NotExistentRestaurant {

        var restaurant = FindRestaurant(restaurantName);
        return restaurant.GetAvailableTables();
    }

    public Restaurant[] SearchRestaurantByName(String restaurantName) {
        return Database
            .Restaurants
            .Search(restaurant -> restaurant.getName().toLowerCase().contains(restaurantName.toLowerCase()))
            .toArray(Restaurant[]::new);
    }

    public Restaurant[] SearchRestaurantByType(String type){
        return Database
            .Restaurants
            .Search(restaurant -> restaurant.getType().equalsIgnoreCase(type))
            .toArray(Restaurant[]::new);
    }

    public void AddReview(
            String issuerUsername,
            String restaurantName,
            double foodScore,
            double serviceScore,
            double ambianceScore,
            double overallScore,
            String comment
    )
            throws
            NotExistentUser,
            NotExistentRestaurant,
            NotExpectedUserRole,
            ScoreOutOfRange
    {
        var review = new Review(foodScore, serviceScore, ambianceScore, overallScore, comment);

        var issuer = FindUser(issuerUsername);
        EnsureUserIs(issuer, UserRole.Costumer);

        var restaurant = FindRestaurant(restaurantName);

        restaurant.addReview(issuerUsername, review);
    }

    User FindUser(String username) throws NotExistentUser {
        try {
            return Database.Users.Get(username);
        } catch (KeyNotFound ex) {
            throw new NotExistentUser();
        }
    }

    Reserve FindReserve(String username, int reserveNumber) throws NotExistentReserve, NotExistentUser {

        if(Database.Users.Exists(username)){
            throw new NotExistentUser();
        }

        try{
            return Database.Reserves.Get(new PairType<>(username, reserveNumber));
        } catch (KeyNotFound e) {
            throw new NotExistentReserve();
        }
    }

    Restaurant FindRestaurant(String restaurantName) throws NotExistentRestaurant {
        try {
            return Database.Restaurants.Get(restaurantName);
        } catch (KeyNotFound ex) {
            throw new NotExistentRestaurant();
        }
    }

    void EnsureUserIs(User user, UserRole desiredRole) throws NotExpectedUserRole {
        if(!user.RoleIs(desiredRole)){
            throw new NotExpectedUserRole(desiredRole);
        }
    }

}
package service;

import database.Database;
import exceptions.*;
import models.*;
import utils.AvailableTable;
import utils.PairType;
import utils.UserRole;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Mizdooni {

    private final Database Database;


    private User LoggedInUser;

    public Mizdooni(Database database){
        Database = database;
    }

    public void Login(String username, String password) throws MizdooniNotAuthenticatedException {
        try {
            var user = FindUser(username);
            if (!user.getPassword().equals(password))
                throw new MizdooniNotAuthenticatedException();
            LoggedInUser = user;
        } catch (NotExistentUser ex){
            throw new MizdooniNotAuthenticatedException();
        }

    }

    public void Logout() throws MizdooniNotAuthorizedException {
        EnsureLoggedIn();
        LoggedInUser = null;
    }

    public void EnsureLoggedIn() throws MizdooniNotAuthorizedException {
        if(LoggedInUser == null)
            throw new MizdooniNotAuthorizedException();
    }

    public void EnsureLoggedIn(UserRole role) throws MizdooniNotAuthorizedException {
        EnsureLoggedIn();
        EnsureUserIs(LoggedInUser, role);
    }

    public void AddUser(
            String role,
            String username,
            String password,
            String email,
            String country,
            String city
    ) throws UserAlreadyExits, InvalidAddress, InvalidUser {

        var address = new User.Address(country, city);
        var user = new User(username, role, password, email, address);
        User.ValidateUser(username, role, password, email, address);
        try{
            Database.Users.Add(user);
        }
        catch (KeyAlreadyExists ex){
            throw new UserAlreadyExits();
        }

    }

    public void AddRestaurant(
            String name,
            String manager,
            String type,
            LocalTime openTime,
            LocalTime closeTime,
            String description,
            String country,
            String city,
            String street
    )
            throws
            NotExistentUser,
            NotExpectedUserRole,
            RestaurantAlreadyExists,
            InvalidAddress
    {
        var address = new Restaurant.Address(country, city, street);
        address.Validate();
        var managerUser = FindUser(manager);
        EnsureUserIs(managerUser, UserRole.Manager);

        var restaurant = new Restaurant(name,openTime, closeTime, manager, type, description, address);

        try{
            Database.Restaurants.Add(restaurant);
        } catch (KeyAlreadyExists ex){
            throw new RestaurantAlreadyExists();
        }

    }

    public void AddTable(
            int tableNumber,
            String restaurantName,
            String managerName,
            int seatNumber
    ) throws NotExistentRestaurant, NotExpectedUserRole, NotExistentUser, TableAlreadyExists, SeatNumNotPos {

        var restaurant = FindRestaurant(restaurantName);

        var manager = FindUser(managerName);
        EnsureUserIs(manager, UserRole.Manager);
        if(!manager.Is(restaurant.getManagerUsername())){
            throw new NotExistentRestaurant();
        }

        var table = new Table(tableNumber, restaurant, seatNumber);

        try{
            Database.Tables.Add(table);
        }catch (KeyAlreadyExists ex){
            throw new TableAlreadyExists();
        }
    }

    public int ReserveATable(
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
        EnsureUserIs(reservee, UserRole.Client);
        var restaurant = FindRestaurant(restaurantName);
        var reserveNumber = Database.ReserveIdGenerator.GetNext();
        restaurant.ValidateReserveTime(reserveTime);
        var table = FindTable(restaurantName, tableNumber);
        var reserve = table.MakeReserve(reserveNumber, reservee, reserveTime);

        try{
            Database.Reserves.Add(reserve);
        } catch (KeyAlreadyExists ex){
            throw new RuntimeException(ex);
        }

        return reserve.getReserveNumber();
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
        EnsureUserIs(user, UserRole.Client);

        return Database
                .Reserves
                .Search(reserve -> reserve.IsActive() && user.Is(reserve.getReserveeUsername()))
                .toArray(Reserve[]::new);

    }

    public AvailableTable[] GetAvailableTables(String restaurantName)
        throws NotExistentRestaurant {

        if(!Database.Restaurants.Exists(restaurantName))
            throw new NotExistentRestaurant();

        return Database
                .Tables
                .Search(table -> table.getRestaurant().Is(restaurantName))
                .map(Table::GetAvailableTimes)
                .filter(AvailableTable::HasAnyAvailableTime)
                .toArray(AvailableTable[]::new);
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
        EnsureUserIs(issuer, UserRole.Client);

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

    Table FindTable(String restaurantName, int tableNumber)
            throws
            NotExistentTable,
            NotExistentRestaurant
    {

        if(!Database.Restaurants.Exists(restaurantName)){
            throw new NotExistentRestaurant();
        }

        try{
            return Database.Tables.Get(new PairType<>(restaurantName, tableNumber));
        } catch (KeyNotFound e) {
            throw new NotExistentTable();
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
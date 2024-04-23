package ir.ut.ie.service;

import ir.ut.ie.database.Database;
import ir.ut.ie.exceptions.*;
import ir.ut.ie.models.*;
import ir.ut.ie.utils.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Mizdooni {

    private final Database Database;
    private User LoggedInUser;

    public Mizdooni(Database database){
        Database = database;
    }

    public Restaurant[] getRestaurantsFor(String manager){
        return Database
                .Restaurants
                .Search(restaurant -> restaurant.getManagerUsername().equals(manager))
                .toArray(Restaurant[]::new);
    }

    public User getLoggedIn() throws MizdooniNotAuthorizedException {
        ensureLoggedIn();
        return LoggedInUser;
    }

    public void login(String username, String password) throws MizdooniNotAuthenticatedException {
        try {
            var user = findUser(username);
            if (!user.getPassword().equals(password))
                throw new MizdooniNotAuthenticatedException();
            LoggedInUser = user;
        } catch (NotExistentUser ex){
            throw new MizdooniNotAuthenticatedException();
        }

    }

    public void logout() throws MizdooniNotAuthorizedException {
        ensureLoggedIn();
        LoggedInUser = null;
    }

    public void ensureLoggedIn() throws MizdooniNotAuthorizedException {
        if(LoggedInUser == null)
            throw new MizdooniNotAuthorizedException();
    }

    public void ensureLoggedIn(UserRole role) throws MizdooniNotAuthorizedException {
        ensureLoggedIn();
        ensureUserIs(LoggedInUser, role);
    }

    public void addUser(
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

    public void addRestaurant(
            String name,
            String manager,
            String type,
            LocalTime openTime,
            LocalTime closeTime,
            String description,
            String country,
            String city,
            String street,
            String image
    )
            throws
            NotExistentUser,
            NotExpectedUserRole,
            RestaurantAlreadyExists,
            InvalidAddress
    {
        var address = new Restaurant.Address(country, city, street);
        address.Validate();
        var managerUser = findUser(manager);
        ensureUserIs(managerUser, UserRole.Manager);

        var restaurant = new Restaurant(name,openTime, closeTime, manager, type, description, address, image);

        try{
            Database.Restaurants.Add(restaurant);
        } catch (KeyAlreadyExists ex){
            throw new RestaurantAlreadyExists();
        }

    }

    int getNextTableNumber(String restaurantName) throws NotExistentRestaurant {
        var restaurant = findRestaurant(restaurantName);
        return restaurant.getNextTableNumber();
    }

    public int addTable(
            String restaurantName,
            String managerName,
            int seatNumber
    ) throws NotExistentRestaurant, NotExpectedUserRole, NotExistentUser, SeatNumNotPos {

        var tableNumber = getNextTableNumber(restaurantName);

        var restaurant = findRestaurant(restaurantName);

        var manager = findUser(managerName);
        ensureUserIs(manager, UserRole.Manager);
        if(!manager.Is(restaurant.getManagerUsername())){
            throw new NotExistentRestaurant();
        }

        var table = new Table(tableNumber, restaurant, seatNumber);

        try{
            Database.Tables.Add(table);
        }catch (KeyAlreadyExists ex){
            throw new RuntimeException(ex);
        }
        restaurant.addTable(table);

        return tableNumber;
    }


    public int reserveATable(String reserveeUsername, String restaurantName, LocalDateTime reserveTime, int seats)
            throws NotExistentUser, NotExpectedUserRole, NotExistentRestaurant, TimeBelongsToPast, TimeIsNotRound,
            NotInWorkHour, NoFreeTable {

        var reservee = findUser(reserveeUsername);
        ensureUserIs(reservee, UserRole.Client);

        var restaurant = findRestaurant(restaurantName);
        var reserveNumber = Database.ReserveIdGenerator.GetNext();
        var reserve = restaurant.MakeReserve(reserveNumber, reservee, reserveTime, seats);

        try{
            Database.Reserves.Add(reserve);
        } catch (KeyAlreadyExists ex){
            throw new RuntimeException(ex);
        }

        return reserve.getReserveNumber();
    }

    public void cancelReserve(String username, int reserveNumber)
            throws
            NotExistentUser,
            NotExistentReserve,
            CancelingExpiredReserve,
            CancelingCanceledReserve
    {
        var reserve = findReserve(username, reserveNumber);
        reserve.Cancel();
    }

    public LocalTime[] getAvailableTables(
            String restaurantName, LocalDate requestDate, int requestedSeats)
        throws NotExistentRestaurant {

        if(!Database.Restaurants.Exists(restaurantName))
            throw new NotExistentRestaurant();

        return Database
                .Tables
                .Search(table -> table.getRestaurant().Is(restaurantName) && table.getNumberOfSeats() >= requestedSeats)
                .flatMap(table -> table.GetAvailableTimes(requestDate).stream())
                .toArray(LocalTime[]::new);
    }

    public Restaurant[] searchRestaurantByName(String restaurantName) {
        return Database
            .Restaurants
            .Search(restaurant -> restaurant.getName().toLowerCase().contains(restaurantName.toLowerCase()))
            .toArray(Restaurant[]::new);
    }

    public Restaurant[] searchRestaurantByType(String type){
        return Database
            .Restaurants
            .Search(restaurant -> restaurant.getType().equalsIgnoreCase(type))
            .toArray(Restaurant[]::new);
    }
    public Restaurant[] searchRestaurantByLocation(String location){
        return Database
                .Restaurants
                .Search(restaurant ->
                        restaurant.getRestaurantAddress().city().equalsIgnoreCase(location) ||
                        restaurant.getRestaurantAddress().country().equalsIgnoreCase(location))
                .toArray(Restaurant[]::new);
    }

    public void addReview(
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
            ScoreOutOfRange,
            NotAllowedToAddReview
    {
        var review = new Review(restaurantName, issuerUsername, foodScore, serviceScore, ambianceScore,
                overallScore, comment);

        var issuer = findUser(issuerUsername);
        ensureUserIs(issuer, UserRole.Client);
        var restaurant = findRestaurant(restaurantName);
        ensureUserHaveAnyPassedReserveAt(issuerUsername, restaurantName);
        restaurant.upsertReview(review);

        Database.Reviews.Upsert(review);
    }

    void ensureUserHaveAnyPassedReserveAt(String user, String restaurant) throws NotAllowedToAddReview {
        if(
            Database.Reserves.Search( reserve ->
                reserve.getReserveeUsername().equals(user) &&
                reserve.getTable().getRestaurant().Is(restaurant) &&
                reserve.IsActive() &&
                reserve.IsPassed()
            ).findAny().isEmpty()
        ){
            throw new NotAllowedToAddReview();
        }
    }

    public User findUser(String username) throws NotExistentUser {
        try {
            return Database.Users.Get(username);
        } catch (KeyNotFound ex) {
            throw new NotExistentUser();
        }
    }

    public Reserve findReserve(String username, int reserveNumber) throws NotExistentReserve, NotExistentUser {

        if(!Database.Users.Exists(username)){
            throw new NotExistentUser();
        }

        try{
            return Database.Reserves.Get(new PairType<>(username, reserveNumber));
        } catch (KeyNotFound e) {
            throw new NotExistentReserve();
        }
    }

    public Restaurant findRestaurant(String restaurantName) throws NotExistentRestaurant {
        try {
            return Database.Restaurants.Get(restaurantName);
        } catch (KeyNotFound ex) {
            throw new NotExistentRestaurant();
        }
    }

    public Review findReview(String restaurantName, String issuer) throws MizdooniNotFoundException {
        try {
            return Database.Reviews.Get(new PairType<>(restaurantName, issuer));
        } catch (KeyNotFound e) {
            throw new MizdooniNotFoundException("Review");
        }
    }

    void ensureUserIs(User user, UserRole desiredRole) throws NotExpectedUserRole {
        if (!user.RoleIs(desiredRole)) {
            throw new NotExpectedUserRole(desiredRole);
        }
    }

    public Review[] getReviews(String restaurantName) {
        return Database.Reviews.Search(review -> review.getRestaurantName().equals(restaurantName)).toArray(Review[]::new);
    }

    public Reserve[] getReserves(String reservee)  {
        return Database
                .Reserves
                .Search(reserve -> reserve.getReserveeUsername().equals(reservee))
                .toArray(Reserve[]::new);
    }

    public Restaurant[] getBestRestaurants(String city, int limit) {
        return Database
                .Restaurants
                .Search(restaurant -> restaurant.getRestaurantAddress().city().equals(city))
                .sorted((r1, r2)-> {
                    if(r1.getRating().getAverageOverallScore() > r2.getRating().getAverageOverallScore())
                        return -1;
                    return 1;
                }).limit(limit)
                .toArray(Restaurant[]::new);
    }

    public Restaurant[] getBestRestaurants(int limit) {
        return Database
                .Restaurants
                .All()
                .sorted((r1, r2)-> {
                    if(r1.getRating().getAverageOverallScore() > r2.getRating().getAverageOverallScore())
                        return -1;
                    return 1;
                }).limit(limit)
                .toArray(Restaurant[]::new);
    }
}
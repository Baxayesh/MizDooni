package ir.ut.ie.service;

import ir.ut.ie.database.Database;
import ir.ut.ie.exceptions.*;
import ir.ut.ie.models.*;
import ir.ut.ie.utils.*;
import lombok.SneakyThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class Mizdooni {

    private final Database Database;
    private User LoggedInUser;

    public Mizdooni(Database database){
        Database = database;
    }

    public Restaurant[] getRestaurantsFor(String manager){
        return Database
                .Restaurants
                .Search(restaurant -> restaurant.getManager().getUsername().equals(manager))
                .toArray(Restaurant[]::new);
    }

    public User getLoggedIn() throws MizdooniNotAuthorizedException {
        ensureLoggedIn();
        return LoggedInUser;
    }

    public void login(String username, String password) throws MizdooniNotAuthenticatedException {
        try {
            var user = _findUser(username);
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

        //NOTE: this method will be used to check access authority from controller layer
        //and should be handled in a better way (access privilege should be invariant from user object type)
        switch (role){
            case Manager -> {
                if(LoggedInUser instanceof Manager)
                    return;
            }
            case Client -> {
                if (LoggedInUser instanceof Client)
                    return;
            }

        }
        throw new NotExpectedUserRole(role);
    }

    public void addUser(
            String role,
            String username,
            String password,
            String email,
            String country,
            String city
    ) throws UserAlreadyExits, InvalidAddress, InvalidUser {

        User user;
        if(role.equals("client")) {
            user = new Client(username, password, email, country, city);
        } else {
            user = new Manager(username, password, email, country, city);
        }

        try{
            Database.Users.Add(user);
        }
        catch (KeyAlreadyExists ex){
            throw new UserAlreadyExits();
        }

    }

    public void addRestaurant(
            String name,
            String managerName,
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
        var manager = findManager(managerName);

        var restaurant = new Restaurant(name,openTime, closeTime, manager, type, description, country, city, street, image);

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
    ) throws NotExistentRestaurant, MizdooniNotAuthorizedException, NotExistentUser, SeatNumNotPos {

        var tableNumber = getNextTableNumber(restaurantName);

        var restaurant = findRestaurant(restaurantName);

        var manager = findManager(managerName);

        if(!manager.Is(restaurant.getManager().getUsername())){
            throw new MizdooniNotAuthorizedException();
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

        var reservee = findClient(reserveeUsername);

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
                        restaurant.getRestaurantAddress().getCity().equalsIgnoreCase(location) ||
                        restaurant.getRestaurantAddress().getCountry().equalsIgnoreCase(location))
                .toArray(Restaurant[]::new);
    }

    @SneakyThrows(KeyNotFound.class)
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
        var restaurant = findRestaurant(restaurantName);
        var issuer = findClient(issuerUsername);

        var review = new Review(restaurant, issuer, foodScore, serviceScore, ambianceScore,
                overallScore, comment);

        ensureUserHaveAnyPassedReserveAt(issuerUsername, restaurantName);


        if(!Database.Reviews.Exists(new PairType<>(restaurantName, issuerUsername))){
            restaurant.Rating.ConsiderReview(review);
        }else{
            restaurant.Rating.UpdateReview(Database.Reviews.Get(new PairType<>(restaurantName, issuerUsername)) , review);
        }

        Database.Reviews.Upsert(review);

    }

    void ensureUserHaveAnyPassedReserveAt(String user, String restaurant) throws NotAllowedToAddReview {
        if(
            Database.Reserves.Search( reserve ->
                reserve.getReservee().getUsername().equals(user) &&
                reserve.getTable().getRestaurant().Is(restaurant) &&
                reserve.IsActive() &&
                reserve.IsPassed()
            ).findAny().isEmpty()
        ){
            throw new NotAllowedToAddReview();
        }
    }

    User _findUser(String username) throws NotExistentUser {
        try {
            return Database.Users.Get(username);
        } catch (KeyNotFound ex) {
            throw new NotExistentUser();
        }
    }

    public Manager findManager(String username) throws NotExistentUser, NotExpectedUserRole {
        var user = _findUser(username);

        if(user instanceof Manager manager)
            return manager;

        throw new NotExpectedUserRole(UserRole.Manager);
    }

    public Client findClient(String username) throws NotExistentUser, NotExpectedUserRole {
        var user = _findUser(username);

        if(user instanceof Client client)
            return client;

        throw new NotExpectedUserRole(UserRole.Client);
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

    public Review[] getReviews(String restaurantName) {
        return Database.Reviews.Search(review -> review.getRestaurantName().equals(restaurantName)).toArray(Review[]::new);
    }

    public Reserve[] getReserves(String reservee)  {
        return Database
                .Reserves
                .Search(reserve -> reserve.getReservee().getUsername().equals(reservee))
                .toArray(Reserve[]::new);
    }

    public Restaurant[] getBestRestaurants(String city, int limit) {
        return Database
                .Restaurants
                .Search(restaurant -> restaurant.getRestaurantAddress().getCity().equals(city))
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

    public LocationModel[] getUsedLocations(){

        var addresses = Database.Restaurants.All().map(Restaurant::getRestaurantAddress);

        var locations = new HashMap<String, Set<String>>();

        addresses.forEach(address -> {
            if(!locations.containsKey(address.getCountry())){
                locations.put(address.getCountry(), new TreeSet<>(){});
            }
            locations.get(address.getCountry()).add(address.getCity());
        });

        return locations
                .keySet()
                .stream()
                .map(country -> new LocationModel(country, locations.get(country).toArray(String[]::new)))
                .toArray(LocationModel[]::new);
    }

    public String[] getUsedFoodTypes(){
        return Database.Restaurants.All().map(Restaurant::getType).distinct().toArray(String[]::new);
    }
}
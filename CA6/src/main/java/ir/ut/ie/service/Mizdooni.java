package ir.ut.ie.service;

import ir.ut.ie.database.Database;
import ir.ut.ie.exceptions.*;
import ir.ut.ie.models.*;
import ir.ut.ie.utils.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class Mizdooni {

    private final Database Database;

    @Autowired
    public Mizdooni(Database database){
        Database = database;
    }

    public Restaurant[] getRestaurantsFor(String manager){
        return Database.RestaurantRepo.getByManager(manager);
    }

    @Transactional
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

        Database.RestaurantRepo.add(restaurant);

    }

    @Transactional
    public int addTable(
            String restaurantName,
            String managerName,
            int seatNumber
    ) throws NotExistentRestaurant, MizdooniNotAuthorizedException, NotExistentUser, SeatNumNotPos {

        var restaurant = findRestaurant(restaurantName);

        var manager = findManager(managerName);

        if(!manager.is(restaurant.getManager().getUsername())){
            throw new MizdooniNotAuthorizedException();
        }

        var table = new Table(restaurant, seatNumber);


        var id = Database.TableRepo.add(table);
        restaurant.addTable(table);
        return id;
    }

    @Transactional
    public int reserveATable(String reserveeUsername, String restaurantName, LocalDateTime reserveTime, int seats)
            throws NotExistentUser, NotExpectedUserRole, NotExistentRestaurant, TimeBelongsToPast, TimeIsNotRound,
            NotInWorkHour, NoFreeTable {

        var reservee = findClient(reserveeUsername);

        var restaurant = findRestaurant(restaurantName);
        var reserve = restaurant.MakeReserve(reservee, reserveTime, seats);

        return Database.ReserveRepo.add(reserve);

    }

    @Transactional
    public void cancelReserve(String username, int reserveNumber)
            throws
            NotExistentReserve,
            CancelingExpiredReserve,
            CancelingCanceledReserve
    {
        var reserve = findReserve(username, reserveNumber);
        reserve.Cancel();
        Database.ReserveRepo.update(reserve);
    }

    public LocalTime[] getAvailableTimes(
            String restaurantName, LocalDate requestDate, int requestedSeats)
        throws NotExistentRestaurant {

        var restaurant = findRestaurant(restaurantName);

        return restaurant.getAvailableTimes(requestDate, requestedSeats);

    }

    public Restaurant[] searchRestaurantByName(String restaurantName, int offset, int limit){
        return Database.RestaurantRepo.searchByName(restaurantName, offset, limit);
    }

    public Restaurant[] searchRestaurantByType(String type, int offset, int limit){
        return Database.RestaurantRepo.searchByType(type, offset, limit);
    }

    public Restaurant[] searchRestaurantByLocation(String location, int offset, int limit){
       return Database.RestaurantRepo.searchByLocation(location, offset, limit);
    }

    @Transactional
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

        Database.ReviewRepo.upsert(review);

    }

    void ensureUserHaveAnyPassedReserveAt(String user, String restaurant) throws NotAllowedToAddReview {
        if(!Database.ReserveRepo.doUserHasAnyPassedReserveAt(user, restaurant)){
            throw new NotAllowedToAddReview();
        }
    }

    public Manager findManager(String username) throws NotExistentUser, NotExpectedUserRole {
        var user = Database.UserRepo.get(username);

        if(user instanceof Manager manager)
            return manager;

        throw new NotExpectedUserRole(UserRole.Manager);
    }

    public Client findClient(String username) throws NotExistentUser, NotExpectedUserRole {
        var user = Database.UserRepo.get(username);

        if(user instanceof Client client)
            return client;

        throw new NotExpectedUserRole(UserRole.Client);
    }

    public Reserve findReserve(String username, int reserveNumber) throws NotExistentReserve {
        return Database.ReserveRepo.get(username, reserveNumber);
    }

    public Restaurant findRestaurant(String restaurantName) throws NotExistentRestaurant {
        return Database.RestaurantRepo.get(restaurantName);
    }

    public Review findReview(String restaurantName, String issuer) throws MizdooniNotFoundException {
        return Database.ReviewRepo.get(restaurantName, issuer);
    }

    public Review[] getReviews(String restaurantName, int offset, int limit) {
        return Database.ReviewRepo.get(restaurantName, offset, limit);
    }

    public long getReviewCount(String restaurantName){
        return Database.ReviewRepo.count(restaurantName);
    }

    public Reserve[] getReserves(String reservee)  {
        return Database.ReserveRepo.get(reservee);
    }

    public Restaurant[] getBestRestaurantsFor(String username, int limit) throws NotExpectedUserRole, NotExistentUser {
        var user = findClient(username);
        return Database.RestaurantRepo.getBests(user.getAddress(), limit);
    }

    public Restaurant[] getBestRestaurants(int limit) {
        return Database.RestaurantRepo.getBests(limit);
    }

    public LocationModel[] getUsedLocations(){
        return Database.RestaurantRepo.getLocations();
    }

    public String[] getUsedFoodTypes(){
        return Database.RestaurantRepo.getTypes();
    }
}
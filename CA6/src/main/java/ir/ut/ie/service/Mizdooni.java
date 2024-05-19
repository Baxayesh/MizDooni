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
import java.util.Optional;

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
            RestaurantAlreadyExists
    {
        var manager = findManager(managerName).orElseThrow(NotExistentUser::new);

        var restaurant = new Restaurant(name,openTime, closeTime, manager, type, description, country, city, street, image);

        Database.RestaurantRepo.add(restaurant);

    }

    @Transactional
    public int addTable(
            String restaurantName,
            String managerName,
            int seatNumber
    ) throws NotExistentRestaurant {

        var restaurant = findRestaurant(restaurantName);

        var manager = findManager(managerName);

        if(manager.isEmpty() || !manager.get().is(restaurant.getManager().getUsername())){
            throw new NotExistentRestaurant();
        }

        var table = new Table(restaurant, seatNumber);


        var id = Database.TableRepo.add(table);
        restaurant.addTable(table);
        return id;
    }

    @Transactional
    public int reserveATable(String reserveeUsername, String restaurantName, LocalDateTime reserveTime, int seats)
            throws NotExistentUser, NotExistentRestaurant, TimeIsNotRound,
            NotInWorkHour, NoFreeTable {

        var reservee = findClient(reserveeUsername).orElseThrow(NotExistentUser::new);

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
            NotAllowedToAddReview
    {
        var restaurant = findRestaurant(restaurantName);
        var issuer = findClient(issuerUsername).orElseThrow(NotExistentUser::new);

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

    public Optional<Manager> findManager(String username) {
        var user = Database.UserRepo.tryGet(username);

        if(user.isPresent() && user.get() instanceof Manager manager)
            return Optional.of(manager);

        return Optional.empty();

    }

    public Optional<Client> findClient(String username) {
        var user = Database.UserRepo.tryGet(username);

        if(user.isPresent() && user.get() instanceof Client client)
            return Optional.of(client);

        return Optional.empty();
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

    public Restaurant[] getBestRestaurantsFor(String username, int limit) throws NotExistentUser {
        var user = findClient(username).orElseThrow(NotExistentUser::new);
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
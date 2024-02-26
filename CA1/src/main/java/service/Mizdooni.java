package service;

import database.Database;
import exceptions.*;
import model.*;
import utils.AvailableTable;
import utils.PairType;
import utils.UserRole;

import java.time.LocalDateTime;

public class Mizdooni {

    Database Database;

    public Mizdooni(Database database){
        Database = database;
    }


    public Reserve ReserveATable(String reserveeUsername, String restaurantName, int tableNumber, LocalDateTime reserveTime)
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

    public void AddReview(String issuerUsername, String restaurantName, Review review)
            throws
            NotExistentUser,
            NotExistentRestaurant,
            NotExpectedUserRole
    {

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
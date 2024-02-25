package service;

import database.Database;
import exceptions.*;
import model.Reserve;
import model.Restaurant;
import model.User;
import utils.AvailableTable;
import utils.PairType;
import utils.UserRole;

import java.time.LocalDateTime;
import java.util.Collection;

public class Mizdooni {

    Database Database;

    public Mizdooni(){
        Database = new Database();
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

        // TODO: save reserve history
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
        // TODO: save reserve cancellation history
    }

    public Collection<AvailableTable> GetAvailableTables(String restaurantName)
        throws NotExistentRestaurant {

        var restaurant = FindRestaurant(restaurantName);
        return restaurant.GetAvailableTables();
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
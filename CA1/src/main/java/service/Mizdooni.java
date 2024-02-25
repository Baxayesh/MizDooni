package service;

import database.Database;
import exceptions.*;
import model.*;
import utils.UserRole;

import java.time.LocalDateTime;

public class Mizdooni {

    Database Database;

    public Mizdooni(){
        Database = new Database();
    }

    public void ReserveATable(String reserveeUsername, String restaurantName, int tableNumber, LocalDateTime reserveTime)
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
        var reserve = restaurant.MakeReserve(reservee, tableNumber, reserveTime);
        // TODO: save reserve history
    }

    User FindUser(String username) throws NotExistentUser {
        for(var user : Database.getUsers()){
            if(user.Is(username)){
                return user;
            }
        }

        throw new NotExistentUser();
    }

    Restaurant FindRestaurant(String restaurantName) throws NotExistentRestaurant {
        for(var restaurant : Database.getRestaurants()){
            if(restaurant.Is(restaurantName)){
                return restaurant;
            }
        }
        throw new NotExistentRestaurant();
    }

    void EnsureUserIs(User user, UserRole desiredRole) throws NotExpectedUserRole {
        if(!user.Is(desiredRole)){
            throw new NotExpectedUserRole(desiredRole);
        }
    }

}
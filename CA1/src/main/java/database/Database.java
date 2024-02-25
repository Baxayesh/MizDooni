package database;

import lombok.Getter;
import lombok.Setter;
import model.Restaurant;
import model.User;

import java.util.ArrayList;

@Getter
@Setter
public class Database{

    private ArrayList<Restaurant> Restaurants;
    private ArrayList<User> Users;

    public Database(){
        Restaurants = new ArrayList<>();
        Users = new ArrayList<>();
    }

}
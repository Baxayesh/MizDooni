package ui;

import database.Database;
import service.Mizdooni;

import java.util.Scanner;
import com.google.gson.Gson;
import model.*;
import database.Database.*;


public class ConsoleMizdooni {

    Mizdooni Service;

    public ConsoleMizdooni(Mizdooni mizdooni){
        Service = mizdooni;
    }

    public void Start(){
        System.out.println("Mizdooni Is Up!");

        Scanner inputScanner = new Scanner(System.in);

        while (true){
            var command = inputScanner.next();

            if(command.equals("exit")){
                return;
            }

            var jsonData = inputScanner.nextLine();
            ProcessCommand(command, jsonData);
        }
    }

    void ProcessCommand(String command, String jsonData){
        System.out.printf("your command : %s your data: %s\n", command, jsonData);
        //TODO: Implement
//        Database myDatabase = new Database();
//        Mizdooni mizdooni = new Mizdooni(myDatabase);
        switch (command){
            case "addUser":
                if(!jsonData.isEmpty()) {
                    User user = new Gson().fromJson(jsonData, User.class);
                    return Service.AddUser(user);
                }
                break;
            case "addRestaurant":
                if(!jsonData.isEmpty()) {
                    Restaurant restaurant = new Gson().fromJson(jsonData, Restaurant.class);
                    return Service.AddRestaurant(restaurant);
                }
                break;
            case "addTable":
                if(!jsonData.isEmpty()) {
                    Table table = new Gson().fromJson(jsonData, Table.class);
                    return Service.AddTable(table);
                }
                break;
            case "reserveTable":
                if(!jsonData.isEmpty()) {
                    Reserve reserve = new Gson().fromJson(jsonData, Reserve.class);
                    return Service.ReserveATable(reserve);
                }
                break;
            case "cancelReservation":
                if(!jsonData.isEmpty()) {
                    Reserve reserve = new Gson().fromJson(jsonData, Reserve.class);
                    return Service.CancelReserve(reserve);
                }
                break;
            case "showReservationHistory":
                if(!jsonData.isEmpty()) {
                    Reserve reserve = new Gson().fromJson(jsonData, Reserve.class);
                    return Service.GetActiveReserves(reserve);
                }
                break;
            case "searchRestaurantsByName":
                if(!jsonData.isEmpty()) {
                    Restaurant restaurant = new Gson().fromJson(jsonData, Restaurant.class);
                    return Service.SearchRestaurantByName(restaurant);
                }
                break;
            case "searchRestaurantsByType":
                if(!jsonData.isEmpty()) {
                    Restaurant restaurant = new Gson().fromJson(jsonData, Restaurant.class);
                    return Service.SearchRestaurantByType(restaurant);
                }
                break;
            case "showAvailableTables":
                if(!jsonData.isEmpty()) {
                    Table table = new Gson().fromJson(jsonData, Table.class);
                    return Service.GetAvailableTables(table);
                }
                break;
            case "addReview":
                if(!jsonData.isEmpty()) {
                    Review review = new Gson().fromJson(jsonData, Review.class);
                    return Service.AddReview(review);
                }
                break;
        }
    }
}

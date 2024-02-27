package ui;

import database.Database;
import service.Mizdooni;

import java.security.Provider;
import java.util.Scanner;
import com.google.gson.Gson;
import model.*;
import database.Database.*;
import java.util.Map;
import java.util.regex.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.*;


public class ConsoleMizdooni {

    Mizdooni Service;
    //private Mizdooni mizdooni;
    //private Scanner input;
    private static ObjectMapper mapper;

    public ConsoleMizdooni(Mizdooni mizdooni){

        Service = mizdooni;
        //input = new Scanner(System.in);
        mapper = new ObjectMapper();
    }

    public void Start() throws JsonProcessingException{
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

    void ProcessCommand(String command, String jsonData) throws JsonProcessingException{
        System.out.printf("your command : %s your data: %s\n", command, jsonData);
        //TODO: Implement
        switch (command){
            case "addUser":
                if(!jsonData.isEmpty()) {
                    User user = new Gson().fromJson(jsonData, User.class);
                    Service.AddUser(user);
                }
                break;
            case "addRestaurant":
                if(!jsonData.isEmpty()) {
                    Restaurant restaurant = new Gson().fromJson(jsonData, Restaurant.class);
                    Service.AddRestaurant(restaurant);
                }
                break;
            case "addTable":
                if(!jsonData.isEmpty()) {
                    Table table = new Gson().fromJson(jsonData, Table.class);
                    Service.AddTable(table);
                }
                break;
            case "reserveTable":
                if(!jsonData.isEmpty()) {
                    Reserve reserve = new Gson().fromJson(jsonData, Reserve.class);
                    Service.ReserveATable(reserve);
                }
                break;
            case "cancelReservation":
                if(!jsonData.isEmpty()) {
                    Reserve reserve = new Gson().fromJson(jsonData, Reserve.class);
                    Service.CancelReserve(reserve);
                }
                break;
            case "showReservationHistory":
                if(!jsonData.isEmpty()) {
                    Reserve reserve = new Gson().fromJson(jsonData, Reserve.class);
                    Service.GetActiveReserves(reserve);
                }
                break;
            case "searchRestaurantsByName":
                if(!jsonData.isEmpty()) {
                    Restaurant restaurant = new Gson().fromJson(jsonData, Restaurant.class);
                    Service.SearchRestaurantByName(restaurant);
                }
                break;
            case "searchRestaurantsByType":
                if(!jsonData.isEmpty()) {
                    Restaurant restaurant = new Gson().fromJson(jsonData, Restaurant.class);
                    Service.SearchRestaurantByType(restaurant);
                }
                break;
            case "showAvailableTables":
                if(!jsonData.isEmpty()) {
                    Table table = new Gson().fromJson(jsonData, Table.class);
                    Service.GetAvailableTables(table);
                }
                break;
            case "addReview":
                if(!jsonData.isEmpty()) {
                    Review review = new Gson().fromJson(jsonData, Review.class);
                    Service.AddReview(review);
                }
                break;
        }
    }
    public static void printOutput(Output output) throws JsonProcessingException {
        String print = mapper.writeValueAsString(output);
        print = print.replace("\\", "");
        System.out.println(print);
    }
}

package ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import exceptions.*;
import model.*;
import service.Mizdooni;

import java.util.Scanner;


public class ConsoleMizdooni {

    Mizdooni Service;
    //private Mizdooni mizdooni;
    //private Scanner input;
    private static final ObjectMapper mapper = new ObjectMapper();

    public ConsoleMizdooni(Mizdooni mizdooni){

        Service = mizdooni;
    }

    public void Start()
            throws JsonProcessingException,
            NotExistentUser,
            NotExpectedUserRole,
            NotExistentRestaurant,
            TimeBelongsToPast,
            TableIsReserved,
            TimeIsNotRound,
            NotInWorkHour,
            NotExistentTable,
            NotExistentUser,
            NotExistentReserve,
            CancelingExpiredReserve,
            CancelingCanceledReserve,
            NotExistentUser,
            NotExistentRestaurant,
            NotExpectedUserRole,
            ScoreOutOfRange{
        System.out.println("Mizdooni Is Up!");

        Scanner inputScanner = new Scanner(System.in);

        while (true){
            var command = inputScanner.next();

            if(command.equals("exit")){
                return;
            }

            var jsonData = inputScanner.nextLine();
            try {
                var data = ProcessCommand(command, jsonData);
                printOutput(new Output(true, data.toString()));
            } catch (MizdooniUserException ex){
                printOutput(new Output(false, ex.getMessage()));
            }

        }
    }

    Object ProcessCommand(String command, String jsonData) throws MizdooniUserException, JsonProcessingException {
        Reserve reserve;
        Restaurant restaurant;
        User user;

        switch (command){
            case "addUser":
                user = new Gson().fromJson(jsonData, User.class);
                Service.AddUser(user.getRole(), user.getUsername(), user.getPassword(), user.getEmail(), user.getUserAddress());
                return "user added successfully";
            case "addRestaurant":
                restaurant = new Gson().fromJson(jsonData, Restaurant.class);
                Service.AddRestaurant(restaurant.getName(), restaurant.getManagerUsername(), restaurant.getType(), restaurant.getOpenTime(), restaurant.getCloseTime(), restaurant.getDescription(), restaurant.getRestaurantAddress());
                return "user added successfully";
            case "addTable":
                Table table = new Gson().fromJson(jsonData, Table.class);
                Service.AddTable(table.getTableNumber(), table.getRestaurant().getName(), table.getUser().getUsername(), table.getNumberOfSeats());
                return "user added successfully";
            case "reserveTable":
                reserve = new Gson().fromJson(jsonData, Reserve.class);
                var id =Service.ReserveATable(reserve.getReserveeUsername(), reserve.getReserveeUsername(), reserve.getReserveNumber(), reserve.GetReserveTime());
                return id;
            case "cancelReservation":
                reserve = new Gson().fromJson(jsonData, Reserve.class);
                Service.CancelReserve(reserve.getReserveeUsername(),reserve.getReserveNumber());
                return "operation done successfully";
            case "showReservationHistory":
                reserve = new Gson().fromJson(jsonData, Reserve.class);
                return Service.GetActiveReserves(reserve.getReserveeUsername());
            case "searchRestaurantsByName":
                restaurant = new Gson().fromJson(jsonData, Restaurant.class);
                return Service.SearchRestaurantByName(restaurant.getName());
            case "searchRestaurantsByType":
                restaurant = new Gson().fromJson(jsonData, Restaurant.class);
                return Service.SearchRestaurantByType(restaurant.getType());
            case "showAvailableTables":
                restaurant = new Gson().fromJson(jsonData, Restaurant.class);
                return Service.GetAvailableTables(restaurant.getName());
            case "addReview":
                Review review = new Gson().fromJson(jsonData, Review.class);
                restaurant = new Gson().fromJson(jsonData, Restaurant.class);
                user = new Gson().fromJson(jsonData, User.class);
                Service.AddReview(user.getUsername(), restaurant.getName(), review.getFoodScore(), review.getServiceScore(), review.getAmbianceScore(), review.getOverallScore(), review.getComment());
                return "operation done successfully";
        }
        return "command not Found";
    }

    public static void printOutput(Output output) throws JsonProcessingException {
        String print = mapper.writeValueAsString(output);
        print = print.replace("\\", "");
        System.out.println(print);
    }
}

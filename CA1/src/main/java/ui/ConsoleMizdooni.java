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
            ProcessCommand(command, jsonData);
        }
    }

    void ProcessCommand(String command, String jsonData)
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
            ScoreOutOfRange

            {
        System.out.printf("your command : %s your data: %s\n", command, jsonData);
        //TODO: Implement
        switch (command){
            case "addUser":
                if(!jsonData.isEmpty()) {
                    User user = new Gson().fromJson(jsonData, User.class);
                    Service.AddUser(user.getRole(), user.getUsername(), user.getPassword(), user.getEmail(), user.getUserAddress());
                }
                break;
            case "addRestaurant":
                if(!jsonData.isEmpty()) {
                    Restaurant restaurant = new Gson().fromJson(jsonData, Restaurant.class);
                    Service.AddRestaurant(restaurant.getName(), restaurant.getManager().getUsername(), restaurant.getType(), restaurant.getOpenTime(), restaurant.getCloseTime(), restaurant.getDescription(), restaurant.getRestaurantAddress());
                }
                break;
            case "addTable":
                if(!jsonData.isEmpty()) {
                    Table table = new Gson().fromJson(jsonData, Table.class);
                    Service.AddTable(table.getTableNumber(), table.getRestaurant().getName(), table.getUser().getUsername(), table.getNumberOfSeats());
                }
                break;
            case "reserveTable":
                if(!jsonData.isEmpty()) {
                    Reserve reserve = new Gson().fromJson(jsonData, Reserve.class);
                    Service.ReserveATable(reserve.getReserveeUsername(), reserve.getReserveeUsername(), reserve.getReserveNumber(), reserve.GetReserveTime());
                }
                break;
            case "cancelReservation":
                if(!jsonData.isEmpty()) {
                    Reserve reserve = new Gson().fromJson(jsonData, Reserve.class);
                    Service.CancelReserve(reserve.getReserveeUsername(),reserve.getReserveNumber());
                }
                break;
            case "showReservationHistory":
                if(!jsonData.isEmpty()) {
                    Reserve reserve = new Gson().fromJson(jsonData, Reserve.class);
                    Service.GetActiveReserves(reserve.getReserveeUsername());
                }
                break;
            case "searchRestaurantsByName":
                if(!jsonData.isEmpty()) {
                    Restaurant restaurant = new Gson().fromJson(jsonData, Restaurant.class);
                    Service.SearchRestaurantByName(restaurant.getName());
                }
                break;
            case "searchRestaurantsByType":
                if(!jsonData.isEmpty()) {
                    Restaurant restaurant = new Gson().fromJson(jsonData, Restaurant.class);
                    Service.SearchRestaurantByType(restaurant.getType());
                }
                break;
            case "showAvailableTables":
                if(!jsonData.isEmpty()) {
                    Restaurant restaurant = new Gson().fromJson(jsonData, Restaurant.class);
                    Service.GetAvailableTables(restaurant.getName());
                }
                break;
            case "addReview":
                if(!jsonData.isEmpty()) {
                    Review review = new Gson().fromJson(jsonData, Review.class);
                    Restaurant restaurant = new Gson().fromJson(jsonData, Restaurant.class);
                    User user = new Gson().fromJson(jsonData, User.class);
                    Service.AddReview(user.getUsername(), restaurant.getName(), review.getFoodScore(), review.getServiceScore(), review.getAmbianceScore(), review.getOverallScore(), review.getComment());
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

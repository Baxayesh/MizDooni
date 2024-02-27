package model;

import com.fasterxml.jackson.core.JsonProcessingException;
import exceptions.*;
import exceptions.TableIsReserved;
import lombok.Getter;
import lombok.Setter;
import ui.ConsoleMizdooni;
import utils.AvailableTable;
import utils.PairType;
import java.time.LocalDateTime;
import java.util.ArrayList;


@Getter
@Setter
public class Table extends EntityModel<PairType<String, Integer>> {

    private int TableNumber;
    private int NumberOfSeats;
    private Restaurant Restaurant;
    private User User;
    private ArrayList<Reserve> Reserves;

    static PairType<String, Integer> CreateTableKey(Restaurant restaurant, int tableNumber){
        return new PairType<>(restaurant.getKey(), tableNumber);
    }

    public Table(int tableNumber, Restaurant restaurant, User managerUsername , int numOfSeats){
        super(CreateTableKey(restaurant, tableNumber));
        TableNumber = tableNumber;
        NumberOfSeats = numOfSeats;
        Restaurant = restaurant;
        User = managerUsername;
        Reserves = new ArrayList<>();
    }

    public void addTable(int tableNumber, Restaurant restaurantName, User managerUsername, int seatsNumber) throws JsonProcessingException {
        // Check if the table number is unique
        //TODO:FIX CHECK
        /*for (Table existingTable : Tables) {
            if (existingTable.getTableNumber() == tableNumber) {
                //return "Error: Table with the same number already exists.";
                Exception e = new TableAlreadyExists();
                ConsoleMizdooni.printOutput(new Output(false, e.getMessage()));
            }
        }*/

        // Check if the manager username exists (you'll need a method to verify this)
        if (!managerExists(managerUsername)) {
            //return "Error: Restaurant manager username does not exist.";
            Exception e = new NotExistentUser();
            ConsoleMizdooni.printOutput(new Output(false, e.getMessage()));
        }

        // Check if the restaurant name exists (you'll need a method to verify this)
        if (!restaurantExists(restaurantName)) {
            //return "Error: Restaurant name does not exist.";
            Exception e = new NotExistentRestaurant();
            ConsoleMizdooni.printOutput(new Output(false, e.getMessage()));
        }

        // Check if seatsNumber is a natural number
        if (seatsNumber <= 0) {
            //return "Error: Seats number must be a positive integer.";
            Exception e = new SeatNumNotPos();
            ConsoleMizdooni.printOutput(new Output(false, e.getMessage()));
        }

        ConsoleMizdooni.printOutput(new Output(true,"Table added successfully."));

    }

    private boolean managerExists(User managerUsername) {return true;}

    private boolean restaurantExists(Restaurant restaurantName) {return true;}


    void EnsureTableIsFreeIn(LocalDateTime reserveTime) throws TableIsReserved {
        for(var reserve : Reserves){
            if(reserve.GetReserveTime().equals(reserveTime) && reserve.IsActive()){
                throw new TableIsReserved();
            }
        }
    }

    public Reserve MakeReserve(int reserveNumber, User reservee, LocalDateTime reserveTime) throws TableIsReserved {
        EnsureTableIsFreeIn(reserveTime);
        var reserve = new Reserve(reserveNumber, this, reservee, reserveTime);
        Reserves.add(reserve);
        return reserve;
    }

    public boolean Is(int tableNumber) {
        return getTableNumber() == tableNumber;
    }

    public AvailableTable GetAvailableTimes(){

        var now = LocalDateTime.now();

        var availableTimes = new AvailableTable(this);

        Reserves
            .stream()
            .filter(Reserve::IsActive)
            .map(Reserve::GetReserveTime)
            .sorted()
            .dropWhile(time -> time.isAfter(now))
            .distinct()
            .forEach(availableTimes::ConsiderNextReservation);

        return availableTimes;
    }
}
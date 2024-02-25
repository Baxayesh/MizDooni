package model;

import exceptions.TableIsReserved;
import lombok.Getter;
import lombok.Setter;
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
    private ArrayList<Reserve> Reserves;

    static PairType<String, Integer> CreateTableKey(Restaurant restaurant, int tableNumber){
        return new PairType<>(restaurant.getKey(), tableNumber);
    }

    public Table(int tableNumber, Restaurant restaurant, int numOfSeats){
        super(CreateTableKey(restaurant, tableNumber));
        TableNumber = tableNumber;
        NumberOfSeats = numOfSeats;
        Restaurant = restaurant;
    }


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
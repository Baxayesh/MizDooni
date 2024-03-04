package models;

import exceptions.SeatNumNotPos;
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

    public Table(int tableNumber, Restaurant restaurant, int numOfSeats) throws SeatNumNotPos {
        super(CreateTableKey(restaurant, tableNumber));
        if(tableNumber < 1)
            throw new SeatNumNotPos();

        TableNumber = tableNumber;
        NumberOfSeats = numOfSeats;
        Restaurant = restaurant;
        Reserves = new ArrayList<>();
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
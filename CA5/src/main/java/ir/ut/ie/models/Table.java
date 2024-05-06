package ir.ut.ie.models;

import ir.ut.ie.exceptions.SeatNumNotPos;
import ir.ut.ie.exceptions.TableIsReserved;
import lombok.Getter;
import lombok.Setter;
import ir.ut.ie.utils.PairType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


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
        if(!isFreeOn(reserveTime)){
            throw new TableIsReserved();
        }
    }

    public Reserve MakeReserve(int reserveNumber, User reservee, LocalDateTime reserveTime) throws TableIsReserved {
        EnsureTableIsFreeIn(reserveTime);
        var reserve = new Reserve(reserveNumber, this, reservee, reserveTime);
        Reserves.add(reserve);
        return reserve;
    }

    public List<LocalTime> GetAvailableTimes(LocalDate onDate){

        var availableTimes = new ArrayList<LocalTime>();

        var reservedTimes = Reserves
            .stream()
            .filter(reserve -> reserve.IsActive() && reserve.GetReserveTime().toLocalDate().equals(onDate))
            .map(reserve -> reserve.GetReserveTime().toLocalTime())
            .collect(Collectors.toSet());

        for(var currentTime = getRestaurant().getOpenTime();
        currentTime.isBefore(getRestaurant().getCloseTime());
        currentTime = currentTime.plusHours(1)){
            if(!reservedTimes.contains(currentTime)){
                availableTimes.add(currentTime);
            }
        }

        return availableTimes;
    }

    public boolean isFreeOn(LocalDateTime reserveTime) {
        for(var reserve : Reserves){
            if(reserve.GetReserveTime().equals(reserveTime) && reserve.IsActive()){
                return false;
            }
        }
        return true;
    }
}

package ir.ut.ie.utils;

import lombok.Getter;
import ir.ut.ie.models.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class AvailableTable {

    @Getter
    private final Table Table;

    @Getter
    private final ArrayList<LocalDateTime> AvailableTimes;

    @Getter
    private LocalDate LastDayWithReservation;

    private LocalDateTime LastUncheckedTime;


    public AvailableTable(Table table){
        Table = table;
        AvailableTimes = new ArrayList<>();
        LastUncheckedTime = LocalDateTime.now();
        LastUncheckedTime = LocalDateTime.of(
                LastUncheckedTime.toLocalDate(),
                LocalTime.of(LastUncheckedTime.getHour(), 0)
        );
        IncreaseLastUnchecked();
        LastDayWithReservation = LastUncheckedTime.toLocalDate();
    }

    void IncreaseLastUnchecked(){
        LastUncheckedTime = LastUncheckedTime.plusHours(1);
        var restaurant = Table.getRestaurant();

        if(LastUncheckedTime.toLocalTime().isBefore(restaurant.getOpenTime())){
            LastUncheckedTime = LocalDateTime.of(
                    LastUncheckedTime.toLocalDate(),
                    restaurant.getOpenTime()
            );
        }
        else if(restaurant.getCloseTime().isBefore(LastUncheckedTime.toLocalTime())){
            LastUncheckedTime = LocalDateTime.of(
                    LastUncheckedTime.toLocalDate().plusDays(1),
                    restaurant.getOpenTime()
            );
        }
    }

    public void ConsiderNextReservation(LocalDateTime nextActiveReserve){

        while (nextActiveReserve.isAfter(LastUncheckedTime)){
            AvailableTimes.add(LastUncheckedTime);
            IncreaseLastUnchecked();
        }

        LastDayWithReservation = nextActiveReserve.toLocalDate();
    }

    public boolean HasAnyAvailableTime(){
        return !AvailableTimes.isEmpty();
    }
}
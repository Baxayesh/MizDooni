package model;

import exceptions.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

@Getter
@NoArgsConstructor
@Setter
public class Restaurant {

    //fields: id(?), name, manager, type, work start&end time, description, address

    private String Name;
    private LocalTime OpenTime;
    private LocalTime CloseTime;
    private User Manager;
    private String Type;
    private String Description;
    //address

    private ArrayList<Table> Tables;


    public boolean Is(String restaurantName) {
        return Name.equalsIgnoreCase(restaurantName);
    }

    void EnsureTimeIsRound(LocalTime time) throws TimeIsNotRound {
        var roundTime = LocalTime.of(time.getHour(), 0);

        if(! time.equals(roundTime)){
            throw new TimeIsNotRound();
        }
    }

    void EnsureTimeBelongsToFuture(LocalDateTime time) throws TimeBelongsToPast {
        if(time.isBefore(LocalDateTime.now())){
            throw new TimeBelongsToPast();
        }
    }

    void EnsureTimeIsInWorkHours(LocalTime time) throws NotInWorkHour {
        if(time.isBefore(OpenTime) || time.isAfter(CloseTime))
            throw new NotInWorkHour();
    }

    Table FindTable(int tableNumber) throws NotExistentTable {
        for(var table : Tables){
            if(table.Is(tableNumber)){
                return table;
            }
        }

        throw new NotExistentTable();
    }

    public Reserve MakeReserve(User reservee, int tableNumber, LocalDateTime reserveTime)
            throws TableIsReserved, TimeIsNotRound, TimeBelongsToPast, NotInWorkHour, NotExistentTable {

        EnsureTimeIsRound(reserveTime.toLocalTime());
        EnsureTimeBelongsToFuture(reserveTime);
        EnsureTimeIsInWorkHours(reserveTime.toLocalTime());
        var table = FindTable(tableNumber);
        return table.MakeReserve(reservee, reserveTime); // store history
    }
}
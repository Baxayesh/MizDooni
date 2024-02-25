package model;

import exceptions.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

@Getter
@Setter
public class Restaurant extends EntityModel<String> {

    private LocalTime OpenTime;
    private LocalTime CloseTime;
    private User Manager;
    private String Type;
    private String Description;
    private ArrayList<Table> Tables;
    //address

    public String getName(){
        return super.getKey();
    }

    public Restaurant(String name, LocalTime openTime, LocalTime closeTime, User manager, String type, String description) {
        super(name);
        OpenTime = openTime;
        CloseTime = closeTime;
        Manager = manager;
        Type = type;
        Description = description;
        Tables = new ArrayList<>();
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

    public Reserve MakeReserve(int reserveNumber, User reservee, int tableNumber, LocalDateTime reserveTime)
            throws TableIsReserved, TimeIsNotRound, TimeBelongsToPast, NotInWorkHour, NotExistentTable {

        EnsureTimeIsRound(reserveTime.toLocalTime());
        EnsureTimeBelongsToFuture(reserveTime);
        EnsureTimeIsInWorkHours(reserveTime.toLocalTime());
        var table = FindTable(tableNumber);
        return table.MakeReserve(reserveNumber, reservee, reserveTime); // store history
    }
}
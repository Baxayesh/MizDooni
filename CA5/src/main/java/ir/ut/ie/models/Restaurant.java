package ir.ut.ie.models;

import ir.ut.ie.exceptions.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Getter
@Setter
public class Restaurant extends EntityModel<String> {

    private String Name;

    private Manager Manager;

    private LocalTime OpenTime;
    private LocalTime CloseTime;

    private String Type;
    private String Description;
    private String ImageUri;

    private RestaurantAddress restaurantAddress;

    private ArrayList<Table> Tables;

    public Rating Rating;


    public Restaurant(
            String name,
            LocalTime openTime,
            LocalTime closeTime,
            Manager manager,
            String type,
            String description,
            String country,
            String city,
            String street,
            String imageUri
    ) throws InvalidAddress {
        super(name);
        Name = name;
        OpenTime = openTime;
        CloseTime = closeTime;
        Manager = manager;
        Type = type;
        Description = description;
        Tables = new ArrayList<>();
        restaurantAddress = new RestaurantAddress(country, city, street);
        ImageUri = imageUri;
        Rating = new Rating(this);
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

    public void ValidateReserveTime(LocalDateTime reserveTime)
            throws TimeIsNotRound, TimeBelongsToPast, NotInWorkHour {

        EnsureTimeIsRound(reserveTime.toLocalTime());
        EnsureTimeBelongsToFuture(reserveTime);
        EnsureTimeIsInWorkHours(reserveTime.toLocalTime());
    }


    public void addTable(Table table) {
        Tables.add(table);
    }

    public int getNextTableNumber() {
        return Tables.stream().map(Table::getTableNumber).sorted().max(Integer::compare).orElse(0) + 1;
    }

    public Table getTable(int tableNumber) throws NotExistentTable {
        return Tables
                .stream()
                .filter(table -> table.getTableNumber() == tableNumber)
                .findFirst()
                .orElseThrow(NotExistentTable::new);
    }

    public Reserve MakeReserve(int reserveNumber, Client reservee, LocalDateTime reserveTime, int seats) throws
            NoFreeTable, TimeBelongsToPast, TimeIsNotRound, NotInWorkHour {

        ValidateReserveTime(reserveTime);

        var goalTable = Tables.stream()
                .filter(table -> table.isFreeOn(reserveTime) && table.getNumberOfSeats() >= seats)
                .min(
                    (o1, o2) -> {
                        if(o1.getNumberOfSeats() < o2.getNumberOfSeats())
                            return -1;
                        return 1;
                    }
                ).orElseThrow(NoFreeTable::new);

        try {
            return goalTable.MakeReserve(reserveNumber, reservee, reserveTime);
        } catch (TableIsReserved e) {
            throw new RuntimeException(e);
        }

    }

}


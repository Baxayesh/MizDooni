package ir.ut.ie.models;

import ir.ut.ie.exceptions.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@jakarta.persistence.Table(name = "RESTAURANTS")
public class Restaurant implements Serializable {

    @Id
    private String Name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "MANAGER_ID", nullable = false)
    private Manager Manager;

    @Column(nullable = false)
    private LocalTime OpenTime;
    @Column(nullable = false)
    private LocalTime CloseTime;

    @Column(nullable = false)
    private String Type;
    @Column(columnDefinition="text")
    private String Description;
    @Column(nullable = false)
    private String ImageUri;

    @OneToOne(orphanRemoval = true, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private RestaurantAddress restaurantAddress;

    @OneToMany(mappedBy = "Restaurant")
    private List<Table> Tables;

    @OneToOne(orphanRemoval = true, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    public Rating Rating;


    public String getKey(){
        return this.Name;
    }

    public boolean Is(String name) { return this.Name.equals(name); }

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
        Name = name;
        OpenTime = openTime;
        CloseTime = closeTime;
        Manager = manager;
        Type = type;
        Description = description;
        Tables = new ArrayList<>();
        ImageUri = imageUri;
        Rating = new Rating(this);
        restaurantAddress = new RestaurantAddress(this, country, city, street);
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

    public Table getTable(int tableNumber) throws NotExistentTable {
        return Tables
                .stream()
                .filter(table -> table.getTableNumber() == tableNumber)
                .findFirst()
                .orElseThrow(NotExistentTable::new);
    }

    public Reserve MakeReserve(Client reservee, LocalDateTime reserveTime, int seats) throws
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
            return goalTable.MakeReserve(reservee, reserveTime);
        } catch (TableIsReserved e) {
            throw new RuntimeException(e);
        }

    }

    public LocalTime[] getAvailableTimes(LocalDate requestDate, int requestedSeats) {

        var availableTimes = new ArrayList<LocalTime>();

        for (var table : Tables){
            if(table.getNumberOfSeats() >= requestedSeats){
                availableTimes.addAll(table.getAvailableTimes(requestDate));
            }
        }

        return availableTimes.toArray(LocalTime[]::new);
    }
}
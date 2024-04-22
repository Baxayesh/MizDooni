package ir.ut.ie.models;

import ir.ut.ie.exceptions.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.stream.Stream;

@Getter
@Setter
public class Restaurant extends EntityModel<String> {

    private LocalTime OpenTime;
    private LocalTime CloseTime;
    private String ManagerUsername;
    private String Type;
    private String Description;
    private String ImageUri;
    private ArrayList<Table> Tables;
    private Address restaurantAddress;

    public String getName(){
        return super.getKey();
    }

    public Restaurant(
            String name,
            LocalTime openTime,
            LocalTime closeTime,
            String managerUsername,
            String type,
            String description,
            Address address,
            String imageUri
    ) {
        super(name);
        OpenTime = openTime;
        CloseTime = closeTime;
        ManagerUsername = managerUsername;
        Type = type;
        Description = description;
        Tables = new ArrayList<>();
        restaurantAddress = address;
        ImageUri = imageUri;
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

    public record Address(String country, String city, String street) {

        public void Validate() throws InvalidAddress {
            if (
                    country == null ||
                    country.isEmpty() ||
                    city == null ||
                    city.isEmpty() ||
                    street == null ||
                    street.isEmpty()
            ) {
                throw new InvalidAddress();
            }
        }

        @Override
        public String toString() {
            return "%s, %s, %s".formatted(street, city, country);
        }
    }

}


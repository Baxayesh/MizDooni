package models;

import exceptions.InvalidAddress;
import exceptions.NotInWorkHour;
import exceptions.TimeBelongsToPast;
import exceptions.TimeIsNotRound;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.stream.Stream;

@Getter
@Setter
public class Restaurant extends EntityModel<String> {

    private LocalTime OpenTime;
    private LocalTime CloseTime;
    private String ManagerUsername;
    private String Type;
    private String Description;
    private ArrayList<Integer> TableNumbers;
    private Dictionary<String, Review> Reviews;
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
            Address address
    ) {
        super(name);
        OpenTime = openTime;
        CloseTime = closeTime;
        ManagerUsername = managerUsername;
        Type = type;
        Description = description;
        TableNumbers = new ArrayList<>();
        restaurantAddress = address;
        Reviews = new Hashtable<>();
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

    public Stream<Integer> getTableNumbers() {
        return TableNumbers.stream();
    }

    public void addReview(String issuer, Review review) {
        Reviews.put(issuer, review);
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
    }

}


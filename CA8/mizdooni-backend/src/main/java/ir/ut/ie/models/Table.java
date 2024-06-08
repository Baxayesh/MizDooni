package ir.ut.ie.models;

import ir.ut.ie.exceptions.TableIsReserved;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@NoArgsConstructor
@Entity
@jakarta.persistence.Table(name = "TABLES")
public class Table {

    @Id
    @GeneratedValue
    private int TableNumber;

    @Column(nullable = false)
    private int NumberOfSeats;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Restaurant Restaurant;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Manager Owner;

    @OneToMany(mappedBy = "Table")
    private List<Reserve> Reserves;

    public Table(Restaurant restaurant, int numOfSeats) {
        super();

        NumberOfSeats = numOfSeats;
        Restaurant = restaurant;
        Owner = Restaurant.getManager();
        Reserves = new ArrayList<>();
    }

    void EnsureTableIsFreeIn(LocalDateTime reserveTime) throws TableIsReserved {
        if(!isFreeOn(reserveTime)){
            throw new TableIsReserved();
        }
    }

    public Reserve MakeReserve(Client reservee, LocalDateTime reserveTime) throws TableIsReserved {
        EnsureTableIsFreeIn(reserveTime);
        var reserve = new Reserve(this, reservee, reserveTime);
        Reserves.add(reserve);
        return reserve;
    }

    public List<LocalTime> getAvailableTimes(LocalDate onDate){

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

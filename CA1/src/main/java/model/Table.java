package model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import exceptions.TableIsReserved;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@NoArgsConstructor
@Setter
public class Table {

    private int TableNumber;
    private int NumberOfSeats;
    private ArrayList<Reserve> Reserves;


    void EnsureTableIsFreeIn(LocalDateTime reserveTime) throws TableIsReserved {
        for(var reserve : Reserves){
            if(reserve.GetReserveTime().equals(reserveTime) && reserve.IsActive()){
                throw new TableIsReserved();
            }
        }
    }

    public Reserve MakeReserve(User reservee, LocalDateTime reserveTime) throws TableIsReserved {
        EnsureTableIsFreeIn(reserveTime);
        var reserve = new Reserve(this, reservee, reserveTime);
        Reserves.add(reserve);
        return reserve;
    }

    public boolean Is(int tableNumber) {
        return TableNumber == tableNumber;
    }
}
package ir.ut.ie.contracts;

import ir.ut.ie.models.*;
import ir.ut.ie.utils.PairType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public record ReserveModel(
    String restaurant,
    int tableNumber,
    String reservee,
    LocalDateTime reserveTime,
    boolean isCanceled,
    Restaurant.Address address
) {


    public static ReserveModel FromObject(Reserve reserve){
        return new ReserveModel(
                reserve.getTable().getRestaurant().getName(),
                reserve.getTable().getTableNumber(),
                reserve.getReserveeUsername(),
                reserve.GetReserveTime(),
                reserve.getIsCanceled(),
                reserve.getTable().getRestaurant().getRestaurantAddress()
        );
    }

}

package ir.ut.ie.contracts;

import ir.ut.ie.models.*;

import java.time.LocalDateTime;

public record ReserveModel(
    String restaurant,
    int tableNumber,
    String reservee,
    LocalDateTime reserveTime,
    boolean isCanceled,
    RestaurantAddressModel address
) {


    public static ReserveModel fromDomainObject(Reserve model){
        return new ReserveModel(
                model.getTable().getRestaurant().getName(),
                model.getTable().getTableNumber(),
                model.getReservee().getUsername(),
                model.GetReserveTime(),
                model.getIsCanceled(),
                RestaurantAddressModel.fromDomainObject(model.getTable().getRestaurant().getRestaurantAddress())
        );
    }

}
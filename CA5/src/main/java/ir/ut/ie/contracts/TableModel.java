package ir.ut.ie.contracts;

import ir.ut.ie.models.Table;

public record TableModel(
        int tableNumber,
        int numberOfSeats,
        String restaurantName,
        String managerName
){

    public static TableModel fromDomainObject(Table model){
        return new TableModel(
                model.getTableNumber(),
                model.getNumberOfSeats(),
                model.getRestaurant().getName(),
                model.getRestaurant().getManagerUsername()
        );
    }
}
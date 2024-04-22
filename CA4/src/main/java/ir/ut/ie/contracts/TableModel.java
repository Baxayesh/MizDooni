package ir.ut.ie.contracts;

import ir.ut.ie.models.Reserve;
import ir.ut.ie.models.Restaurant;
import ir.ut.ie.models.Table;

import java.util.ArrayList;

public record TableModel(
        int tableNumber,
        int numberOfSeats,
        String restaurantName,
        String managerName
){

    public static TableModel FromTableObject(Table table){
        return new TableModel(
                table.getTableNumber(),
                table.getNumberOfSeats(),
                table.getRestaurant().getName(),
                table.getRestaurant().getManagerUsername()
        );
    }
}
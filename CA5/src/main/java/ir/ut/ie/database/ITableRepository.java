package ir.ut.ie.database;

import ir.ut.ie.models.Table;
import jdk.jshell.spi.ExecutionControl;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ITableRepository {

    int add(Table table);

    //CHECK: should be moved to reserves?
    LocalTime[] getAvailableTimes(String restaurantName, LocalDate requestDate, int requestedSeats);

    Table get(String restaurant, int number);
}

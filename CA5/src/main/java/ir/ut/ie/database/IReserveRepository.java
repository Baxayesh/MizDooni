package ir.ut.ie.database;

import ir.ut.ie.exceptions.NotExistentReserve;
import ir.ut.ie.models.Reserve;
import jdk.jshell.spi.ExecutionControl;

public interface IReserveRepository {


    int add(Reserve reserve);

    boolean doUserHasAnyPassedReserveAt(String user, String restaurant);

    Reserve get(String reservee, int reserveNumber) throws NotExistentReserve;

    Reserve[] get(String reservee);

    void save(Reserve reserve);
}

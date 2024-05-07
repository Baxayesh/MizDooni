package ir.ut.ie.models;

import ir.ut.ie.exceptions.CancelingCanceledReserve;
import ir.ut.ie.exceptions.CancelingExpiredReserve;
import lombok.Getter;
import lombok.Setter;
import ir.ut.ie.utils.PairType;

import java.time.LocalDateTime;

@Getter
@Setter
public class Reserve extends EntityModel<PairType<String,Integer>> {

    private int ReserveNumber;
    private Restaurant Restaurant;
    private Table Table;
    private Client Reservee;
    private LocalDateTime ReserveTime;
    private Boolean IsCanceled;


    public Reserve(int reserveNumber, Table table, Client reservee, LocalDateTime reserveTime) {
        super(new PairType<>(reservee.getUsername(), reserveNumber));
        ReserveNumber = reserveNumber;
        Table = table;
        Restaurant = table.getRestaurant();
        Reservee = reservee;
        ReserveTime = reserveTime;
        IsCanceled = false;
    }

    public void Cancel() throws CancelingExpiredReserve, CancelingCanceledReserve {

        if(IsCanceled)
            throw new CancelingCanceledReserve();

        if(IsPassed())
            throw new CancelingExpiredReserve();

        IsCanceled = true;
    }


    public boolean IsActive() {
        return ! IsCanceled;
    }

    public boolean IsPassed() { return ReserveTime.isBefore(LocalDateTime.now()); }
    public LocalDateTime GetReserveTime() {
        return ReserveTime;
    }
}
package models;

import exceptions.CancelingCanceledReserve;
import exceptions.CancelingExpiredReserve;
import lombok.Getter;
import lombok.Setter;
import utils.PairType;

import java.time.LocalDateTime;

@Getter
@Setter
public class Reserve extends EntityModel<PairType<String,Integer>> {

    private Table Table;
    private User Reservee;
    private LocalDateTime ReserveTime;
    private Boolean IsCanceled;

    public int getReserveNumber() {
        return super.getKey().getSecond();
    }
    public String getReserveeUsername(){
        return super.getKey().getFirst();
    }

    public Reserve(int reserveNumber, Table table, User reservee, LocalDateTime reserveTime) {
        super(new PairType<>(reservee.getUsername(), reserveNumber));
        this.Table = table;
        Reservee = reservee;
        ReserveTime = reserveTime;
        IsCanceled = false;
    }

    public void Cancel() throws CancelingExpiredReserve, CancelingCanceledReserve {

        if(IsCanceled)
            throw new CancelingCanceledReserve();

        if(ReserveTime.isBefore(LocalDateTime.now()))
            throw new CancelingExpiredReserve();

        IsCanceled = true;
    }


    public boolean IsActive() {
        return ! IsCanceled;
    }

    public LocalDateTime GetReserveTime() {
        return ReserveTime;
    }
}
package model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Reserve extends EntityModel<Integer> {

    private Table Table;
    private User Reservee;
    private LocalDateTime ReserveTime;
    private Boolean IsCanceled;

    public int getReserveNumber() {
        return super.getKey();
    }

    public Reserve(int reserveNumber, Table table, User reservee, LocalDateTime reserveTime) {
        super(reserveNumber);
        this.Table = table;
        Reservee = reservee;
        ReserveTime = reserveTime;
        IsCanceled = false;
    }

    public boolean IsActive() {
        return ! IsCanceled;
    }

    public LocalDateTime GetReserveTime() {
        return ReserveTime;
    }
}
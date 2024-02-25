package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Setter
public class Reserve {

    private Table Table;
    private User Reservee;
    private LocalDateTime ReserveTime;
    private Boolean IsCanceled;

    public Reserve(Table table, User reservee, LocalDateTime reserveTime) {
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
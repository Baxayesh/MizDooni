package ir.ut.ie.models;

import ir.ut.ie.exceptions.CancelingCanceledReserve;
import ir.ut.ie.exceptions.CancelingExpiredReserve;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@jakarta.persistence.Table(name = "RESERVES")
public class Reserve implements Serializable {

    @Id
    @GeneratedValue
    private int ReserveNumber;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Restaurant Restaurant;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Table Table;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Client Reservee;

    @Column(nullable = false)
    private LocalDateTime ReserveTime;
    @Column(nullable = false)
    private Boolean IsCanceled;

    public Reserve(Table table, Client reservee, LocalDateTime reserveTime) {
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
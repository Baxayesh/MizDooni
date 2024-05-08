package ir.ut.ie.models;

import ir.ut.ie.exceptions.CancelingCanceledReserve;
import ir.ut.ie.exceptions.CancelingExpiredReserve;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ir.ut.ie.utils.PairType;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Reserve extends EntityModel<PairType<String,Integer>> {

    @Id
    private int ReserveNumber;

    @OneToOne(optional = false)
    @JoinColumn(nullable = false)
    private Restaurant Restaurant;

    @OneToOne(optional = false)
    @JoinColumn(nullable = false)
    private Table Table;

    @OneToOne(optional = false)
    @JoinColumn(nullable = false)
    private Client Reservee;

    @Column(nullable = false)
    private LocalDateTime ReserveTime;
    @Column(nullable = false)
    private Boolean IsCanceled;

    @Override
    public PairType<String, Integer> getKey() {
        return new PairType<>(this.getReservee().getUsername(), this.getReserveNumber());
    }


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
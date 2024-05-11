package ir.ut.ie.database;

import ir.ut.ie.exceptions.NotExistentReserve;
import ir.ut.ie.models.Reserve;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;

@Repository
@Setter
@Getter
@NoArgsConstructor
public class ReserveRepository implements IReserveRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public ReserveRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public int add(Reserve reserve) {
        entityManager.persist(reserve);
        return reserve.getReserveNumber();
    }

    @Override
    public boolean doUserHasAnyPassedReserveAt(String user, String restaurant) {

        var query = entityManager.createQuery("select count(r) from Reserve r " +
                "where r.Reservee.Username = :username_pr and r.Restaurant.Name = :restaurant_pr and " +
                "r.ReserveTime < CURRENT_TIMESTAMP", Long.class);

        query.setParameter("username_pr", user);
        query.setParameter("restaurant_pr", restaurant);

        return query.getSingleResult() > 0;
    }

    @Override
    public Reserve get(String reservee, int reserveNumber) throws NotExistentReserve {

        var reserve = entityManager.find(Reserve.class, reserveNumber);

        if(reserve == null || !reserve.getReservee().is(reservee))
            throw new NotExistentReserve();

        return reserve;
    }

    @Override
    public Reserve[] get(String reservee) {

        var query = entityManager.createQuery("from Reserve r " +
                "where r.Reservee.Username = :reservee_pr", Reserve.class);

        query.setParameter("reservee_pr", reservee);

        return  query.getResultList().toArray(Reserve[]::new);
    }

    @Override
    public void update(Reserve reserve) {
        entityManager.merge(reserve);
    }
}

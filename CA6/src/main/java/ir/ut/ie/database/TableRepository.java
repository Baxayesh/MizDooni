package ir.ut.ie.database;

import ir.ut.ie.exceptions.MizdooniNotFoundException;
import ir.ut.ie.models.Table;
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
public class TableRepository implements ITableRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public TableRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public int add(Table table) {
        entityManager.persist(table);
        return table.getTableNumber();
    }

    @Override
    public Table get(String restaurant, int number) throws MizdooniNotFoundException {
        var table = entityManager.find(Table.class, number);

        if(table == null || !table.getRestaurant().is(restaurant))
            throw new MizdooniNotFoundException("Table");

        return table;
    }
}

package ir.ut.ie.database;

import ir.ut.ie.exceptions.MizdooniNotFoundException;
import ir.ut.ie.models.Table;

public interface ITableRepository {

    int add(Table table);

    Table get(String restaurant, int number) throws MizdooniNotFoundException;
}

package ir.ut.ie.testUtils.fakes;

import ir.ut.ie.database.ITableRepository;
import ir.ut.ie.exceptions.MizdooniNotFoundException;
import ir.ut.ie.models.Table;

import java.util.ArrayList;
import java.util.List;

public class FakeTableRepo implements ITableRepository {


    List<Table> memory;

    public FakeTableRepo(){
        memory = new ArrayList<>();
    }

    @Override
    public int add(Table table) {
        memory.add(table);
        table.setTableNumber(memory.size());
        return table.getTableNumber();
    }

    @Override
    public Table get(String restaurant, int number) throws MizdooniNotFoundException {
        for (var table : memory){
            if(table.getRestaurant().is(restaurant) & table.getTableNumber() == number)
                return table;
        }

        throw new MizdooniNotFoundException("Table");
    }
}

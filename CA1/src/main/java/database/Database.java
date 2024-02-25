package database;

import model.*;
import utils.PairType;

public class Database {

    public final IRepository<String, User> Users;
    public final IRepository<String, Restaurant> Restaurants;
    public final IRepository<PairType<String, Integer>, Table> Tables;
    public final IRepository<Integer, Reserve> Reserves;

    public final KeyGenerator ReserveIdGenerator;

    public Database(){
        Users = new InMemoryRepo<>();
        Restaurants = new InMemoryRepo<>();
        Tables = new InMemoryRepo<>();
        Reserves = new InMemoryRepo<>();
        ReserveIdGenerator = new KeyGenerator();
    }

}


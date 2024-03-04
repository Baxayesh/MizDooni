package database;

import models.Reserve;
import models.Restaurant;
import models.Table;
import models.User;
import utils.PairType;

public class Database {

    public final IRepository<String, User> Users;
    public final IRepository<String, Restaurant> Restaurants;
    public final IRepository<PairType<String, Integer>, Table> Tables;
    public final IRepository<PairType<String, Integer>, Reserve> Reserves;

    public final KeyGenerator ReserveIdGenerator;

    public Database(
            IRepository<String, User> users,
            IRepository<String, Restaurant> restaurants,
            IRepository<PairType<String, Integer>, Table> tables,
            IRepository<PairType<String, Integer>, Reserve> reserves
    ) {
        Users = users;
        Restaurants = restaurants;
        Tables = tables;
        Reserves = reserves;
        ReserveIdGenerator = new KeyGenerator();
    }

    public static Database CreateInMemoryDatabase(){
        return new Database(
            new InMemoryRepo<>(),
            new InMemoryRepo<>(),
            new InMemoryRepo<>(),
            new InMemoryRepo<>()
        );
    }

}


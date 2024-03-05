package database;

import models.*;
import utils.PairType;

public class Database {

    public final IRepository<String, User> Users;
    public final IRepository<String, Restaurant> Restaurants;
    public final IRepository<PairType<String, Integer>, Table> Tables;
    public final IRepository<PairType<String, Integer>, Reserve> Reserves;

    public final IRepository<PairType<String, String>, Review> Reviews;

    public final KeyGenerator ReserveIdGenerator;

    public Database(
            IRepository<String, User> users,
            IRepository<String, Restaurant> restaurants,
            IRepository<PairType<String, Integer>, Table> tables,
            IRepository<PairType<String, Integer>, Reserve> reserves,
            IRepository<PairType<String, String>, Review> reviews
    ) {
        Users = users;
        Restaurants = restaurants;
        Tables = tables;
        Reserves = reserves;
        Reviews = reviews;
        ReserveIdGenerator = new KeyGenerator();
    }

    public static Database CreateInMemoryDatabase(){
        return new Database(
            new InMemoryRepo<>(),
            new InMemoryRepo<>(),
            new InMemoryRepo<>(),
            new InMemoryRepo<>(),
            new InMemoryRepo<>()
        );
    }

}


package ir.ut.ie.database;

public class Database {

    public final IUserRepository UserRepo;
    public final IRestaurantRepository RestaurantRepo;
    public final ITableRepository TableRepo;
    public final IReserveRepository ReserveRepo;
    public final IReviewRepository ReviewRepo;

    public Database(
            IUserRepository userRepo,
            IRestaurantRepository restaurantRepo,
            ITableRepository tableRepo,
            IReserveRepository reserveRepo,
            IReviewRepository reviewRepo
    ) {
        UserRepo = userRepo;
        RestaurantRepo = restaurantRepo;
        TableRepo = tableRepo;
        ReserveRepo = reserveRepo;
        ReviewRepo = reviewRepo;
    }

    public Database(){
        // :(
        UserRepo = null;
        RestaurantRepo = null;
        TableRepo = null;
        ReserveRepo = null;
        ReviewRepo = null;
    }

}


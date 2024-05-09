package ir.ut.ie.database;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Database {

    public final IUserRepository UserRepo;
    public final IRestaurantRepository RestaurantRepo;
    public final ITableRepository TableRepo;
    public final IReserveRepository ReserveRepo;
    public final IReviewRepository ReviewRepo;

    @Autowired
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

    public static Database createUsing(EntityManager em){
        return new Database(
                new UserRepository(em),
                new RestaurantRepository(em),
                new TableRepository(em),
                new ReserveRepository(em),
                new ReviewRepository(em)
        );
    }

}


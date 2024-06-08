package ir.ut.ie.database;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Database {

    public final IUserRepository UserRepo;
    public final IRestaurantRepository RestaurantRepo;
    public final ITableRepository TableRepo;
    public final IReserveRepository ReserveRepo;
    public final IReviewRepository ReviewRepo;

}
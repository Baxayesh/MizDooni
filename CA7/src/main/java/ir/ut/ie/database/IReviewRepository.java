package ir.ut.ie.database;

import ir.ut.ie.exceptions.MizdooniNotFoundException;
import ir.ut.ie.models.Review;

public interface IReviewRepository {

    Review[] get(String restaurantName, int offset, int limit);

    long count(String restaurantName);

    Review get(String restaurantName, String issuer) throws MizdooniNotFoundException;

    boolean exists(String restaurantName, String issuer);

    void upsert(Review review);
}
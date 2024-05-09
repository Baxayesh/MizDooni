package ir.ut.ie.database;

import ir.ut.ie.exceptions.MizdooniNotFoundException;
import ir.ut.ie.models.Review;
import jdk.jshell.spi.ExecutionControl;

public interface IReviewRepository {

    Review[] get(String restaurantName, int offset, int limit);

    int count(String restaurantName);

    Review get(String restaurantName, String issuer) throws MizdooniNotFoundException;

    void upsert(Review review);
}
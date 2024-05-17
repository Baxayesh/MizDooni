package ir.ut.ie.testUtils.fakes;

import ir.ut.ie.database.IReviewRepository;
import ir.ut.ie.exceptions.MizdooniNotFoundException;
import ir.ut.ie.models.Review;

import java.util.ArrayList;
import java.util.List;


public class FakeReviewRepo implements IReviewRepository {


    List<Review> reviews;

    public FakeReviewRepo(){
        reviews = new ArrayList<>();
    }


    int find(String restaurantName, String issuer){

        for (int i = 0; i < reviews.size(); i++){
            if(reviews.get(i).getRestaurant().is(restaurantName) && reviews.get(i).getIssuer().is(issuer)){
                return i;
            }
        }

        return -1;
    }

    @Override
    public Review[] get(String restaurantName, int offset, int limit) {
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public long count(String restaurantName) {
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public Review get(String restaurantName, String issuer) throws MizdooniNotFoundException {

        var index = find(restaurantName, issuer);

        if(index < 0)
            throw new MizdooniNotFoundException("Review");

        return reviews.get(index);
    }

    @Override
    public boolean exists(String restaurantName, String issuer) {
        return find(restaurantName, issuer) >= 0;
    }

    @Override
    public void upsert(Review review) {
        var index = find(review.getRestaurant().getName(), review.getIssuer().getUsername());

        if(index < 0){
            reviews.add(review);
            review.setId(reviews.size());
        }else{
            reviews.remove(index);
            reviews.add(review);
        }
    }
}
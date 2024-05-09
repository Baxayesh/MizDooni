package ir.ut.ie.database;

import ir.ut.ie.exceptions.MizdooniNotFoundException;
import ir.ut.ie.models.Rating;
import ir.ut.ie.models.Review;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;

@Repository
@Setter
@Getter
@NoArgsConstructor
public class ReviewRepository implements IReviewRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public ReviewRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Review[] get(String restaurantName, int offset, int limit) {

        var query = entityManager.createQuery("from Review r where " +
                "r.restaurant.name = :restaurantName_pr", Review.class);

        query.setParameter("restaurantName_pr", restaurantName);
        query.setFirstResult(offset);
        query.setMaxResults(limit);

        return query.getResultList().toArray(Review[]::new);
    }

    @Override
    public int count(String restaurantName) {
        var query = entityManager.createQuery("select count(r) from Review r where " +
                "r.restaurant.name = :restaurantName_pr", Integer.class);

        query.setParameter("restaurantName_pr", restaurantName);

        return query.getSingleResult();
    }

    @Override
    public Review get(String restaurantName, String issuer) throws MizdooniNotFoundException {
        var review = fetch(restaurantName, issuer);

        if(review == null)
            throw new MizdooniNotFoundException("Review");

        return review;
    }

    public Review fetch(String restaurantName, String issuer){

        var query = entityManager.createQuery("from Review r where " +
                "r.restaurant.name = :restaurantName_pr and r.issuer.username = :issuer_pr", Review.class);

        query.setParameter("restaurantName_pr", restaurantName);
        query.setParameter("issuer_pr", issuer);

        return query.getSingleResult();

    }

    @Override
    public void upsert(Review review) {
        var previousReview = fetch(review.getRestaurant().getName(), review.getIssuer().getUsername());
        var rating = entityManager.find(Rating.class, review.getRestaurant());

        if(previousReview == null){
            rating.ConsiderReview(review);
        }else{
            rating.UpdateReview(previousReview, review);
            entityManager.remove(previousReview);
        }

        entityManager.merge(rating);
        entityManager.persist(review);
    }
}

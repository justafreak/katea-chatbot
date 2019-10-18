package ml.strikers.kateaserver.fulfilment.repository;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.KeyFactory;
import ml.strikers.kateaserver.fulfilment.entity.Review;
import ml.strikers.kateaserver.fulfilment.repository.mapping.ReviewEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class ReviewRepository extends AbstractRepository<Review, UUID> {

    private KeyFactory reviewKeyFactory = datastore.newKeyFactory().setKind(Review.KIND);

    private final ReviewEntityMapper reviewEntityMapper;

    public ReviewRepository(Datastore datastore, ReviewEntityMapper reviewEntityMapper) {
        super(datastore);
        this.reviewEntityMapper = reviewEntityMapper;
    }

    @Override
    public Review save(Review review) {
        datastore.add(reviewEntityMapper.buildReviewEntity(reviewKeyFactory, review));
        return review;
    }

}

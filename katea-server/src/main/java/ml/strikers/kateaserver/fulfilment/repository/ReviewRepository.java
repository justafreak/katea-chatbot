package ml.strikers.kateaserver.fulfilment.repository;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.StringValue;
import ml.strikers.kateaserver.fulfilment.entity.Review;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ReviewRepository extends AbstractRepository<Review, UUID> {

    public ReviewRepository(Datastore datastore) {
        super(datastore, Review.KIND);
    }

    @Override
    public Review save(Review review) {
        final var hack = review.getReviews().toString().replace("[", "").replace("]", "");
        final var reviewEntity = Entity.newBuilder(keyFactory.newKey())
                .set(Review.ID, review.getId().toString())
                .set(Review.URL, review.getUrl())
                .set(Review.REVIEWS, StringValue.newBuilder(hack)
                        .setExcludeFromIndexes(true)
                        .build())
                .build();
        datastore.add(reviewEntity);
        return review;
    }

    @Override
    public List<Review> getEntityByProperty(String key, Object value) {
        return null;
    }

}

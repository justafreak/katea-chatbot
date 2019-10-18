package ml.strikers.kateaserver.fulfilment.repository;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.IncompleteKey;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.StringValue;
import ml.strikers.kateaserver.fulfilment.entity.Review;
import ml.strikers.kateaserver.util.SerializationUtil;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static com.google.cloud.datastore.FullEntity.newBuilder;

@Repository
public class ReviewRepository extends AbstractRepository<Review, UUID> {

    public ReviewRepository(Datastore datastore) {
        super(datastore);
    }

    private KeyFactory reviewKeyFactory = datastore.newKeyFactory().setKind(Review.KIND);

    @Override
    public Review save(Review review) {
        final var reviewEntity = newBuilder(reviewKeyFactory.newKey())
                .set(Review.ID, review.getId().toString())
                .set(Review.URL, review.getUrl())
                .set(Review.REVIEWS, StringValue.newBuilder(SerializationUtil.write(review.getReviews()))
                        .setExcludeFromIndexes(true)
                        .build())
                .build();
        datastore.add(reviewEntity);
        return review;
    }

}

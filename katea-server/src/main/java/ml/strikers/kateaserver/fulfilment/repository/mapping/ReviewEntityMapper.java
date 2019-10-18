package ml.strikers.kateaserver.fulfilment.repository.mapping;

import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.IncompleteKey;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.StringValue;
import ml.strikers.kateaserver.fulfilment.entity.Review;
import ml.strikers.kateaserver.util.SerializationUtil;
import org.springframework.stereotype.Component;

import static com.google.cloud.datastore.FullEntity.newBuilder;

@Component
public class ReviewEntityMapper {

    public FullEntity<IncompleteKey> buildReviewEntity(KeyFactory reviewKeyFactory, Review review) {
        return newBuilder(reviewKeyFactory.newKey())
                .set(Review.ID, review.getId().toString())
                .set(Review.URL, review.getUrl())
                .set(Review.REVIEWS, StringValue.newBuilder(SerializationUtil.write(review.getReviews()))
                        .setExcludeFromIndexes(true)
                        .build())
                .build();
    }
}

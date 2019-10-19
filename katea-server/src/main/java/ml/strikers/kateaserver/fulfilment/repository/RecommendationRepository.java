package ml.strikers.kateaserver.fulfilment.repository;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.KeyFactory;
import ml.strikers.kateaserver.fulfilment.repository.mapping.RecommendationEntityMapper;
import ml.strikers.kateaserver.fulfilment.service.Recommendation;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class RecommendationRepository extends AbstractRepository<Recommendation, UUID> {

    private final KeyFactory hotelKeyFactory = datastore.newKeyFactory().setKind(Recommendation.KIND);

    private final RecommendationEntityMapper recommendationEntityMapper;

    public RecommendationRepository(Datastore datastore, RecommendationEntityMapper recommendationEntityMapper) {
        super(datastore);
        this.recommendationEntityMapper = recommendationEntityMapper;
    }

    @Override
    public Recommendation save(Recommendation recommendation) {
        datastore.add(recommendationEntityMapper.buildRecommendationEntity(hotelKeyFactory, recommendation));
        return recommendation;
    }


}

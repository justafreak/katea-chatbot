package ml.strikers.kateaserver.fulfilment.repository;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.KeyFactory;
import lombok.extern.slf4j.Slf4j;
import ml.strikers.kateaserver.fulfilment.entity.Recommendation;
import ml.strikers.kateaserver.fulfilment.repository.mapping.RecommendationEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@Slf4j
public class RecommendationRepository extends AbstractRepository<Recommendation, UUID> {

    private final KeyFactory hotelKeyFactory = datastore.newKeyFactory().setKind(Recommendation.KIND);

    private final RecommendationEntityMapper recommendationEntityMapper;

    public RecommendationRepository(Datastore datastore, RecommendationEntityMapper recommendationEntityMapper) {
        super(datastore);
        this.recommendationEntityMapper = recommendationEntityMapper;
    }

    @Override
    public Recommendation save(Recommendation recommendation) {
        try {
            datastore.add(recommendationEntityMapper.buildRecommendationEntity(hotelKeyFactory, recommendation));
        } catch (Exception e) {
            log.error("Unable to save recommendation", e);
        }
        return recommendation;
    }


}

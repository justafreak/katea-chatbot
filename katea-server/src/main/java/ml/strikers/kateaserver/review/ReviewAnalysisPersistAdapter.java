package ml.strikers.kateaserver.review;

import ml.strikers.kateaserver.fulfilment.entity.Recommendation;
import ml.strikers.kateaserver.fulfilment.repository.HotelRepository;
import ml.strikers.kateaserver.fulfilment.repository.RecommendationRepository;
import ml.strikers.kateaserver.fulfilment.service.RecommendationMapper;
import ml.strikers.kateaserver.review.entity.HotelReviewsAnalysisResponse;
import ml.strikers.kateaserver.review.entity.ReviewAnalysisResponse;
import ml.strikers.kateaserver.review.entity.SentimentValue;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

@Service
public class ReviewAnalysisPersistAdapter {

    private final HotelRepository hotelRepository;
    private final RecommendationRepository recommendationRepository;

    public ReviewAnalysisPersistAdapter(HotelRepository hotelRepository,
                                        RecommendationRepository recommendationRepository) {
        this.hotelRepository = hotelRepository;
        this.recommendationRepository = recommendationRepository;
    }


    void persist(HotelReviewsAnalysisResponse response) {
        response.getReviewResponses()
                .stream()
                .filter(reviewResponse -> Objects.nonNull(reviewResponse.getSentiment()) && reviewResponse.getSentiment().getValue() == SentimentValue.positive)
                .map(reviewResponse -> reviewToRecommendation(response.getHotelId(), reviewResponse))
                .forEach(recommendationRepository::save);
//        hotelRepository.update(response);

    }

    private Recommendation reviewToRecommendation(String hotelId, ReviewAnalysisResponse response) {
        Recommendation recommendation = RecommendationMapper.map(new ArrayList<>(response.getFacilities()));
        recommendation.setId(UUID.randomUUID());
        recommendation.setHotelId(UUID.fromString(hotelId));
        recommendation.setReview(response.getReview());
        recommendation.setSentimentScore(response.getSentiment().getScore());
        return recommendation;
    }
}

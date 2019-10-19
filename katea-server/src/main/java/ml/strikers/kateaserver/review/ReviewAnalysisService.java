package ml.strikers.kateaserver.review;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.gax.rpc.InvalidArgumentException;
import com.google.cloud.dialogflow.v2.QueryResult;
import com.google.protobuf.Value;
import lombok.extern.slf4j.Slf4j;
import ml.strikers.kateaserver.exception.DeserializationException;
import ml.strikers.kateaserver.fulfilment.service.DialogProvider;
import ml.strikers.kateaserver.review.entity.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ReviewAnalysisService {

    private final static String REVIEWS_FILE_PATH = "/data/reviews.json";
    private final static String SWITCH_TO_REVIEW_CONTEXT_PHRASE = "ExtremelyRandomPhraseUnlikelyToBeWrittenByAnyone";

    private final ObjectMapper json = new ObjectMapper();
    private final DialogProvider dialogProvider;
    private final ReviewAnalysisPersistAdapter persistAdapter;

    public ReviewAnalysisService(DialogProvider dialogProvider, ReviewAnalysisPersistAdapter persistAdapter) {
        this.dialogProvider = dialogProvider;
        this.persistAdapter = persistAdapter;
    }

    public String startReviewAnalysis() {
        try {
            UUID contextSession = switchToReviewContext();
            InputStream input = ReviewAnalysisService.class.getResourceAsStream(REVIEWS_FILE_PATH);
            List<HotelReviews> hotelReviewsList = json.readValue(input, json.getTypeFactory().constructCollectionType(List.class, HotelReviews.class));
            hotelReviewsList.stream()
                    .map(hotelReviews -> analyzeHotelReviews(hotelReviews, contextSession))
                    .forEach(persistAdapter::persist);
            return "Review analysis done for " + hotelReviewsList.size() + " hotels";
        } catch (IOException e) {
            throw new DeserializationException(e.getMessage());
        }
    }


    private HotelReviewsAnalysisResponse analyzeHotelReviews(HotelReviews hotelReviews, UUID contextSession) {
        List<ReviewAnalysisResponse> reviewsResponse = hotelReviews.getReviews()
                .stream()
                .map(review -> analyzeReview(review, contextSession))
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
        return aggregateReviewsPerHotel(hotelReviews.getId(), reviewsResponse);
    }

    private HotelReviewsAnalysisResponse aggregateReviewsPerHotel(String hotelId, List<ReviewAnalysisResponse> reviewsResponses) {
        HotelReviewsAnalysisResponse hotelResponse = new HotelReviewsAnalysisResponse();
        hotelResponse.setHotelId(hotelId);
        hotelResponse.setReviewResponses(reviewsResponses);
        maybeHotelSentiment(reviewsResponses).ifPresent(hotelResponse::setSentiment);
        hotelResponse.setFacilities(positiveFacilities(reviewsResponses));
        return hotelResponse;
    }

    private Set<String> positiveFacilities(List<ReviewAnalysisResponse> reviewsResponses) {
        return reviewsResponses.stream().filter(review -> review.getSentiment() != null && SentimentValue.positive.equals(review.getSentiment().getValue()))
                .flatMap(reviewAnalysisResponse -> reviewAnalysisResponse.getFacilities().stream())
                .collect(Collectors.toSet());
    }

    private Optional<Sentiment> maybeHotelSentiment(List<ReviewAnalysisResponse> reviewsResponses) {
        OptionalDouble maybeHotelScore = reviewsResponses.stream()
                .filter(response -> Objects.nonNull(response.getSentiment()))
                .map(ReviewAnalysisResponse::getSentiment)
                .mapToDouble(sentiment -> {
                    var scoreMultiplier = sentiment.getValue() == SentimentValue.positive ? 1 : -1;
                    return sentiment.getScore() * scoreMultiplier;
                })
                .average();
        return maybeHotelScore.stream().mapToObj(score -> {
            var sentiment = score >= 0 ? SentimentValue.positive : SentimentValue.negative;
            return new Sentiment(sentiment, Math.abs(score));
        }).findAny();
    }

    private Optional<ReviewAnalysisResponse> analyzeReview(String review, UUID sessionId) {
        try {
            QueryResult queryResult = dialogProvider.getDialogFlowResponse(review, sessionId);
            Map<String, Value> parameters = queryResult.getParameters().getFieldsMap();

            Optional<Sentiment> maybeSentiment = maybeReviewSentiment(parameters);
            Value quality = parameters.get("quality");
            if (quality == null) {
                return Optional.empty();
            }
            Set<String> reviewFacilities = quality.getListValue().getValuesList()
                    .stream()
                    .map(Value::getStringValue)
                    .collect(Collectors.toSet());

            if (maybeSentiment.isEmpty() && reviewFacilities.isEmpty()) {
                return Optional.empty();
            } else {
                var response = new ReviewAnalysisResponse();
                response.setReview(review);
                response.setFacilities(reviewFacilities);
                maybeSentiment.ifPresent(response::setSentiment);
                return Optional.of(response);
            }
        } catch (InvalidArgumentException e) {
            log.error("Unable to do DialogFlow request", e);
            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private Optional<Sentiment> maybeReviewSentiment(Map<String, Value> parameters) {
        Value reviewSentiment = parameters.get("review-sentiment");
        if (reviewSentiment == null) {
            return Optional.empty();
        }
        List<String> reviewSentiments = reviewSentiment
                .getListValue()
                .getValuesList()
                .stream()
                .map(Value::getStringValue)
                .collect(Collectors.toList());
        Map<String, Long> sentiments = reviewSentiments.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        if (reviewSentiments.isEmpty()) {
            return Optional.empty();
        }
        Long positiveCount = sentiments.getOrDefault(SentimentValue.positive.toString(), 0L);
        Long negativeCount = sentiments.getOrDefault(SentimentValue.negative.toString(), 0L);
        if (positiveCount > negativeCount) {
            return Optional.of(new Sentiment(SentimentValue.positive, positiveCount / (double) (positiveCount + negativeCount)));
        } else {
            return Optional.of(new Sentiment(SentimentValue.negative, negativeCount / (double) (positiveCount + negativeCount)));
        }
    }

    private UUID switchToReviewContext() {
        try {
            UUID uuid = UUID.randomUUID();
            QueryResult queryResponse = dialogProvider.getDialogFlowResponse(SWITCH_TO_REVIEW_CONTEXT_PHRASE, uuid);
            log.info("Review analysis query response: " + queryResponse);
            return uuid;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}

package ml.strikers.kateaserver.review.entity;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class HotelReviewsAnalysisResponse {
    private String hotelId;
    private List<ReviewAnalysisResponse> reviewResponses;
    private Sentiment sentiment;
    private Set<String> facilities;
}

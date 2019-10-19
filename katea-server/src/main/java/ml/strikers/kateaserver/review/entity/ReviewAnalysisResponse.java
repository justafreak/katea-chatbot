package ml.strikers.kateaserver.review.entity;

import lombok.Data;

import java.util.Set;

@Data
public class ReviewAnalysisResponse {
    private String review;
    private Sentiment sentiment;
    private Set<String> facilities;
}
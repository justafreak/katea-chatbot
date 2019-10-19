package ml.strikers.kateaserver.review.entity;

import lombok.Data;

@Data
public class Sentiment {
    private SentimentValue value;
    private double score;

    public Sentiment(SentimentValue value, double score) {
        this.value = value;
        this.score = score;
    }
}

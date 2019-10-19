package ml.strikers.kateaserver.review.entity;

import lombok.Data;

import java.util.List;

@Data
public class HotelReviews {
    private String id;
    private String url;
    private List<String> reviews;
}

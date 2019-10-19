package ml.strikers.kateaserver.fulfilment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.strikers.kateaserver.review.entity.Sentiment;
import ml.strikers.kateaserver.review.entity.SentimentValue;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {

    public static final String KIND = "Hotels";

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String IMAGE_URL = "imageUrl";
    public static final String URL = "url";
    public static final String RATING = "rating";
    public static final String CITY = "city";
    public static final String PRICE = "price";
    public static final String VENUE_TYPE = "venueType";
    public static final String ZONE = "zone";
    public static final String REVIEW_COUNT = "reviewCount";
    public static final String FACILITIES = "facilities";
    public static final String LAT_LONG = "latLong";
    public static final String SENTIMENT_VALUE = "sentimentValue";
    public static final String SENTIMENT_SCORE = "sentimentScore";
    public static final String REVIEW_RECOMMENDED_FACILITIES = "reviewRecommendedFacilities";

    private UUID id;
    private String name;
    private String imageUrl;
    private String url;
    private Double rating;
    private String city;
    private Price price;
    private String venueType;
    private String zone;
    private long reviewCount;
    private List<String> facilities;
    private String latLong;
    private int recommendScore;
    private SentimentValue sentimentValue;
    private double sentimentScore;
    private List<String> reviewRecommendedFacilities;

    public static final Comparator<Hotel> SCORE_COMPARATOR = Comparator.comparing(Hotel::getRecommendScore);


}

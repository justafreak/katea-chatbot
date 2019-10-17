package ml.strikers.kateaserver.fulfilment.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Hotel {

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String IMAGE_URL = "imageUrl";
    public static final String RATING = "rating";
    public static final String CITY = "city";
    public static final String PRICE_VALUE = "priceValue";
    public static final String PRICE_CURRENCY = "priceCurrency";
    public static final String VENUE_TYPE = "venueType";
    public static final String ZONE = "zone";
    public static final String REVIEW_COUNT = "reviewCount";
    public static final String FACILITIES = "facilities";
    public static final String LAT_LONG = "latLong";

    private UUID id;
    private String name;
    private String imageUrl;
    private String url;
    private Double rating;
    private String city;
    private Price price;
    private String venueType;
    private String zone;
    private int reviewCount;
    private List<String> facilities;
    private String latLong;

}

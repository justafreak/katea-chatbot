package ml.strikers.kateaserver.fulfilment.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Recommendation {

    public static final String KIND = "Recommendation";

    public static final String ID = "id";
    public static final String SESSION_ID = "session_id";
    public static final String HOTEL_ID = "hotel_id";
    public static final String LIKE = "like";
    public static final String ACCOMMODATION_QUALITY_WIFI = "accomodation_quality_wifi";
    public static final String ACCOMMODATION_QUALITY_STAFF = "accomodation_quality_staff";
    public static final String ACCOMMODATION_QUALITY_LOCATION = "accomodation_quality_location";
    public static final String ACCOMMODATION_QUALITY_PRICE = "accomodation_quality_price";
    public static final String ACCOMMODATION_QUALITY_QUIET = "accomodation_quality_quiet";
    public static final String ACCOMMODATION_QUALITY_BREAKFAST = "accomodation_quality_breakfast";
    public static final String ACCOMMODATION_QUALITY_CLEANLINESS = "accomodation_quality_cleanliness";
    public static final String TRAVEL_TYPE_WORK = "travel_type_work";
    public static final String TRAVEL_TYPE_HONEYMOON = "travel_type_honeymoon";
    public static final String TRAVEL_TYPE_CITY_BREAK = "travel_type_citybreak";
    public static final String TRAVEL_TYPE_HOLIDAY = "travel_type_holiday";
    public static final String TRAVEL_TYPE_COMPANION_SOLO = "travel_companion_solo";
    public static final String TRAVEL_TYPE_COMPANION_KIDS = "travel_companion_kids";
    public static final String TRAVEL_TYPE_COMPANION_COUPLE = "travel_companion_couple";
    public static final String TRAVEL_TYPE_COMPANION_FRIENDS = "travel_companion_friends";
    public static final String SENTIMENT_SCORE = "sentiment_score";
    public static final String REVIEW = "review";

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("sessionId")
    private UUID sessionId;

    @JsonProperty("hotel_id")
    private UUID hotelId;

    @JsonProperty("like")
    private double like;

    @JsonProperty("accomodation_quality_wifi")
    private double accommodationQualityWifi;

    @JsonProperty("accomodation_quality_staff")
    private double accommodationQualityStaff;

    @JsonProperty("accomodation_quality_location")
    private double accommodationQualityLocation;

    @JsonProperty("accomodation_quality_price")
    private double accommodationQualityPrice;

    @JsonProperty("accomodation_quality_quiet")
    private double accommodationQualityQuiet;

    @JsonProperty("accomodation_quality_breakfast")
    private double accommodationQualityBreakFast;

    @JsonProperty("accomodation_quality_cleanliness")
    private double accommodationQualityCleanLiness;

    @JsonProperty("travel_type_work")
    private double travelTypeWork;

    @JsonProperty("travel_type_honeymoon")
    private double travelTypeHoneyMoon;

    @JsonProperty("travel_type_citybreak")
    private double travelTypeCityBreak;

    @JsonProperty("travel_type_holiday")
    private double travelTypeHoliday;

    @JsonProperty("travel_companion_solo")
    private double travelTypeCompanionSolo;

    @JsonProperty("travel_companion_kids")
    private double travelTypeCompanionKids;

    @JsonProperty("travel_companion_couple")
    private double travelTypeCompanionCouple;

    @JsonProperty("travel_companion_friends")
    private double travelTypeCompanionFriends;

    @JsonProperty("sentiment_score")
    private double sentimentScore;

    @JsonProperty
    private String review;
}

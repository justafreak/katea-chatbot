package ml.strikers.kateaserver.fulfilment.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Recommendation {

    public static final String KIND = "Recommendation";

    public static final String ID = "id";
    public static final String SESSION_ID = "sessionId";
    public static final String HOTEL_ID = "hotelId";
    public static final String LIKE = "like";
    public static final String ACCOMMODATION_QUALITY_WIFI = "accommodationQualityWifi";
    public static final String ACCOMMODATION_QUALITY_STAFF = "accommodationQualityStaff";
    public static final String ACCOMMODATION_QUALITY_LOCATION = "accommodationQualityLocation";
    public static final String ACCOMMODATION_QUALITY_PRICE = "accommodationQualityPrice";
    public static final String ACCOMMODATION_QUALITY_QUIET = "accommodationQualityQuiet";
    public static final String ACCOMMODATION_QUALITY_BREAKFAST = "accommodationQualityBreakFast";
    public static final String ACCOMMODATION_QUALITY_CLEANLINESS = "accommodationQualityCleanLiness";
    public static final String TRAVEL_TYPE_WORK = "travelTypeWork";
    public static final String TRAVEL_TYPE_HONEYMOON = "travelTypeWork";
    public static final String TRAVEL_TYPE_CITY_BREAK = "travelTypeCityBreak";
    public static final String TRAVEL_TYPE_HOLIDAY = "travelTypeHoliday";
    public static final String TRAVEL_TYPE_COMPANION_SOLO = "travelTypeCompanionSolo";
    public static final String TRAVEL_TYPE_COMPANION_KIDS = "travelTypeCompanionKids";
    public static final String TRAVEL_TYPE_COMPANION_COUPLE = "travelTypeCompanionCouple";
    public static final String TRAVEL_TYPE_COMPANION_FRIENDS = "travelTypeCompanionFriends";

    private UUID id;

    @JsonProperty("sessionId")
    private UUID sessionId;

    @JsonProperty("hotel_id")
    private UUID hotelId;

    @JsonProperty("like")
    private double like;

    @JsonProperty("accomodation_quality_wifi")
    private Double accommodationQualityWifi;

    @JsonProperty("accomodation_quality_staff")
    private Double accommodationQualityStaff;

    @JsonProperty("accomodation_quality_location")
    private Double accommodationQualityLocation;

    @JsonProperty("accomodation_quality_quiet")
    private Double accommodationQualityPrice;

    @JsonProperty("accomodation_quality_quiet")
    private Double accommodationQualityQuiet;

    @JsonProperty("accomodation_quality_breakfast")
    private Double accommodationQualityBreakFast;

    @JsonProperty("accomodation_quality_cleanliness")
    private Double accommodationQualityCleanLiness;

    @JsonProperty("travel_type_work")
    private Double travelTypeWork;

    @JsonProperty("travel_type_honeymoon")
    private Double travelTypeHoneyMoon;

    @JsonProperty("travel_type_citybreak")
    private Double travelTypeCityBreak;

    @JsonProperty("travel_type_holiday")
    private Double travelTypeHoliday;

    @JsonProperty("travel_companion_solo")
    private Double travelTypeCompanionSolo;

    @JsonProperty("travel_companion_kids")
    private Double travelTypeCompanionKids;

    @JsonProperty("travel_companion_couple")
    private Double travelTypeCompanionCouple;

    @JsonProperty("travel_companion_friends")
    private Double travelTypeCompanionFriends;


}

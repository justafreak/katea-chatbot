package ml.strikers.kateaserver.fulfilment.entity;

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

    @JsonProperty("accomodation_quality_quiet")
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


}

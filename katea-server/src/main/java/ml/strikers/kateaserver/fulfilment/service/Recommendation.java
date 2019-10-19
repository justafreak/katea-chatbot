package ml.strikers.kateaserver.fulfilment.service;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.UUID;

@Data
public class Recommendation {

    @JsonProperty("sessionId")
    private UUID sessionId;

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

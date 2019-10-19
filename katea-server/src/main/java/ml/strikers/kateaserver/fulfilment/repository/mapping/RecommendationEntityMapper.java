package ml.strikers.kateaserver.fulfilment.repository.mapping;

import com.google.cloud.datastore.*;
import ml.strikers.kateaserver.fulfilment.service.Recommendation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.google.cloud.datastore.FullEntity.newBuilder;

@Component
public class RecommendationEntityMapper {


    public FullEntity<IncompleteKey> buildRecommendationEntity(KeyFactory recommendationKeyFactory,
                                                               Recommendation recommendation) {
        return newBuilder(recommendationKeyFactory.newKey())
                .set(Recommendation.ID, recommendation.getId().toString())
                .set(Recommendation.SESSION_ID, recommendation.getSessionId().toString())
                .set(Recommendation.HOTEL_ID, recommendation.getHotelId().toString())
                .set(Recommendation.LIKE, recommendation.getLike())
                .set(Recommendation.ACCOMMODATION_QUALITY_WIFI, recommendation.getAccommodationQualityWifi())
                .set(Recommendation.ACCOMMODATION_QUALITY_STAFF, recommendation.getAccommodationQualityStaff())
                .set(Recommendation.ACCOMMODATION_QUALITY_LOCATION, recommendation.getAccommodationQualityLocation())
                .set(Recommendation.ACCOMMODATION_QUALITY_PRICE, recommendation.getAccommodationQualityPrice())
                .set(Recommendation.ACCOMMODATION_QUALITY_QUIET, recommendation.getAccommodationQualityQuiet())
                .set(Recommendation.ACCOMMODATION_QUALITY_BREAKFAST, recommendation.getAccommodationQualityBreakFast())
                .set(Recommendation.ACCOMMODATION_QUALITY_CLEANLINESS, recommendation.getAccommodationQualityCleanLiness())
                .set(Recommendation.TRAVEL_TYPE_WORK, recommendation.getTravelTypeWork())
                .set(Recommendation.TRAVEL_TYPE_HONEYMOON, recommendation.getTravelTypeHoneyMoon())
                .set(Recommendation.TRAVEL_TYPE_CITY_BREAK, recommendation.getTravelTypeCityBreak())
                .set(Recommendation.TRAVEL_TYPE_HOLIDAY, recommendation.getTravelTypeHoliday())
                .set(Recommendation.TRAVEL_TYPE_COMPANION_SOLO, recommendation.getTravelTypeCompanionSolo())
                .set(Recommendation.TRAVEL_TYPE_COMPANION_KIDS, recommendation.getTravelTypeCompanionKids())
                .set(Recommendation.TRAVEL_TYPE_COMPANION_COUPLE, recommendation.getTravelTypeCompanionCouple())
                .set(Recommendation.TRAVEL_TYPE_COMPANION_FRIENDS, recommendation.getTravelTypeCompanionFriends())
                .build();
    }

    public List<Recommendation> entityToRecommendation(QueryResults<Entity> queryResults) {
        final var resultRecommendation = new ArrayList<Recommendation>();
        queryResults.forEachRemaining(entity -> resultRecommendation.add(entityToRecommendation(entity)));
        return resultRecommendation;
    }

    public Recommendation entityToRecommendation(Entity entity) {
        return Recommendation.builder()
                .id(UUID.fromString(entity.getString(Recommendation.ID)))
                .hotelId(UUID.fromString(entity.getString(Recommendation.HOTEL_ID)))
                .like(entity.getDouble(Recommendation.LIKE))
                .accommodationQualityWifi(entity.getDouble((Recommendation.ACCOMMODATION_QUALITY_WIFI)))
                .accommodationQualityStaff(entity.getDouble(Recommendation.ACCOMMODATION_QUALITY_STAFF))
                .accommodationQualityLocation(entity.getDouble(Recommendation.ACCOMMODATION_QUALITY_LOCATION))
                .accommodationQualityPrice(entity.getDouble(Recommendation.ACCOMMODATION_QUALITY_PRICE))
                .accommodationQualityQuiet(entity.getDouble(Recommendation.ACCOMMODATION_QUALITY_QUIET))
                .accommodationQualityBreakFast(entity.getDouble(Recommendation.ACCOMMODATION_QUALITY_BREAKFAST))
                .accommodationQualityCleanLiness(entity.getDouble(Recommendation.ACCOMMODATION_QUALITY_CLEANLINESS))
                .travelTypeWork(entity.getDouble(Recommendation.TRAVEL_TYPE_WORK))
                .travelTypeHoneyMoon(entity.getDouble(Recommendation.TRAVEL_TYPE_HONEYMOON))
                .travelTypeCityBreak(entity.getDouble(Recommendation.TRAVEL_TYPE_CITY_BREAK))
                .travelTypeHoliday(entity.getDouble(Recommendation.TRAVEL_TYPE_HOLIDAY))
                .travelTypeCompanionSolo(entity.getDouble(Recommendation.TRAVEL_TYPE_COMPANION_SOLO))
                .travelTypeCompanionKids(entity.getDouble(Recommendation.TRAVEL_TYPE_COMPANION_KIDS))
                .travelTypeCompanionCouple(entity.getDouble(Recommendation.TRAVEL_TYPE_COMPANION_COUPLE))
                .travelTypeCompanionFriends(entity.getDouble(Recommendation.TRAVEL_TYPE_COMPANION_FRIENDS))
                .build();
    }
}

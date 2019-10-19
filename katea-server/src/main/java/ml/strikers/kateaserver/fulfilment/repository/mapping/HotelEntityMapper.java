package ml.strikers.kateaserver.fulfilment.repository.mapping;

import com.google.cloud.datastore.*;
import ml.strikers.kateaserver.fulfilment.entity.Hotel;
import ml.strikers.kateaserver.review.entity.HotelReviewsAnalysisResponse;
import ml.strikers.kateaserver.util.SerializationUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.google.cloud.datastore.FullEntity.newBuilder;

@Component
public class HotelEntityMapper {

    private final PriceEntityMapper priceEntityMapper;

    public HotelEntityMapper(PriceEntityMapper priceEntityMapper) {
        this.priceEntityMapper = priceEntityMapper;
    }


    public FullEntity<IncompleteKey> buildHotelEntity(KeyFactory hotelKeyFactory,
                                                      KeyFactory priceKeyFactory,
                                                      Hotel hotel) {
        return newBuilder(hotelKeyFactory.newKey())
                .set(Hotel.ID, hotel.getId().toString())
                .set(Hotel.NAME, hotel.getName())
                .set(Hotel.IMAGE_URL, hotel.getImageUrl())
                .set(Hotel.URL, hotel.getUrl())
                .set(Hotel.RATING, hotel.getRating())
                .set(Hotel.CITY, hotel.getCity())
                .set(Hotel.PRICE, priceEntityMapper.buildPriceEntity(priceKeyFactory, hotel))
                .set(Hotel.VENUE_TYPE, hotel.getVenueType())
                .set(Hotel.ZONE, hotel.getZone())
                .set(Hotel.REVIEW_COUNT, hotel.getReviewCount())
                .set(Hotel.FACILITIES, SerializationUtil.write(hotel.getFacilities()))
                .set(Hotel.LAT_LONG, hotel.getLatLong())
                .build();
    }

    public Entity updatedHotelEntity(Key key,
                                     KeyFactory priceKeyFactory,
                                     Hotel hotel,
                                     HotelReviewsAnalysisResponse analysisResponse) {
        return Entity.newBuilder(key)
                .set(Hotel.ID, hotel.getId().toString())
                .set(Hotel.NAME, hotel.getName())
                .set(Hotel.IMAGE_URL, hotel.getImageUrl())
                .set(Hotel.URL, hotel.getUrl())
                .set(Hotel.RATING, hotel.getRating())
                .set(Hotel.CITY, hotel.getCity())
                .set(Hotel.PRICE, priceEntityMapper.buildPriceEntity(priceKeyFactory, hotel))
                .set(Hotel.VENUE_TYPE, hotel.getVenueType())
                .set(Hotel.ZONE, hotel.getZone())
                .set(Hotel.REVIEW_COUNT, hotel.getReviewCount())
                .set(Hotel.FACILITIES, hotel.getFacilities().stream().map(StringValue::new).collect(Collectors.toList()))
                .set(Hotel.LAT_LONG, hotel.getLatLong())
                .set(Hotel.SENTIMENT_SCORE, analysisResponse.getSentiment().getScore())
                .set(Hotel.SENTIMENT_VALUE, analysisResponse.getSentiment().getValue().toString())
                .set(Hotel.REVIEW_RECOMMENDED_FACILITIES, hotel.getFacilities().stream().map(StringValue::new).collect(Collectors.toList()))
                .build();
    }

    public List<Hotel> entityToHotels(QueryResults<Entity> queryResults) {
        final var resultHotels = new ArrayList<Hotel>();
        queryResults.forEachRemaining(entity -> resultHotels.add(entityToHotel(entity)));
        return resultHotels;
    }

    public Hotel entityToHotel(Entity entity) {
        return Hotel.builder()
                .id(UUID.fromString(entity.getString(Hotel.ID)))
                .name(entity.getString(Hotel.NAME))
                .imageUrl(entity.getString(Hotel.IMAGE_URL))
                .url(entity.getString(Hotel.URL))
                .rating(entity.getDouble(Hotel.RATING))
                .city(entity.getString(Hotel.CITY))
                .price(priceEntityMapper.getPrice(entity.getEntity(Hotel.PRICE)))
                .venueType(entity.getString(Hotel.VENUE_TYPE))
                .zone(entity.getString(Hotel.ZONE))
                .reviewCount(entity.getLong(Hotel.REVIEW_COUNT))
                .facilities(entity.getList(Hotel.FACILITIES).stream().map(Value::get).map(Object::toString).collect(Collectors.toList()))
                .latLong(entity.getString(Hotel.LAT_LONG))
                .recommendScore(1)
                .build();
    }

}

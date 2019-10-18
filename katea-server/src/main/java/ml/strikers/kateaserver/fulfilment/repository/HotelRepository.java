package ml.strikers.kateaserver.fulfilment.repository;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.IncompleteKey;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import ml.strikers.kateaserver.fulfilment.entity.Currency;
import ml.strikers.kateaserver.fulfilment.entity.Hotel;
import ml.strikers.kateaserver.fulfilment.entity.Price;
import ml.strikers.kateaserver.util.SerializationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.google.cloud.datastore.FullEntity.newBuilder;

/**
 * Docs:
 * 1. https://github.com/googleapis/google-cloud-java#authentication
 * 2. https://github.com/googleapis/google-cloud-java/tree/master/google-cloud-clients/google-cloud-datastore
 */
@Repository
public class HotelRepository extends AbstractRepository<Hotel, UUID> {

    public HotelRepository(Datastore datastore) {
        super(datastore);
    }

    private final KeyFactory hotelKeyFactory = datastore.newKeyFactory().setKind(Hotel.KIND);
    private final KeyFactory priceKeyFactory = datastore.newKeyFactory().setKind(Price.KIND);

    @Override
    public Hotel save(Hotel hotel) {
        datastore.add(buildHotelEntity(hotel));
        return hotel;
    }

    private FullEntity<IncompleteKey> buildPriceEntity(Hotel hotel) {
        return newBuilder(priceKeyFactory.newKey())
                .set(Price.CURRENCY, hotel.getPrice().getCurrency().name())
                .set(Price.VALUE, hotel.getPrice().getValue())
                .build();
    }

    private FullEntity<IncompleteKey> buildHotelEntity(Hotel hotel) {
        return newBuilder(hotelKeyFactory.newKey())
                .set(Hotel.ID, hotel.getId().toString())
                .set(Hotel.NAME, hotel.getName())
                .set(Hotel.IMAGE_URL, hotel.getImageUrl())
                .set(Hotel.URL, hotel.getUrl())
                .set(Hotel.RATING, hotel.getRating())
                .set(Hotel.CITY, hotel.getCity())
                .set(Hotel.PRICE, buildPriceEntity(hotel))
                .set(Hotel.VENUE_TYPE, hotel.getVenueType())
                .set(Hotel.ZONE, hotel.getZone())
                .set(Hotel.REVIEW_COUNT, hotel.getReviewCount())
                .set(Hotel.FACILITIES, SerializationUtil.write(hotel.getFacilities()))
                .set(Hotel.LAT_LONG, hotel.getLatLong())
                .build();
    }

    public List<Hotel> getHotelsByCity(String city) {
        return entityToHotels(datastore.run(Query.newGqlQueryBuilder(Query.ResultType.ENTITY,
                "SELECT * FROM " + Hotel.KIND + " WHERE " + Hotel.CITY + " = @city " + "LIMIT @limit")
                .setBinding("city", city)
                .setBinding("limit", 3)
                .build()));
    }

    private List<Hotel> entityToHotels(QueryResults<Entity> queryResults) {
        final var resultHotels = new ArrayList<Hotel>();
        queryResults.forEachRemaining(entity -> resultHotels.add(entityToHotel(entity)));
        return resultHotels;
    }

    private Hotel entityToHotel(Entity entity) {
        return Hotel.builder()
                .id(UUID.fromString(entity.getString(Hotel.ID)))
                .name(entity.getString(Hotel.NAME))
                .imageUrl(entity.getString(Hotel.IMAGE_URL))
                .url(entity.getString(Hotel.URL))
                .rating(entity.getDouble(Hotel.RATING))
                .city(entity.getString(Hotel.CITY))
                .price(getPrice(entity.getEntity(Hotel.PRICE)))
                .venueType(entity.getString(Hotel.VENUE_TYPE))
                .zone(entity.getString(Hotel.ZONE))
                .reviewCount(entity.getLong(Hotel.REVIEW_COUNT))
                .facilities(Arrays.asList(SerializationUtil.read(entity.getString(Hotel.FACILITIES), String[].class)))
                .latLong(entity.getString(Hotel.LAT_LONG))
                .recommendScore(1)
                .build();
    }

    private Price getPrice(FullEntity entity) {
        return Price.builder()
                .value(entity.getDouble(Price.VALUE))
                .currency(Currency.valueOf(entity.getString(Price.CURRENCY)))
                .build();
    }

}

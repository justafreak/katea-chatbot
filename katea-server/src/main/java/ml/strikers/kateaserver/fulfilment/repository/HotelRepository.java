package ml.strikers.kateaserver.fulfilment.repository;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.EntityQuery;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery;
import ml.strikers.kateaserver.fulfilment.entity.Currency;
import ml.strikers.kateaserver.fulfilment.entity.Hotel;
import ml.strikers.kateaserver.fulfilment.entity.Price;
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
        super(datastore, Hotel.KIND);
    }

    @Override
    public Hotel save(Hotel hotel) {
        datastore.add(
                newBuilder(keyFactory.newKey())
                        .set(Hotel.ID, hotel.getId().toString())
                        .set(Hotel.NAME, hotel.getName())
                        .set(Hotel.IMAGE_URL, hotel.getImageUrl())
                        .set(Hotel.URL, hotel.getUrl())
                        .set(Hotel.RATING, hotel.getRating())
                        .set(Hotel.CITY, hotel.getCity())
                        .set(Hotel.PRICE_VALUE, hotel.getPrice().getValue())
                        .set(Hotel.PRICE_CURRENCY, hotel.getPrice().getCurrency().toString())
                        .set(Hotel.VENUE_TYPE, hotel.getVenueType())
                        .set(Hotel.ZONE, hotel.getZone())
                        .set(Hotel.REVIEW_COUNT, hotel.getReviewCount())
                        .set(Hotel.FACILITIES, hotel.getFacilitiesAsString())
                        .set(Hotel.LAT_LONG, hotel.getLatLong())
                        .build()
        );
        return hotel;
    }

    @Override
    public List<Hotel> getEntityByProperty(String key, Object value) {
        Query<Entity> query = Query.newGqlQueryBuilder(Query.ResultType.ENTITY,
                "SELECT * FROM " + Hotel.KIND + " WHERE " + key + " = @key")
                .setBinding("key", (String) value)
                .build();
        return entityToHotels(datastore.run(query));
    }

    public List<Hotel> getHotelsByCity(String city) {
        Query<Entity> query = Query.newGqlQueryBuilder(Query.ResultType.ENTITY,
                "SELECT * FROM " + Hotel.KIND + " WHERE " + Hotel.CITY + " = @city " + "LIMIT @limit")
                .setBinding("city", city)
                .setBinding("limit", 3)
                .build();

        EntityQuery build = Query.newEntityQueryBuilder()
                .setKind("Test").setLimit(300)
                .setOrderBy(StructuredQuery.OrderBy.desc(Hotel.RATING))
                .setFilter(StructuredQuery.PropertyFilter.eq(Hotel.CITY, city))
                .build();

//        final var query = Query.newEntityQueryBuilder()
//                .setKind(Hotel.KIND)
//                .setLimit(300)
//                .setOrderBy(StructuredQuery.OrderBy.desc(Hotel.RATING))
////                .setFilter(StructuredQuery.PropertyFilter.eq(Hotel.ID, city))
//                .build();


        return entityToHotels(datastore.run(query));
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
                .price(getPrice(entity))
                .venueType(entity.getString(Hotel.VENUE_TYPE))
                .zone(entity.getString(Hotel.ZONE))
                .reviewCount(entity.getLong(Hotel.REVIEW_COUNT))
                .facilities(Arrays.asList(entity.getString(Hotel.FACILITIES).split(","))) //continuarea mizeriei de mai sus
                .latLong(entity.getString(Hotel.LAT_LONG))
                .recommendScore(1)
                .build();
    }

    private Price getPrice(Entity entity) {
        return Price.builder()
                .value(entity.getDouble(Hotel.PRICE_VALUE))
                .currency(Currency.valueOf(entity.getString(Hotel.PRICE_CURRENCY)))
                .build();
    }

}

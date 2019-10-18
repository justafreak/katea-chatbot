package ml.strikers.kateaserver.fulfilment.repository;

import com.google.cloud.datastore.*;
import ml.strikers.kateaserver.fulfilment.entity.Currency;
import ml.strikers.kateaserver.fulfilment.entity.Hotel;
import ml.strikers.kateaserver.fulfilment.entity.Price;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
        final var hotelEntity = Entity.newBuilder(keyFactory.newKey())
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
                .set(Hotel.FACILITIES, hotel.getFacilities().toString().replace("[", "").replace("]", ""))//mizerie
                .set(Hotel.LAT_LONG, hotel.getLatLong())
                .set(Hotel.LAT_LONG, hotel.getLatLong())
                .build();
        datastore.add(hotelEntity);
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
                "SELECT * FROM " + Hotel.KIND + " WHERE " + Hotel.CITY + " = @city")
                .setBinding("city", city)
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
        while (queryResults.hasNext()) {
            resultHotels.add(entityToHotel(queryResults.next()));
        }
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
                .facilities(Arrays.asList(entity.getString(Hotel.FACILITIES).split(",")))//continuarea mizeriei de mai sus
                .latLong(entity.getString(Hotel.LAT_LONG))
                .recommendScore(1)
                .build();
    }

    private Price getPrice(Entity entity) {
        Price price = new Price();
        price.setValue(entity.getDouble(Hotel.PRICE_VALUE));
        price.setCurrency(Currency.valueOf(entity.getString(Hotel.PRICE_CURRENCY)));
        return price;
    }

    private StructuredQuery.Filter createFilter(String city) {
//        return StructuredQuery.PropertyFilter.eq(Hotel.CITY, city);

        return StructuredQuery.CompositeFilter.and(StructuredQuery.PropertyFilter.eq(Hotel.CITY, city));
    }

    public static List<Hotel> getByCity(String city) {
        Price price = new Price();
        price.setCurrency(Currency.RON);
        price.setValue(2.22);
        Price price2 = new Price();
        price2.setCurrency(Currency.RON);
        price2.setValue(999.22);
        Price price3 = new Price();
        price3.setCurrency(Currency.RON);
        price3.setValue(809.22);
        return List.of(
                Hotel.builder()
                        .name("The Colonnade")
                        .price(price)
                        .city(city)
                        .rating(6.60)
                        .id(UUID.fromString("d3d46211-fa0c-44d2-939b-2252facb1ec0"))
                        .imageUrl("https://r-cf.bstatic.com/xdata/images/hotel/square200/122358182.jpg?k=04522139bfae775f531554f2be8a966e14f11880e12c76f58aa7ec31269eb2d2&o=")
                        .url("https://www.booking.com/hotel/gb/the-colonnade-london.en-gb.html")
                        .build(),
                Hotel.builder()
                        .name("Britannia International Hotel Canary Wharf")
                        .price(price2)
                        .city(city)
                        .rating(9.60)
                        .id(UUID.fromString("7a4ce2b7-7c43-48d8-a7d0-e254e555b479"))
                        .imageUrl("https://q-cf.bstatic.com/xdata/images/hotel/square200/50756453.jpg?k=18558635c66b534dabc514ace7a15f69c7c363dc79acb6d3c206bc7e4004f20b&o=")
                        .url("https://www.booking.com/hotel/gb/britannia-londoninternational.en-gb.html")
                        .build(),
                Hotel.builder()
                        .name("Lovely 1 Bed near British Museum (5)")
                        .price(price3)
                        .city(city)
                        .rating(10.0)
                        .id(UUID.fromString("67a41446-598c-427a-9375-479936766a35"))
                        .imageUrl("https://q-cf.bstatic.com/xdata/images/hotel/square200/219366877.jpg?k=79b7db28056fd01d72a4041203710b8f2a3aea071cdc6a1c66d894b49bd2ffd2&o=")
                        .url("https://www.booking.com/hotel/gb/lovely-1-bed-near-british-museum-5.en-gb.html")
                        .build()
        );
    }

}

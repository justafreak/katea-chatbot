package ml.strikers.kateaserver.fulfilment.repository;

import com.google.cloud.datastore.*;
import ml.strikers.kateaserver.fulfilment.entity.Currency;
import ml.strikers.kateaserver.fulfilment.entity.Hotel;
import ml.strikers.kateaserver.fulfilment.entity.Price;

import java.util.List;
import java.util.UUID;

/**
 * Docs:
 * 1. https://github.com/googleapis/google-cloud-java#authentication
 * 2. https://github.com/googleapis/google-cloud-java/tree/master/google-cloud-clients/google-cloud-datastore
 */
public class HotelDataStoreAdapter implements Repository<Hotel, UUID> {

    private Datastore datastore;
    private KeyFactory keyFactory;

    public HotelDataStoreAdapter() {
        datastore = DatastoreOptions.getDefaultInstance().getService();
        keyFactory = datastore.newKeyFactory().setKind("Hotels");
    }


    @Override
    public Hotel save(Hotel entity) {
        IncompleteKey key = keyFactory.newKey();
        FullEntity<IncompleteKey> hotelEntity = Entity.newBuilder(key)
                .set(Hotel.ID, entity.getId().toString())
                .set(Hotel.NAME, entity.getName())
                .set(Hotel.IMAGE_URL, entity.getImageUrl())
                .set(Hotel.RATING, entity.getRating())
                .set(Hotel.CITY, entity.getCity())
                .set(Hotel.PRICE_VALUE, entity.getPrice().getValue())
                .set(Hotel.PRICE_CURRENCY, entity.getPrice().getCurrency().toString())
                .set(Hotel.VENUE_TYPE, entity.getVenueType())
                .set(Hotel.ZONE, entity.getZone())
                .set(Hotel.REVIEW_COUNT, entity.getReviewCount())
                .set(Hotel.FACILITIES, entity.getFacilities().toString())
                .set(Hotel.LAT_LONG, entity.getLatLong())
                .build();
        datastore.add(hotelEntity);
        return entity;
    }

    @Override
    public Hotel getById(UUID uuid) {
        return null;
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

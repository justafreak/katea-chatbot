package ml.strikers.kateaserver.fulfilment.repository;

import com.google.cloud.datastore.*;
import ml.strikers.kateaserver.fulfilment.entity.Hotel;
import ml.strikers.kateaserver.fulfilment.entity.Price;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Docs:
 * 1. https://github.com/googleapis/google-cloud-java#authentication
 * 2. https://github.com/googleapis/google-cloud-java/tree/master/google-cloud-clients/google-cloud-datastore
 */
@org.springframework.stereotype.Repository
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
        FullEntity<IncompleteKey> incBookEntity = Entity.newBuilder(key)
                .set("name", entity.getName())
                .build();
        Entity hotel = datastore.add(incBookEntity);
        return entity;
    }

    @Override
    public Hotel getById(UUID uuid) {
        return null;
    }

    @Override
    public List<Hotel> getByCity(String city) {
        return List.of(Hotel.builder()
                .name("The Colonnade")
                .price(new Price())
                .city(city)
                .id(UUID.fromString("d3d46211-fa0c-44d2-939b-2252facb1ec0"))
                .imageUrl("https://r-cf.bstatic.com/xdata/images/hotel/square200/122358182.jpg?k=04522139bfae775f531554f2be8a966e14f11880e12c76f58aa7ec31269eb2d2&o=")
                .url("https://www.booking.com/hotel/gb/the-colonnade-london.en-gb.html")
                .build());
    }

}

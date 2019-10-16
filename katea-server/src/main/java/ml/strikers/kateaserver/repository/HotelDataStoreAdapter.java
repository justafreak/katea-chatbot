package ml.strikers.kateaserver.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.KeyFactory;
import ml.strikers.kateaserver.entity.Hotel;
import ml.strikers.kateaserver.entity.Price;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.UUID;

/**
 * Docs:
 * 1. https://github.com/googleapis/google-cloud-java#authentication
 * 2. https://github.com/googleapis/google-cloud-java/tree/master/google-cloud-clients/google-cloud-datastore
 */
@Repository
public class HotelDataStoreAdapter implements AbstractRepository<Hotel, UUID> {

    private final Datastore datastore;

    private final ObjectMapper objectMapper;

    private KeyFactory keyFactory;
    private static final String KIND = "Hotels";

    public HotelDataStoreAdapter(Datastore datastore, ObjectMapper objectMapper) {
        this.datastore = datastore;
        this.objectMapper = objectMapper;
        this.keyFactory = datastore.newKeyFactory().setKind(KIND);
    }


    @Override
    public Hotel save(Hotel entity) {
        final Entity hotel;
        try {
            hotel = Entity.newBuilder(keyFactory.newKey(entity.getId().toString()))
                    .set("name", entity.getName())
                    .set("imageUrl", entity.getImageUrl())
                    .set("url", entity.getUrl())
                    .set("rating", entity.getRating())
                    .set("price", objectMapper.writeValueAsString(entity.getPrice()))
                    .build();
            datastore.add(hotel);
            return entity;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Hotel getById(UUID uuid) {
        return entityToHotel(datastore.get(keyFactory.newKey(uuid.toString())));
    }

    public Hotel entityToHotel(Entity entity) {
        try {
            return Hotel.builder()                                    // Convert to Book form
                    .id(UUID.fromString(entity.getString(Hotel.ID)))
                    .name(entity.getString(Hotel.NAME))
                    .imageUrl(entity.getString(Hotel.IMAGE_URL))
                    .url(entity.getString(Hotel.URL))
                    .rating(Double.valueOf(entity.getString(Hotel.RATING)))
                    .price(objectMapper.readValue(entity.getString(Hotel.PRICE), Price.class))
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

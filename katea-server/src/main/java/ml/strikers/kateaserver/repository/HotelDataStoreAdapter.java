package ml.strikers.kateaserver.repository;

import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.datastore.*;
import ml.strikers.kateaserver.entity.Hotel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * Docs:
 * 1. https://github.com/googleapis/google-cloud-java#authentication
 * 2. https://github.com/googleapis/google-cloud-java/tree/master/google-cloud-clients/google-cloud-datastore
 */
public class HotelDataStoreAdapter implements Repository<Hotel, UUID> {

    private Datastore datastore;
    private KeyFactory keyFactory;

    public HotelDataStoreAdapter() throws IOException {
        datastore = DatastoreOptions.getDefaultInstance().getService();
        keyFactory = datastore.newKeyFactory().setKind("Hotels");
    }


    @Override
    public Hotel save(Hotel entity) {
        IncompleteKey key = keyFactory.newKey();
        FullEntity<IncompleteKey> incBookEntity = Entity.newBuilder(key)
//                .set("name", entity.getName())
                .build();
        Entity hotel = datastore.add(incBookEntity);
        return entity;
    }

    @Override
    public Hotel getById(UUID uuid) {
        return null;
    }

}

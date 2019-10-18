package ml.strikers.kateaserver.fulfilment.repository;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Query;
import ml.strikers.kateaserver.fulfilment.entity.Hotel;
import ml.strikers.kateaserver.fulfilment.entity.Price;
import ml.strikers.kateaserver.fulfilment.repository.mapping.HotelEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Docs:
 * 1. https://github.com/googleapis/google-cloud-java#authentication
 * 2. https://github.com/googleapis/google-cloud-java/tree/master/google-cloud-clients/google-cloud-datastore
 */
@Repository
public class HotelRepository extends AbstractRepository<Hotel, UUID> {

    private final KeyFactory hotelKeyFactory = datastore.newKeyFactory().setKind(Hotel.KIND);
    private final KeyFactory priceKeyFactory = datastore.newKeyFactory().setKind(Price.KIND);

    private final HotelEntityMapper hotelEntityMapper;

    public HotelRepository(Datastore datastore, HotelEntityMapper hotelEntityMapper) {
        super(datastore);
        this.hotelEntityMapper = hotelEntityMapper;
    }

    @Override
    public Hotel save(Hotel hotel) {
        datastore.add(hotelEntityMapper.buildHotelEntity(hotelKeyFactory, priceKeyFactory, hotel));
        return hotel;
    }

    public List<Hotel> getHotelsByCity(String city) {
        return hotelEntityMapper.entityToHotels(datastore.run(Query.newGqlQueryBuilder(Query.ResultType.ENTITY,
                "SELECT * FROM " + Hotel.KIND + " WHERE " + Hotel.CITY + " = @city " + "LIMIT @limit")
                .setBinding("city", city)
                .setBinding("limit", 3)
                .build()));
    }

}

package ml.strikers.kateaserver.fulfilment.repository;

import com.google.cloud.datastore.*;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;
import ml.strikers.kateaserver.fulfilment.entity.Hotel;
import ml.strikers.kateaserver.fulfilment.entity.Price;
import ml.strikers.kateaserver.fulfilment.repository.mapping.HotelEntityMapper;
import ml.strikers.kateaserver.review.entity.HotelReviewsAnalysisResponse;
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

    public void update(HotelReviewsAnalysisResponse analysisResponse) {
        EntityQuery entityQuery = Query.newEntityQueryBuilder()
                .setKind(Hotel.KIND)
                .setFilter(PropertyFilter.eq(Hotel.ID, analysisResponse.getHotelId()))
                .build();
        QueryResults<Entity> queryResult = datastore.run(entityQuery);
        if (queryResult.hasNext()) {
            Entity entity = queryResult.next();
            Hotel hotel = hotelEntityMapper.entityToHotel(entity);
            Key key = hotelKeyFactory.newKey(entity.getKey().getId());
            Entity updatedEntity = hotelEntityMapper.updatedHotelEntity(key, priceKeyFactory, hotel, analysisResponse);
            datastore.update(updatedEntity);
        }
    }

    public List<Hotel> getHotelsByCity(String city) {
        return hotelEntityMapper.entityToHotels(datastore.run(Query.newGqlQueryBuilder(Query.ResultType.ENTITY,
                "SELECT * FROM " + Hotel.KIND + " WHERE " + Hotel.CITY + " = @city " + "LIMIT @limit")
                .setBinding("city", city)
                .setBinding("limit", 3)
                .build()));
    }

}

package ml.strikers.kateaserver.fulfilment.repository.mapping;

import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.IncompleteKey;
import com.google.cloud.datastore.KeyFactory;
import ml.strikers.kateaserver.fulfilment.entity.Currency;
import ml.strikers.kateaserver.fulfilment.entity.Hotel;
import ml.strikers.kateaserver.fulfilment.entity.Price;
import org.springframework.stereotype.Component;

import static com.google.cloud.datastore.FullEntity.newBuilder;


@Component
public class PriceEntityMapper {

    FullEntity<IncompleteKey> buildPriceEntity(KeyFactory priceKeyFactory, Hotel hotel) {
        return newBuilder(priceKeyFactory.newKey())
                .set(Price.CURRENCY, hotel.getPrice().getCurrency().name())
                .set(Price.VALUE, hotel.getPrice().getValue())
                .build();
    }

    Price getPrice(FullEntity entity) {
        return Price.builder()
                .value(entity.getDouble(Price.VALUE))
                .currency(Currency.valueOf(entity.getString(Price.CURRENCY)))
                .build();
    }
}

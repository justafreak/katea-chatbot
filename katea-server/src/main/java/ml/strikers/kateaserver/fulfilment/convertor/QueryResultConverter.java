package ml.strikers.kateaserver.fulfilment.convertor;

import com.google.cloud.dialogflow.v2.Intent;
import com.google.cloud.dialogflow.v2.QueryResult;
import lombok.experimental.UtilityClass;
import ml.strikers.kateaserver.fulfilment.entity.Fulfilment;
import ml.strikers.kateaserver.fulfilment.entity.Hotel;
import ml.strikers.kateaserver.util.SerializationUtil;
import ml.strikers.kateaserver.web.convertor.ParameterConverter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@UtilityClass
public class QueryResultConverter {

    public static Fulfilment covertResultToFulfilment(QueryResult queryResult) {
        Fulfilment fulfilment = new Fulfilment();
        fulfilment.setFulfilmentSimpleResponse(queryResult.getFulfillmentText());
        fulfilment.setHotelList(convert(queryResult.getFulfillmentMessages(0)));
        fulfilment.setParameters(ParameterConverter.convert(queryResult.getParameters().getFieldsMap()));
        return fulfilment;
    }

    private static List<Hotel> convert(Intent.Message message) {
        return message.getCarouselSelect()
                .getItemsList()
                .stream()
                .map(QueryResultConverter::getHotel)
                .collect(Collectors.toList());
    }

    private Hotel getHotel(Intent.Message.CarouselSelect.Item item) {
        try {
            return SerializationUtil.OBJECT_MAPPER.readValue(item.getDescription(), Hotel.class);
        } catch (IOException e) {
            return Hotel.builder()
                    .imageUrl(item.getImage().getImageUri())
                    .name(item.getTitle())
                    .build();
        }
    }

}

package ml.strikers.kateaserver.fulfilment.convertor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.dialogflow.v2.Intent;
import com.google.cloud.dialogflow.v2.QueryResult;
import ml.strikers.kateaserver.fulfilment.entity.Fulfilment;
import ml.strikers.kateaserver.fulfilment.entity.Hotel;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class QueryResultConverter {

    public static Fulfilment covertResultToFulfilment(QueryResult queryResult) {
        Fulfilment fulfilment = new Fulfilment();
        fulfilment.setFulfilmentSimpleResponse(queryResult.getFulfillmentText());
        fulfilment.setHotelList(convert(queryResult.getFulfillmentMessages(0)));
        return fulfilment;
    }

    private static List<Hotel> convert(Intent.Message message) {
        return message.getCarouselSelect()
                .getItemsList()
                .stream()
                .map(item -> {

                    try {
                        return new ObjectMapper().readValue(item.getDescription(), Hotel.class);
                    } catch (IOException e) {
                        return Hotel.builder()
                                .imageUrl(item.getImage().getImageUri())
                                .name(item.getTitle())
                                .build();
                    }

                }).collect(Collectors.toList());
    }
}

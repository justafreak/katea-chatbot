package ml.strikers.kateaserver.web.convertor;

import lombok.experimental.UtilityClass;
import ml.strikers.kateaserver.fulfilment.entity.Fulfilment;
import ml.strikers.kateaserver.web.rest.v1.dto.*;

import java.util.List;

import static java.util.Objects.isNull;

@UtilityClass
public class FulfilmentConvertor {

    public static Response fulfilmentToResponseConvertor(Fulfilment fulfilment) {
        final var response = new Response();
        response.setSessionId(fulfilment.getUUID());
        final var hotelList = fulfilment.getHotelList();
        response.setParameters(fulfilment.getParameters());

        if (isNull(hotelList) || hotelList.isEmpty()) {
            final var simpleReply = SimpleReply.builder()
                    .reply(fulfilment.getFulfilmentSimpleResponse())
                    .type(ResponseType.TEXT)
                    .build();
            response.setMessage(List.of(simpleReply));
            return response;
        }

        final var carouselList = CarouselList.builder()
                .reply(fulfilment.getHotelList())
                .type(ResponseType.CAROUSEL)
                .build();
        response.setMessage(List.of(
                SimpleReply.builder().reply(Constants.RECOMMENDATIONS).build(),
                carouselList)
        );
        return response;
    }

}

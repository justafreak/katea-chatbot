package ml.strikers.kateaserver.web.convertor;

import ml.strikers.kateaserver.fulfilment.entity.Fulfilment;
import ml.strikers.kateaserver.web.rest.v1.DTO.CarouselList;
import ml.strikers.kateaserver.web.rest.v1.DTO.Response;
import ml.strikers.kateaserver.web.rest.v1.DTO.ResponseType;
import ml.strikers.kateaserver.web.rest.v1.DTO.SimpleReply;

public class FulfilmentConvertor {

    public static Response fulfilmentToResponseConvertor(Fulfilment fulfilment) {
        Response response = new Response();
        response.setSessionId(fulfilment.getUUID());

        if (fulfilment.getHotelList() == null || fulfilment.getHotelList().isEmpty()) {
            SimpleReply simpleReply = new SimpleReply();
            simpleReply.setReply(fulfilment.getFulfilmentSimpleResponse());
            simpleReply.setType(ResponseType.text);
            response.setMessage(simpleReply);
            return response;
        }
        CarouselList carouselList = new CarouselList();
        carouselList.setReply(fulfilment.getHotelList());
        carouselList.setType(ResponseType.carousel);
        response.setMessage(carouselList);
        return response;
    }
}

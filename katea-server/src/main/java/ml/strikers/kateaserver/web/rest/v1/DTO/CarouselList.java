package ml.strikers.kateaserver.web.rest.v1.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import ml.strikers.kateaserver.fulfilment.entity.Hotel;

import java.util.List;

@Data
@NoArgsConstructor
public class CarouselList implements Message {

    private ResponseType type = ResponseType.MSG_TYPE_CAROUSEL;
    private List<Hotel> reply;


}

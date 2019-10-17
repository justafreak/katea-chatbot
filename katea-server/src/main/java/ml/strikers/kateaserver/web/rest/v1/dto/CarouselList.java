package ml.strikers.kateaserver.web.rest.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.strikers.kateaserver.fulfilment.entity.Hotel;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarouselList implements IMessage {

    @Builder.Default
    private ResponseType type = ResponseType.CAROUSEL;
    private List<Hotel> reply;

}

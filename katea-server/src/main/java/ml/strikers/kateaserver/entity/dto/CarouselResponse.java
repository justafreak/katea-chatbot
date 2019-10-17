package ml.strikers.kateaserver.entity.dto;

import lombok.Data;
import ml.strikers.kateaserver.entity.Hotel;

import java.util.List;

@Data
public class CarouselResponse implements UiMessageResponse {
    private UiResponseType type = UiResponseType.carousel;
    private List<Hotel> reply;
}

package ml.strikers.kateaserver.web.rest.v1.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ICarouselItem {

    private String url;
    private String imgSrc;
    private String title;
    private String price;
}

package ml.strikers.kateaserver.entity;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Hotel {

    private UUID id;
    private String name;
    private String imageUrl;
    private String url;
    private Double rating;
    private String city;
    private Price price;

}

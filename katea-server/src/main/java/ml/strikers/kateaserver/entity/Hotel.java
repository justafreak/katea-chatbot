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


    public static final String ID = "id";
    public static final String NAME ="name";
    public static final String IMAGE_URL ="imageUrl";
    public static final String URL ="url";
    public static final String RATING ="rating";
    public static final String CITY ="city";
    public static final String PRICE ="price";

}

package ml.strikers.kateaserver.fulfilment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    public static final String KIND = "Reviews";

    public static final String ID = "id";
    public static final String URL = "url";
    public static final String REVIEWS = "reviews";

    private UUID id;
    private String url;
    private List<String> reviews;

}

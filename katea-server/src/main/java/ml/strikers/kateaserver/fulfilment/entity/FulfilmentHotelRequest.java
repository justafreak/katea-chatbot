package ml.strikers.kateaserver.fulfilment.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FulfilmentHotelRequest {

    private String city;
    private List<String> facilities;
    private String companions;
    private String tripType;
    private String accommodationType;

}

package ml.strikers.kateaserver.fulfilment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfilmentHotelRequest {

    private String city;
    private List<String> facilities;
    private String companions;
    private String tripType;
    private String accommodationType;

}

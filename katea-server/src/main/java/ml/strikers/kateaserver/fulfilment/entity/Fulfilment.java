package ml.strikers.kateaserver.fulfilment.entity;

import com.google.protobuf.Value;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Fulfilment {

    private java.util.UUID UUID;
    private String fulfilmentSimpleResponse;
    private List<String> fulfilmentResponses;
    private List<Hotel> hotelList;
    private Map<String, Object> parameters;
}

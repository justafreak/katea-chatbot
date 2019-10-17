package ml.strikers.kateaserver.entity.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FullfilmentHotelRequest {
    private String location;
    private List<String> qualities;
    private String companions;
    private String tripType;
    private String accommodationType;
}

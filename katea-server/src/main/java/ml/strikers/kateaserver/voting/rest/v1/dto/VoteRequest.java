package ml.strikers.kateaserver.voting.rest.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteRequest {

    private UUID sessionId;
    private UUID id;
    private Boolean vote;
    private Map<String, Object> parameters;

}

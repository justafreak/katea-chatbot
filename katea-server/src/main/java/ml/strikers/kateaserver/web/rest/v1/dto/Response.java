package ml.strikers.kateaserver.web.rest.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    private UUID sessionId;
    private List<IMessage> message;
    private Map<String, Object> parameters;
}


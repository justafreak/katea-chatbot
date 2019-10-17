package ml.strikers.kateaserver.web.rest.v1.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class Response {

    private UUID sessionId;
    private IMessage message;

}


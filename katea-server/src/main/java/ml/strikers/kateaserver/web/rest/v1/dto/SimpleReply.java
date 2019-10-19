package ml.strikers.kateaserver.web.rest.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleReply implements IMessage {

    @Builder.Default
    private ResponseType type = ResponseType.TEXT;
    private String reply;
}

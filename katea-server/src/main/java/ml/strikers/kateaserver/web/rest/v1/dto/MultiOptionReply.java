package ml.strikers.kateaserver.web.rest.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MultiOptionReply implements IMessage {

    @Builder.Default
    private ResponseType type = ResponseType.QUICK_REPLY;
    private List<String> reply;

}

package ml.strikers.kateaserver.web.rest.v1.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MultiOptionReply implements IMessage {

    private ResponseType type = ResponseType.QUICK_REPLY;
    private List<String> reply;

}

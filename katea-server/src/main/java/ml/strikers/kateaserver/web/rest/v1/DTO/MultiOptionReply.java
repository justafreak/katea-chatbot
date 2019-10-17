package ml.strikers.kateaserver.web.rest.v1.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MultiOptionReply implements IMessage {

    private ResponseType type = ResponseType.MSG_TYPE_QUICK_REPLY;
    private List<String> reply;
}

package ml.strikers.kateaserver.web.rest.v1.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SimpleReply implements Reply{

    private String reply;
}

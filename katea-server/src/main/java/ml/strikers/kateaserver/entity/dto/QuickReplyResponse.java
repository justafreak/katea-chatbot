package ml.strikers.kateaserver.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuickReplyResponse implements UiMessageResponse {
    private UiResponseType type = UiResponseType.quickreply;
    private List<String> reply;
}

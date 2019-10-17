package ml.strikers.kateaserver.entity.dto;

import lombok.Data;

@Data
public
class TextResponse implements UiMessageResponse {
    private UiResponseType type = UiResponseType.text;
    private String reply;
}

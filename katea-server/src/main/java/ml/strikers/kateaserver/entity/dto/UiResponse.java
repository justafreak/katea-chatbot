package ml.strikers.kateaserver.entity.dto;

import lombok.Data;

@Data
public class UiResponse {

    private String sessionId;
    private UiMessageResponse message;
}

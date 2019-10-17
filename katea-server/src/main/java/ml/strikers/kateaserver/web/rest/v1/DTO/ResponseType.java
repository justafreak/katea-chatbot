package ml.strikers.kateaserver.web.rest.v1.DTO;

import lombok.Data;


public enum ResponseType {


    MSG_TYPE_TEXT("MSG_TYPE_TEXT"), MSG_TYPE_QUICK_REPLY("MSG_TYPE_QUICK_REPLY"), MSG_TYPE_CAROUSEL("MSG_TYPE_CAROUSEL");

    private String responseType;

    ResponseType(String responseType) {
        this.responseType = responseType;
    }
}

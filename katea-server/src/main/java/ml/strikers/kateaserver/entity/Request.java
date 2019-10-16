package ml.strikers.kateaserver.entity;

import lombok.Data;

import java.util.UUID;

@Data
public class Request {

    private UUID sesionId;

    private String requestBody;

}

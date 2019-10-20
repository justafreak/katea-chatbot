package ml.strikers.kateaserver.web.service;


import ml.strikers.kateaserver.fulfilment.entity.Request;
import ml.strikers.kateaserver.fulfilment.service.DialogProvider;
import ml.strikers.kateaserver.web.convertor.FulfilmentConvertor;
import ml.strikers.kateaserver.web.rest.v1.dto.Response;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.util.Objects.isNull;

@Service
public class RequestService {

    private final DialogProvider dialogProvider;

    public RequestService(DialogProvider dialogProvider) {
        this.dialogProvider = dialogProvider;
    }

    public Response detectIntent(Request request) throws Exception {
        final var queryMessage = request.getMessage();
        final var sessionId = request.getSessionId();
        final var uuid = isNull(sessionId) ? UUID.randomUUID() : sessionId;
        final var fulfilment = dialogProvider.detectIntent(queryMessage, uuid);
        return FulfilmentConvertor.fulfilmentToResponseConvertor(fulfilment);
    }
}



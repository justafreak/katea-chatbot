package ml.strikers.kateaserver.web.service;


import ml.strikers.kateaserver.fulfilment.entity.Fulfilment;
import ml.strikers.kateaserver.fulfilment.entity.Request;
import ml.strikers.kateaserver.fulfilment.service.DialogProvider;
import ml.strikers.kateaserver.web.rest.v1.DTO.Response;
import ml.strikers.kateaserver.web.rest.v1.DTO.SimpleReply;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RequestService {

    private final DialogProvider dialogProvider;

    public RequestService(DialogProvider dialogProvider) {
        this.dialogProvider = dialogProvider;
    }

    public Response getResponse(Request request) throws Exception {
        Response response = new Response();
        SimpleReply message = new SimpleReply();
        String queryMessage = request.getMessage();
        UUID uuid = request.getSessionId() == null ? UUID.randomUUID() : request.getSessionId();
        Fulfilment fulfilment = dialogProvider.getFulfilment(queryMessage, uuid);
        message.setReply(fulfilment.getFulfilmentSimpleResponse());
        response.setMessage(message);
        response.setSessionId(fulfilment.getUUID());
        return response;
    }
}


package ml.strikers.kateaserver.voting.service;

import ml.strikers.kateaserver.fulfilment.service.MLService;
import ml.strikers.kateaserver.voting.rest.v1.dto.VoteRequest;
import ml.strikers.kateaserver.web.rest.v1.dto.CarouselList;
import ml.strikers.kateaserver.web.rest.v1.dto.Response;
import ml.strikers.kateaserver.web.rest.v1.dto.ResponseType;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    private final MLService mlService;

    public VoteService(MLService mlService) {
        this.mlService = mlService;
    }


    public Response getResponse(VoteRequest voteRequest) {
        Response response = new Response();
        CarouselList carouselList = new CarouselList();
        carouselList.setType(ResponseType.CAROUSEL);
        carouselList.setReply(mlService.processVoteRequest(voteRequest));
        response.setMessage(carouselList);
        response.setSessionId(voteRequest.getSessionId());
        return response;
    }
}

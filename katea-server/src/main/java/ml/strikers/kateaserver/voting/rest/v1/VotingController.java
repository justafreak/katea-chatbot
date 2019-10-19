package ml.strikers.kateaserver.voting.rest.v1;

import ml.strikers.kateaserver.fulfilment.entity.Hotel;
import ml.strikers.kateaserver.voting.rest.v1.dto.VoteRequest;
import ml.strikers.kateaserver.voting.service.VoteService;
import ml.strikers.kateaserver.web.rest.v1.dto.CarouselList;
import ml.strikers.kateaserver.web.rest.v1.dto.Response;
import ml.strikers.kateaserver.web.rest.v1.dto.ResponseType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vote")
@CrossOrigin(origins = "*", methods = RequestMethod.POST, allowedHeaders = "*", maxAge = 6000L)
public class VotingController {

    private final VoteService voteService;

    public VotingController(VoteService voteService) {
        this.voteService = voteService;
    }


    @PostMapping("")
    public Response query(@RequestBody VoteRequest voteRequest) {
        final List<Hotel> recommendations = voteService.getResponse(voteRequest);
        Response response = new Response();
        CarouselList carouselList = new CarouselList();
        carouselList.setType(ResponseType.CAROUSEL);
        carouselList.setReply(recommendations);
        response.setMessage(carouselList);
        response.setSessionId(voteRequest.getSessionId());
        return response;
    }
}

package ml.strikers.kateaserver.voting.rest.v1;

import ml.strikers.kateaserver.voting.rest.v1.dto.VoteRequest;
import ml.strikers.kateaserver.voting.service.VoteService;
import ml.strikers.kateaserver.web.rest.v1.dto.Response;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vote")
@CrossOrigin(origins = "*", methods = RequestMethod.POST, allowedHeaders = "*", maxAge = 6000L)
public class VotingController {

    private final VoteService voteService;

    public VotingController(VoteService voteService) {
        this.voteService = voteService;
    }


    @PostMapping("")
    public Response query(@RequestBody VoteRequest voteRequest) throws Exception {
        return voteService.getResponse(voteRequest);
    }
}

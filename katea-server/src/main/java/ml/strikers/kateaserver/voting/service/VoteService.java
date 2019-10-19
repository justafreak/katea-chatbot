package ml.strikers.kateaserver.voting.service;

import ml.strikers.kateaserver.fulfilment.convertor.RecommendationConverter;
import ml.strikers.kateaserver.fulfilment.entity.Hotel;
import ml.strikers.kateaserver.fulfilment.entity.Recommendation;
import ml.strikers.kateaserver.fulfilment.service.MLService;
import ml.strikers.kateaserver.voting.rest.v1.dto.VoteRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteService {

    private final MLService mlService;

    public VoteService(MLService mlService) {
        this.mlService = mlService;
    }


    public List<Hotel> getResponse(VoteRequest voteRequest) {
        final Recommendation recommendation = RecommendationConverter.voteRequestTRecommendation(voteRequest);
        return mlService.getSuggestions(recommendation);
    }
}

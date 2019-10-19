package ml.strikers.kateaserver.voting.service;

import ml.strikers.kateaserver.fulfilment.convertor.RecommendationConverter;
import ml.strikers.kateaserver.fulfilment.entity.Hotel;
import ml.strikers.kateaserver.fulfilment.entity.Recommendation;
import ml.strikers.kateaserver.fulfilment.repository.RecommendationRepository;
import ml.strikers.kateaserver.fulfilment.service.MLService;
import ml.strikers.kateaserver.voting.rest.v1.dto.VoteRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteService {

    private final MLService mlService;
    private final RecommendationRepository recommendationRepository;

    public VoteService(MLService mlService, RecommendationRepository recommendationRepository) {
        this.mlService = mlService;
        this.recommendationRepository = recommendationRepository;
    }


    public List<Hotel> getRecommendations(VoteRequest voteRequest) {
        final Recommendation recommendation = RecommendationConverter.voteRequestRecommendation(voteRequest);
        recommendationRepository.save(recommendation);
        final List<Hotel> suggestions = mlService.getSuggestions(recommendation);
        mlService.triggerTrain();
        return suggestions;
    }
}

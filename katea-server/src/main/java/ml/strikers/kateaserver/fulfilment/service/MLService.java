package ml.strikers.kateaserver.fulfilment.service;

import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2beta1WebhookResponse;
import ml.strikers.kateaserver.fulfilment.convertor.RecommendationConverter;
import ml.strikers.kateaserver.fulfilment.entity.Hotel;
import ml.strikers.kateaserver.fulfilment.entity.Recommendation;
import ml.strikers.kateaserver.util.SerializationUtil;
import ml.strikers.kateaserver.voting.rest.v1.dto.VoteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class MLService {


    @Value("${recommendation.client.url}")
    private String recommendationUrl;

    private RestTemplate restTemplate;


    @Autowired
    public MLService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

    }


    public List<Hotel> preprocessQualityFacilities(GoogleCloudDialogflowV2beta1WebhookResponse webHookResponse) {
        return sendRecommendation(RecommendationConverter.webHookToRecmmendation(webHookResponse));
    }

    public List<Hotel> processVoteRequest(VoteRequest voteRequest) {
        return sendRecommendation(RecommendationConverter.voteRequestTRecommendation(voteRequest));
    }

    private List<Hotel> sendRecommendation(Recommendation recommendation) {
        return Arrays.asList(restTemplate.postForObject(recommendationUrl, SerializationUtil.write(recommendation), Hotel[].class));
    }

}


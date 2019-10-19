package ml.strikers.kateaserver.fulfilment.service;

import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2beta1WebhookResponse;
import ml.strikers.kateaserver.fulfilment.entity.DialogFlowEntity;
import ml.strikers.kateaserver.fulfilment.entity.Hotel;
import ml.strikers.kateaserver.fulfilment.entity.Recommendation;
import ml.strikers.kateaserver.util.SerializationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

@Service
public class MLService {


    @Value("${recommendation.client.url}")
    private String recommendationUrl;

    private RestTemplate restTemplate;

    private final RecommendationMapper recommendationMapper;

    @Autowired
    public MLService(RestTemplate restTemplate, RecommendationMapper recommendationMapper) {
        this.restTemplate = restTemplate;
        this.recommendationMapper = recommendationMapper;
    }


    public List<Hotel> preprocessTheRecommendation(GoogleCloudDialogflowV2beta1WebhookResponse webHookResponse) {
        return preprocessQualityFacilities(webHookResponse);
    }

    public List<Hotel> preprocessQualityFacilities(GoogleCloudDialogflowV2beta1WebhookResponse webHookResponse) {
        final LinkedHashMap<String, Object> queryResult = (LinkedHashMap) webHookResponse.get("queryResult");
        final var facilities = getFacilities(queryResult, DialogFlowEntity.QUALITY);
        final var recommendation = recommendationMapper.map(facilities);
        final var session = (String) webHookResponse.get("session");
        final var split = session.split("/");
        final var sessionId = split[split.length - 1];
        recommendation.setSessionId(UUID.fromString(sessionId));
        return sendRecommendation(recommendation);
    }

    private List<Hotel> sendRecommendation(Recommendation recommendation) {
        return Arrays.asList(restTemplate.postForObject(recommendationUrl, SerializationUtil.write(recommendation), Hotel[].class));
    }

    private List<String> getFacilities(LinkedHashMap<String, Object> queryResult, DialogFlowEntity dialogFlowEntity) {
        LinkedHashMap parameters = (LinkedHashMap) queryResult.get("parameters");
        return (List<String>) parameters.get(dialogFlowEntity.getType());
    }
}


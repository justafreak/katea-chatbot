package ml.strikers.kateaserver.fulfilment.service;

import com.google.cloud.dialogflow.v2.QueryResult;
import ml.strikers.kateaserver.fulfilment.entity.DialogFlowEntity;
import ml.strikers.kateaserver.fulfilment.entity.Recommendation;
import ml.strikers.kateaserver.util.SerializationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MLService {


    @Value("${recommendation.client.url}")
    private String recommendationUrl;

    private RestTemplate restTemplate;

    @Autowired
    public MLService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Autowired
    RecommendationMapper recommendationMapper;


    public void preprocessTheRecommendation(QueryResult queryResult) {
        preprocessQualityFacilities(queryResult);
    }

    public void preprocessQualityFacilities(QueryResult queryResult) {
        final var facilities = getFacilities(queryResult, DialogFlowEntity.QUALITY);
        Recommendation recommendation = recommendationMapper.map(facilities);
        recommendation.setSessionId(UUID.randomUUID());
        sendRecommendation(recommendation);
    }

    private void sendRecommendation(Recommendation recommendation) {
        restTemplate.postForObject(recommendationUrl, SerializationUtil.write(recommendation), String.class);
    }

    private List<String> getFacilities(QueryResult queryResult, DialogFlowEntity dialogFlowEntity) {
        return queryResult.getParameters().getFieldsMap()
                .get(dialogFlowEntity.getType())
                .getListValue()
                .getValuesList()
                .stream()
                .map(com.google.protobuf.Value::getStringValue)
                .collect(Collectors.toList());
    }
}


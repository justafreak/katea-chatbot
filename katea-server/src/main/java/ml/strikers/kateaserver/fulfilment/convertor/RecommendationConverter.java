package ml.strikers.kateaserver.fulfilment.convertor;

import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2beta1WebhookResponse;
import ml.strikers.kateaserver.fulfilment.entity.DialogFlowEntity;
import ml.strikers.kateaserver.fulfilment.entity.Recommendation;
import ml.strikers.kateaserver.fulfilment.service.RecommendationMapper;
import ml.strikers.kateaserver.voting.rest.v1.dto.VoteRequest;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RecommendationConverter {

    public static Recommendation voteRequestTRecommendation(VoteRequest voteRequest) {
        final var facilities = getFacilitiesFromParametersMap(voteRequest.getParameters(), DialogFlowEntity.QUALITY);
        final var recommendation = RecommendationMapper.map(facilities);
        recommendation.setSessionId(voteRequest.getSessionId());
        return recommendation;
    }

    public static Recommendation webHookToRecmmendation(GoogleCloudDialogflowV2beta1WebhookResponse webHookResponse) {
        final LinkedHashMap<String, Object> queryResult = (LinkedHashMap) webHookResponse.get("queryResult");
        final var facilities = getFacilitiesFromParametersMap(getFacilitiesMap(queryResult), DialogFlowEntity.QUALITY);
        final var recommendation = RecommendationMapper.map(facilities);
        final var session = (String) webHookResponse.get("session");
        final var split = session.split("/");
        final var sessionId = split[split.length - 1];
        recommendation.setSessionId(UUID.fromString(sessionId));
        return recommendation;
    }

    private static LinkedHashMap<String, Object> getFacilitiesMap(LinkedHashMap<String, Object> queryResult) {
        return (LinkedHashMap) queryResult.get("parameters");
    }

    private static List<String> getFacilitiesFromParametersMap(Map<String, Object> parametersMap, DialogFlowEntity dialogFlowEntity) {
        return (List<String>) parametersMap.get(dialogFlowEntity.getType());
    }
}

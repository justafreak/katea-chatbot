package ml.strikers.kateaserver.fulfilment.service;

import com.google.api.client.util.Maps;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.*;
import lombok.extern.slf4j.Slf4j;
import ml.strikers.kateaserver.fulfilment.convertor.QueryResultConverter;
import ml.strikers.kateaserver.fulfilment.entity.Fulfilment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class DialogProvider {

    @Value("${dialogflow.credentials.location}")
    private Resource resourceFile;

    @Value("${dialogflow.project-id}")
    private String projectId;

    @Value("${dialogflow.default.language}")
    private String languageCode;

    private final MLService mlService;

    public DialogProvider(MLService mlService) {
        this.mlService = mlService;
    }

    public Fulfilment getFulfilment(String queryMessage, UUID uuid) throws Exception {
        QueryResult queryResult = getDialogFlowResponse(queryMessage, uuid);
        Fulfilment fulfilment = QueryResultConverter.covertResultToFulfilment(queryResult);
        fulfilment.setUUID(uuid);
        return fulfilment;
    }

    QueryResult getDialogFlowResponse(String queryMessage, UUID uuid) throws Exception {
        final Map<String, QueryResult> queryResults = Maps.newHashMap();
        final var credentials = GoogleCredentials.fromStream(resourceFile.getInputStream());
        final var sessionsSettings = SessionsSettings.newBuilder().setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build();
        try (SessionsClient sessionsClient = SessionsClient.create(sessionsSettings)) {
            final SessionName session = SessionName.of(projectId, uuid.toString());
            TextInput.Builder textInput = TextInput.newBuilder().setText(queryMessage).setLanguageCode(languageCode);
            QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();
            log.info(queryInput.toString());
            DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);
            QueryResult queryResult = response.getQueryResult();
            mlService.preprocessTheRecommendation(queryResult);
            queryResults.put(queryMessage, queryResult);
            return queryResult;
        }
    }

}


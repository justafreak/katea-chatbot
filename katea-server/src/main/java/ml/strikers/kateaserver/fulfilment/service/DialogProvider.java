package ml.strikers.kateaserver.fulfilment.service;

import com.google.api.client.util.Maps;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.*;
import lombok.extern.slf4j.Slf4j;
import ml.strikers.kateaserver.fulfilment.entity.Fulfilment;
import ml.strikers.kateaserver.fulfilment.entity.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
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

   private final FulfilmentService fulfilmentService;

    public DialogProvider(FulfilmentService fulfilmentService) {
        this.fulfilmentService = fulfilmentService;
    }

    public Fulfilment getFulfilment(String queryMessage, UUID uuid) throws Exception {
        Fulfilment fulfilment= new Fulfilment();
        QueryResult queryResult = detectIntentTexts(queryMessage, uuid);
        fulfilmentService.processFulfilment();
        return new Fulfilment();
    }


    public QueryResult detectIntentTexts(String queryMessage, UUID uuid) throws Exception {
        final Map<String, QueryResult> queryResults = Maps.newHashMap();
        final var credentials = GoogleCredentials.fromStream(resourceFile.getInputStream());
        final var sessionsSettings = SessionsSettings.newBuilder().setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build();
        try (SessionsClient sessionsClient = SessionsClient.create(sessionsSettings)) {
            UUID sessionId = uuid;
            final SessionName session = SessionName.of(projectId, sessionId.toString());
            TextInput.Builder textInput = TextInput.newBuilder().setText(queryMessage).setLanguageCode(languageCode);
            QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();
            DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);
            QueryResult queryResult = response.getQueryResult();
            log.info("Query Text: '{}'", queryResult.getQueryText());
            final var displayName = queryResult.getIntent().getDisplayName();
            final var intentDetectionConfidence = queryResult.getIntentDetectionConfidence();
            log.info("Detected Intent: '{}' (confidence: {})", displayName, intentDetectionConfidence);
            log.info("Fulfillment Text: '{}'", queryResult.getFulfillmentText());
            queryResults.put(queryMessage, queryResult);
            return queryResult;
        }
    }
}

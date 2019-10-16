package ml.strikers.kateaserver.service;

import com.google.api.client.util.Maps;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.*;
import lombok.extern.slf4j.Slf4j;
import ml.strikers.kateaserver.entity.Request;
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


    public Map<String, QueryResult> detectIntentTexts(Request request) throws Exception {
        final Map<String, QueryResult> queryResults = Maps.newHashMap();
        final var credentials = GoogleCredentials.fromStream(resourceFile.getInputStream());
        final var sessionsSettings = SessionsSettings.newBuilder().setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build();
        try (SessionsClient sessionsClient = SessionsClient.create(sessionsSettings)) {
            UUID sessionId = Objects.isNull(request.getSessionId()) ? UUID.randomUUID() : request.getSessionId();
            final SessionName session = SessionName.of(projectId, sessionId.toString());
            TextInput.Builder textInput = TextInput.newBuilder().setText(request.getMessage()).setLanguageCode(languageCode);
            QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();
            DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);
            QueryResult queryResult = response.getQueryResult();
            log.info("Query Text: '{}'", queryResult.getQueryText());
            final var displayName = queryResult.getIntent().getDisplayName();
            final var intentDetectionConfidence = queryResult.getIntentDetectionConfidence();
            log.info("Detected Intent: '{}' (confidence: {})", displayName, intentDetectionConfidence);
            log.info("Fulfillment Text: '{}'", queryResult.getFulfillmentText());
            queryResults.put(request.getMessage(), queryResult);
        }
        return queryResults;
    }
}

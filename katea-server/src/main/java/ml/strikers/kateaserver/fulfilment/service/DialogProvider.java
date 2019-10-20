package ml.strikers.kateaserver.fulfilment.service;

import com.google.api.client.util.Maps;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.QueryResult;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;
import com.google.cloud.dialogflow.v2.TextInput;
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

    public Fulfilment detectIntent(String queryMessage, UUID uuid) throws Exception {
        QueryResult queryResult = getDialogFlowResponse(queryMessage, uuid);
        Fulfilment fulfilment = QueryResultConverter.covertResultToFulfilment(queryResult);
        fulfilment.setUUID(uuid);
        return fulfilment;
    }

    public QueryResult getDialogFlowResponse(String queryMessage, UUID uuid) throws Exception {
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
            queryResults.put(queryMessage, queryResult);
            return queryResult;
        }
    }

}


package ml.strikers.kateaserver.service;

import com.google.api.client.util.Maps;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.cloud.dialogflow.v2.*;
import ml.strikers.kateaserver.entity.Request;
import ml.strikers.kateaserver.service.auth.GoogleAuthentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class DialogFlowIntentExtractor {

    @Value("classpath:google-auth/strikers-chatboot-dialog-account.json")
    private Resource resourceFile;

    @Value("${google.project.id}")
    private String projectId;

    @Value("${google.dialogflow.language.code}")
    private String languageCode;

    private final GoogleAuthentication googleAuthentication;

    public DialogFlowIntentExtractor(GoogleAuthentication googleAuthentication) {
        this.googleAuthentication = googleAuthentication;
    }

    public DetectIntentResponse detectIntentTexts(Request request) throws IOException {
        Map<String, QueryResult> queryResults = Maps.newHashMap();
        final var credentialsProvider = FixedCredentialsProvider.create(googleAuthentication.getGoogleCredentials(resourceFile));
        try (SessionsClient sessionsClient = SessionsClient.create()) {
            SessionName sessionname = SessionName.of(projectId, request.getSesionId() == null ? UUID.randomUUID().toString() : request.getSesionId().toString());

            TextInput.Builder textInput = TextInput.newBuilder().setText(request.getRequestBody()).setLanguageCode(languageCode);
            QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();
            return sessionsClient.detectIntent(sessionname, queryInput);

        }

    }
}
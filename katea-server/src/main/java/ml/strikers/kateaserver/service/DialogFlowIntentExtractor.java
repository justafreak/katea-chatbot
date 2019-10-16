package ml.strikers.kateaserver.service;

import com.google.api.client.util.Maps;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.cloud.dialogflow.v2.*;
import ml.strikers.kateaserver.service.auth.GoogleAuthentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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

    public Map<String, QueryResult> detectIntentTexts(List<String> texts, String sessionId) throws IOException {
        Map<String, QueryResult> queryResults = Maps.newHashMap();
        final var credentialsProvider = FixedCredentialsProvider.create(googleAuthentication.getGoogleCredentials(resourceFile));
        SessionsSettings sessionsSettings = SessionsSettings.newBuilder().setCredentialsProvider(credentialsProvider).build();
        try (SessionsClient sessionsClient = SessionsClient.create(sessionsSettings)) {
            SessionName sessionname = SessionName.of(projectId, sessionId);
            for (String text : texts) {
                TextInput.Builder textInput = TextInput.newBuilder().setText(text).setLanguageCode(languageCode);
                QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();
                DetectIntentResponse response = sessionsClient.detectIntent(sessionname, queryInput);
                QueryResult queryResult = response.getQueryResult();
                queryResults.put(text, queryResult);
            }
        }
        return queryResults;
    }

}

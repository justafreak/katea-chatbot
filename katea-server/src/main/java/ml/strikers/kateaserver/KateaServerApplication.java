package ml.strikers.kateaserver;

import com.google.api.client.util.Maps;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;


@SpringBootApplication
public class KateaServerApplication {

    public static void main(String[] args) throws Exception {
//        SpringApplication.run(KateaServerApplication.class, args);
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("C:\\Users\\admin\\Desktop\\katea-chatbot\\katea-server\\src\\main\\resources\\google-auth\\strikers-chatboot-490ee012a9a7.json"));

//        System.out.println("plm");
//
//        File jsonKey = new File("C:\\Users\\admin\\Desktop\\katea-chatbot\\katea-server\\src\\main\\resources\\google-auth\\strikers-chatboot-490ee012a9a7.json");
//        InputStream inputStream = new FileInputStream(jsonKey);
//        GoogleCredential credential =
//                GoogleCredential.fromStream(inputStream);
//        System.out.println( credential.getAccessToken());

        detectIntentTexts("strikers-chatboot",List.of("I need a place to stay October 03-05"),"1","ro");

    }


    public static Map<String, QueryResult> detectIntentTexts(
            String projectId,
            List<String> texts,
            String sessionId,
            String languageCode) throws Exception {
        Map<String, QueryResult> queryResults = Maps.newHashMap();
        // Instantiates a client
        try (SessionsClient sessionsClient = SessionsClient.create()) {
            // Set the session name using the sessionId (UUID) and projectID (my-project-id)
            SessionName session = SessionName.of(projectId, sessionId);
            System.out.println("Session Path: " + session.toString());

            // Detect intents for each text input
            for (String text : texts) {
                // Set the text (hello) and language code (en-US) for the query
                TextInput.Builder textInput = TextInput.newBuilder().setText(text).setLanguageCode(languageCode);

                // Build the query with the TextInput
                QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();

                // Performs the detect intent request
                DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);

                // Display the query result
                QueryResult queryResult = response.getQueryResult();

                System.out.println("====================");
                System.out.format("Query Text: '%s'\n", queryResult.getQueryText());
                System.out.format("Detected Intent: %s (confidence: %f)\n",
                        queryResult.getIntent().getDisplayName(), queryResult.getIntentDetectionConfidence());
                System.out.format("Fulfillment Text: '%s'\n", queryResult.getFulfillmentText());

                queryResults.put(text, queryResult);
            }
        }
        return queryResults;
    }

}

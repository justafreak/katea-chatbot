package ml.strikers.kateaserver.fulfilment.dialog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2beta1WebhookResponse;
import ml.strikers.kateaserver.fulfilment.entity.Request;
import ml.strikers.kateaserver.fulfilment.service.DialogProvider;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.LinkedHashMap;

@RestController
@RequestMapping("/dialog")
public class DialogController {

    private final DialogProvider detectIntentTexts;

    public DialogController(DialogProvider detectIntentTexts) {
        this.detectIntentTexts = detectIntentTexts;
    }


    @PostMapping("/webhook")
    public GoogleCloudDialogflowV2beta1WebhookResponse test(@RequestBody String response) throws IOException {
        GoogleCloudDialogflowV2beta1WebhookResponse webhookResponse =
                new ObjectMapper().readValue(response, GoogleCloudDialogflowV2beta1WebhookResponse.class);
        final LinkedHashMap<String, Object> queryResult = (LinkedHashMap) webhookResponse.get("queryResult");
        final LinkedHashMap<String, String> parameters = (LinkedHashMap<String, String>) queryResult.get("parameters");
        webhookResponse.setFulfillmentText("RECEIVED: " + parameters);
        return webhookResponse;
    }

    @PostMapping("/query")
    public String query(@RequestBody Request request) throws Exception {
        return detectIntentTexts.detectIntentTexts(request).toString();
    }

}

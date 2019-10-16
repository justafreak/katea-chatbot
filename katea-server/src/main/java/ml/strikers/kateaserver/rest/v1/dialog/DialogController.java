package ml.strikers.kateaserver.rest.v1.dialog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2beta1WebhookResponse;
import ml.strikers.kateaserver.entity.Hotel;
import ml.strikers.kateaserver.util.DataExtractor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("/dialog")
public class DialogController {

    private final DataExtractor dataExtractor;

    @Value("classpath:data/hotels.json")
    private Resource resourceFile;

    public DialogController(DataExtractor dataExtractor) {
        this.dataExtractor = dataExtractor;
    }

    @PostMapping("/webhook")
    public GoogleCloudDialogflowV2beta1WebhookResponse test(@RequestBody String response) throws IOException {
        GoogleCloudDialogflowV2beta1WebhookResponse webhookResponse =
                new ObjectMapper().readValue(response, GoogleCloudDialogflowV2beta1WebhookResponse.class);
        final LinkedHashMap<String, Object> queryResult = (LinkedHashMap<String, Object>) webhookResponse.get("queryResult");
        final LinkedHashMap<String, String> parameters = (LinkedHashMap<String, String>) queryResult.get("parameters");
        webhookResponse.setFulfillmentText("RECEIVED: " + parameters);
        return webhookResponse;
    }



    @GetMapping
    public List<Hotel> getAll() {
        return dataExtractor.extract(resourceFile, Hotel[].class);
    }
}

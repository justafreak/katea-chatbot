package ml.strikers.kateaserver.rest.v1.dialog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2beta1WebhookResponse;
import ml.strikers.kateaserver.entity.Hotel;
import ml.strikers.kateaserver.entity.Price;
import ml.strikers.kateaserver.entity.Request;
import ml.strikers.kateaserver.repository.HotelDataStoreAdapter;
import ml.strikers.kateaserver.service.DialogProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.UUID;

@RestController
@RequestMapping("/dialog")
public class DialogController {

    private final DialogProvider detectIntentTexts;

    @Autowired
    HotelDataStoreAdapter hotelDataStoreAdapter;

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

    @GetMapping("/{id}")
    public Hotel getById(@PathVariable UUID id) {
        hotelDataStoreAdapter.save(Hotel.builder().id(UUID.randomUUID())
                .city("Cluj")
                .name("test")
                .price(new Price())
                .url("url").imageUrl("imageUrl").rating(5.00).build());
        return hotelDataStoreAdapter.getById(id);
    }

}

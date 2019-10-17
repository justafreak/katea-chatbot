package ml.strikers.kateaserver.fulfilment.dialog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2beta1IntentMessage;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2beta1IntentMessageCarouselSelect;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2beta1IntentMessageCarouselSelectItem;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2beta1IntentMessageImage;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2beta1IntentMessageSelectItemInfo;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2beta1WebhookResponse;
import ml.strikers.kateaserver.fulfilment.entity.Hotel;
import ml.strikers.kateaserver.fulfilment.repository.HotelDataStoreAdapter;
import ml.strikers.kateaserver.fulfilment.service.DialogProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dialog")
public class DialogController {

    private final DialogProvider dialogProvider;


    public DialogController(DialogProvider dialogProvider) {
        this.dialogProvider = dialogProvider;
    }


    @PostMapping("/webhook")
    public GoogleCloudDialogflowV2beta1WebhookResponse test(@RequestBody String response) throws IOException {
        GoogleCloudDialogflowV2beta1WebhookResponse webhookResponse =
                new ObjectMapper().readValue(response, GoogleCloudDialogflowV2beta1WebhookResponse.class);

        final LinkedHashMap<String, Object> queryResult = (LinkedHashMap) webhookResponse.get("queryResult");
        final LinkedHashMap<String, String> parameters = (LinkedHashMap<String, String>) queryResult.get("parameters");

        webhookResponse.setFulfillmentMessages(List.of(convert(HotelDataStoreAdapter.getByCity("London"))));
//        final LinkedHashMap<String, String> intent = (LinkedHashMap<String, String>) queryResult.get("intent");
//        if (intent.get("displayName").equals("recommend")) {
//            final LinkedHashMap<String, Object> fullfillment = (LinkedHashMap<String, Object>) queryResult.get("fulfillmentMessages");
//
//        }

        webhookResponse.setFulfillmentText("RECEIVED: " + parameters);
        return webhookResponse;
    }


    private GoogleCloudDialogflowV2beta1IntentMessage convert(List<Hotel> hotels) throws JsonProcessingException {
        GoogleCloudDialogflowV2beta1IntentMessage newMessage = new GoogleCloudDialogflowV2beta1IntentMessage();
        GoogleCloudDialogflowV2beta1IntentMessageCarouselSelect carouselSelect = new GoogleCloudDialogflowV2beta1IntentMessageCarouselSelect()
                .setItems(new ArrayList<>());
        for (Hotel hotel : hotels) {
            GoogleCloudDialogflowV2beta1IntentMessageCarouselSelectItem carouselSelectItem = new GoogleCloudDialogflowV2beta1IntentMessageCarouselSelectItem();
            GoogleCloudDialogflowV2beta1IntentMessageImage image = new GoogleCloudDialogflowV2beta1IntentMessageImage();
            image.setImageUri(hotel.getImageUrl());
            carouselSelectItem.setImage(image);
            carouselSelectItem.setTitle(hotel.getName());
            carouselSelectItem.setDescription(new ObjectMapper().writeValueAsString(hotel));

            carouselSelectItem.set("hotelId", hotel.getId());
            carouselSelectItem.set("url", hotel.getUrl());
            carouselSelectItem.set("rating", hotel.getRating());
            carouselSelectItem.set("price", hotel.getPrice().getValue());
            carouselSelectItem.set("currency", hotel.getPrice().getCurrency());
            carouselSelectItem.set("city", hotel.getCity());
            carouselSelect.getItems().add(carouselSelectItem);
        }

        newMessage.setCarouselSelect(carouselSelect);
        return newMessage;
    }


//    @PostMapping("/query")
//    public String query(@RequestBody Request request) throws Exception {
//        return detectIntentTexts.detectIntentTexts(request).toString();
//    }

}

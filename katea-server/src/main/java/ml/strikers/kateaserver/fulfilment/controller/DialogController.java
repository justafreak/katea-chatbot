package ml.strikers.kateaserver.fulfilment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.dialogflow.v2.model.*;
import ml.strikers.kateaserver.fulfilment.entity.FullfilmentHotelRequest;
import ml.strikers.kateaserver.fulfilment.entity.Hotel;
import ml.strikers.kateaserver.fulfilment.repository.HotelRepository;
import ml.strikers.kateaserver.fulfilment.service.DialogProvider;
import ml.strikers.kateaserver.fulfilment.service.HotelRecommendService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("/dialog")
public class DialogController {

    private final DialogProvider dialogProvider;

    private final HotelRecommendService recommendService;


    public DialogController(DialogProvider dialogProvider, HotelRecommendService recommendService) {
        this.dialogProvider = dialogProvider;
        this.recommendService = recommendService;
    }


    @PostMapping("/webhook")
    public GoogleCloudDialogflowV2beta1WebhookResponse test(@RequestBody String response) throws IOException {
        GoogleCloudDialogflowV2beta1WebhookResponse webhookResponse =
                new ObjectMapper().readValue(response, GoogleCloudDialogflowV2beta1WebhookResponse.class);

        final LinkedHashMap<String, Object> queryResult = (LinkedHashMap) webhookResponse.get("queryResult");
        final LinkedHashMap<String, String> parameters = (LinkedHashMap<String, String>) queryResult.get("parameters");
        FullfilmentHotelRequest.builder().city(parameters.get("geo-city"))
                .companions(parameters.get("companions"))//.facilities(parameters.get("quality"))
                .tripType(parameters.get("trip-type")).build();
        webhookResponse.setFulfillmentMessages(List.of(convert(HotelRepository.getByCity("London"))));
//        final LinkedHashMap<String, String> intent = (LinkedHashMap<String, String>) queryResult.get("intent");
//        if (intent.get("displayName").equals("recommend")) {
//            final LinkedHashMap<String, Object> fullfillment = (LinkedHashMap<String, Object>) queryResult.get("fulfillmentMessages");
//
//        }

        webhookResponse.setFulfillmentText("Here our recommendations");
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
            carouselSelect.getItems().add(carouselSelectItem);
        }

        newMessage.setCarouselSelect(carouselSelect);
        return newMessage;
    }

}

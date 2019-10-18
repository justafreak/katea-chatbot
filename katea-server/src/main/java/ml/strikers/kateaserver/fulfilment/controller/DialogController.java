package ml.strikers.kateaserver.fulfilment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.dialogflow.v2.model.*;
import lombok.extern.slf4j.Slf4j;
import ml.strikers.kateaserver.fulfilment.entity.FulfilmentHotelRequest;
import ml.strikers.kateaserver.fulfilment.entity.Hotel;
import ml.strikers.kateaserver.fulfilment.entity.VoteHotel;
import ml.strikers.kateaserver.fulfilment.repository.HotelRepository;
import ml.strikers.kateaserver.fulfilment.service.DialogProvider;
import ml.strikers.kateaserver.fulfilment.service.HotelRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/dialog")
@Slf4j
public class DialogController {

    private final DialogProvider dialogProvider;

    private final HotelRecommendService recommendService;

    @Autowired
    HotelRepository hotelRepository;


    public DialogController(DialogProvider dialogProvider, HotelRecommendService recommendService) {
        this.dialogProvider = dialogProvider;
        this.recommendService = recommendService;
    }

    private enum Intent {
        VOTE("vote"),
        RECOMMEND("recommend");

        private String fieldName;

        Intent(String name) {
            fieldName = name;
        }
    }


    @GetMapping
    public List<Hotel> trigger(@RequestParam String city) {
        return hotelRepository.getHotelsByCity(city);
    }

    @PostMapping("/webhook")
    @SuppressWarnings("unchecked")
    public GoogleCloudDialogflowV2beta1WebhookResponse test(@RequestBody String response) throws IOException {
        GoogleCloudDialogflowV2beta1WebhookResponse webHookResponse =
                new ObjectMapper().readValue(response, GoogleCloudDialogflowV2beta1WebhookResponse.class);

        final LinkedHashMap<String, Object> queryResult = (LinkedHashMap) webHookResponse.get("queryResult");
        final LinkedHashMap<String, Object> parameters = (LinkedHashMap) queryResult.get("parameters");
        final LinkedHashMap<String, String> intent = (LinkedHashMap<String, String>) queryResult.get("intent");
        String intentName = intent.get("displayName");
        if ("recommend".equals(intentName)) {
            FulfilmentHotelRequest request = FulfilmentHotelRequest.builder()
                    .city((String) parameters.get("geo-city"))
                    .companions((String) parameters.get("companions"))
                    .facilities((List) parameters.get("quality"))
                    .tripType((String) parameters.get("trip-type")).build();
            webHookResponse.setFulfillmentMessages(List.of(convert(hotelRepository.getHotelsByCity(request.getCity()))));
            webHookResponse.setFulfillmentText("Here our recommendations");
        } else if ("vote".equals(intentName)) {
            log.info("Parameters: {}", parameters);
            final List<LinkedHashMap<String, Object>> contexts = (List) queryResult.get("outputContexts");
            final LinkedHashMap<String, Object> contextParameters = (LinkedHashMap) contexts.get(0).get("parameters");

            final var build = FulfilmentHotelRequest.builder()
                    .city((String) contextParameters.get("geo-city"))
                    .companions((String) contextParameters.get("companions"))
                    .facilities((List) contextParameters.get("quality"))
                    .tripType((String) contextParameters.get("trip-type"))
                    .build();

            final var request = VoteHotel.builder().fulfilmentHotelRequest(build)
                    .action(VoteHotel.Action.valueOf((String) parameters.get("recommend")))
                    .hotelId(UUID.fromString((String) parameters.get("id")))
                    .build();
            log.info("Request: {}", request);
            webHookResponse.setFulfillmentText("Thanks for voting");
        }
        return webHookResponse;
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

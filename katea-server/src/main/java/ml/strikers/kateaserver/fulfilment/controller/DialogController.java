package ml.strikers.kateaserver.fulfilment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.dialogflow.v2.model.*;
import ml.strikers.kateaserver.fulfilment.entity.FulfilmentHotelRequest;
import ml.strikers.kateaserver.fulfilment.entity.Hotel;
import ml.strikers.kateaserver.fulfilment.entity.VoteHotel;
import ml.strikers.kateaserver.fulfilment.repository.HotelRepository;
import ml.strikers.kateaserver.fulfilment.service.DialogProvider;
import ml.strikers.kateaserver.fulfilment.service.HotelRecommendService;
import ml.strikers.kateaserver.util.DataExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/dialog")
public class DialogController {

    private final DialogProvider dialogProvider;

    private final HotelRecommendService recommendService;

    @Autowired
    DataExtractor dataExtractor;

    @Autowired
    HotelRepository hotelRepository;


    @Value("classpath:data/reviews.json")
    Resource resource;

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
    public List<Hotel> trigger() {
        return hotelRepository.getHotelsByCity("London");
    }

    @PostMapping("/webhook")
    public GoogleCloudDialogflowV2beta1WebhookResponse test(@RequestBody String response) throws IOException {
        GoogleCloudDialogflowV2beta1WebhookResponse webhookResponse =
                new ObjectMapper().readValue(response, GoogleCloudDialogflowV2beta1WebhookResponse.class);

        final LinkedHashMap<String, Object> queryResult = (LinkedHashMap) webhookResponse.get("queryResult");
        final LinkedHashMap<String, Object> parameters = (LinkedHashMap) queryResult.get("parameters");
        final LinkedHashMap<String, String> intent = (LinkedHashMap<String, String>) queryResult.get("intent");
        String intentName = intent.get("displayName");
        if (intentName.equals("recommend")) {
            FullfilmentHotelRequest request = FullfilmentHotelRequest.builder()
                    .city((String) parameters.get("geo-city"))
                    .companions((String) parameters.get("companions"))
                    .facilities((List) parameters.get("quality"))
                    .tripType((String) parameters.get("trip-type")).build();
            webhookResponse.setFulfillmentMessages(List.of(convert(HotelRepository.getByCity(request.getCity()))));
            webhookResponse.setFulfillmentText("Here our recommendations");
        } else if (intentName.equals("vote")) {
            System.out.println(parameters);
            final List<LinkedHashMap<String, Object>> contexts = (List) queryResult.get("outputContexts");
            final LinkedHashMap<String, Object> contextParameters = (LinkedHashMap) contexts.get(0).get("parameters");

            VoteHotel request = VoteHotel.builder().fullfilmentHotelRequest(FullfilmentHotelRequest.builder()
                    .city((String) contextParameters.get("geo-city"))
                    .companions((String) contextParameters.get("companions"))
                    .facilities((List) contextParameters.get("quality"))
                    .tripType((String) contextParameters.get("trip-type")).build())
                    .action(VoteHotel.Action.valueOf((String) parameters.get("recommend")))
                    .hotelId(UUID.fromString((String) parameters.get("id")))
                    .build();
            System.out.println(request);
            webhookResponse.setFulfillmentText("Thanks for voting");
        }
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

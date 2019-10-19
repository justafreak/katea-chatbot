package ml.strikers.kateaserver.fulfilment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.api.services.dialogflow.v2.model.*;
import lombok.Getter;
import ml.strikers.kateaserver.fulfilment.entity.FulfilmentHotelRequest;
import ml.strikers.kateaserver.fulfilment.entity.Hotel;
import ml.strikers.kateaserver.fulfilment.entity.VoteHotel;
import ml.strikers.kateaserver.fulfilment.repository.HotelRepository;
import ml.strikers.kateaserver.util.SerializationUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

@Service
public class FulfillmentDispatchService {

    private final HotelRepository hotelRepository;

    private final HotelRecommendService hotelRecommendService;

    private final MLService mlService;

    public FulfillmentDispatchService(HotelRepository hotelRepository, HotelRecommendService hotelRecommendService, MLService mlService) {
        this.hotelRepository = hotelRepository;
        this.hotelRecommendService = hotelRecommendService;
        this.mlService = mlService;
    }

    @SuppressWarnings("unchecked")
    public GoogleCloudDialogflowV2beta1WebhookResponse handle(GoogleCloudDialogflowV2beta1WebhookResponse webHookResponse) throws JsonProcessingException {
        final LinkedHashMap<String, Object> queryResult = (LinkedHashMap) webHookResponse.get("queryResult");
        final var intentString = (String) ((LinkedHashMap) queryResult.get("intent")).get("displayName");
        final var intent = Intent.valueOf(intentString.toUpperCase());
        switch (intent) {
            case VOTE:
                return voteIntent(webHookResponse);
            case RECOMMEND:
                return recommendIntent(webHookResponse);
        }
        return webHookResponse;
    }

    @SuppressWarnings("unchecked")
    private GoogleCloudDialogflowV2beta1WebhookResponse recommendIntent(GoogleCloudDialogflowV2beta1WebhookResponse webHookResponse) {
        webHookResponse.setFulfillmentMessages(List.of(convert(mlService.preprocessQualityFacilities(webHookResponse))));
        webHookResponse.setFulfillmentText("Here are our recommendations");
        return webHookResponse;
    }

    @SuppressWarnings("unchecked")
    private GoogleCloudDialogflowV2beta1WebhookResponse voteIntent(GoogleCloudDialogflowV2beta1WebhookResponse webHookResponse) {
        final LinkedHashMap<String, Object> queryResult = (LinkedHashMap) webHookResponse.get("queryResult");
        final List<LinkedHashMap<String, Object>> contexts = (List) queryResult.get("outputContexts");
        final LinkedHashMap<String, Object> contextParameters = (LinkedHashMap) contexts.get(0).get("parameters");
        final LinkedHashMap<String, Object> parameters = (LinkedHashMap) queryResult.get("parameters");

        final var build = FulfilmentHotelRequest.builder()
                .city((String) contextParameters.get("geo-city"))
                .companions((String) contextParameters.get("companions"))
                .facilities((List) contextParameters.get("quality"))
                .tripType((String) contextParameters.get("trip-type"))
                .build();

        final var request = VoteHotel.builder().fulfilmentHotelRequest(build)
                .action(VoteHotel.Action.valueOf(((String) parameters.get("recommend")).toUpperCase()))
                .hotelId(UUID.fromString((String) parameters.get("id")))
                .build();
        webHookResponse.setFulfillmentText("Thanks for voting");
        return webHookResponse;
    }

    private GoogleCloudDialogflowV2beta1IntentMessage convert(List<Hotel> hotels) {
        GoogleCloudDialogflowV2beta1IntentMessage newMessage = new GoogleCloudDialogflowV2beta1IntentMessage();
        GoogleCloudDialogflowV2beta1IntentMessageCarouselSelect carouselSelect = new GoogleCloudDialogflowV2beta1IntentMessageCarouselSelect()
                .setItems(new ArrayList<>());
        for (Hotel hotel : hotels) {
            GoogleCloudDialogflowV2beta1IntentMessageCarouselSelectItem carouselSelectItem = new GoogleCloudDialogflowV2beta1IntentMessageCarouselSelectItem();
            GoogleCloudDialogflowV2beta1IntentMessageImage image = new GoogleCloudDialogflowV2beta1IntentMessageImage();
            image.setImageUri(hotel.getImageUrl());
            carouselSelectItem.setImage(image);
            carouselSelectItem.setTitle(hotel.getName());
            carouselSelectItem.setDescription(SerializationUtil.write(hotel));
            carouselSelect.getItems().add(carouselSelectItem);
        }

        newMessage.setCarouselSelect(carouselSelect);
        return newMessage;
    }

    @Getter
    private enum Intent {
        VOTE("vote"),
        RECOMMEND("recommend");

        private String type;

        Intent(String type) {
            this.type = type;
        }

    }

}

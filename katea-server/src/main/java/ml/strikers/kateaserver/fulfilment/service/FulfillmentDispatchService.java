package ml.strikers.kateaserver.fulfilment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2beta1IntentMessage;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2beta1IntentMessageCarouselSelect;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2beta1IntentMessageCarouselSelectItem;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2beta1IntentMessageImage;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2beta1WebhookResponse;
import ml.strikers.kateaserver.fulfilment.entity.FulfilmentHotelRequest;
import ml.strikers.kateaserver.fulfilment.entity.Hotel;
import ml.strikers.kateaserver.fulfilment.entity.VoteHotel;
import ml.strikers.kateaserver.fulfilment.repository.HotelRepository;
import ml.strikers.kateaserver.util.SerializationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unchecked")
@Service
public class FulfillmentDispatchService {

    @Autowired
    private HotelRepository hotelRepository;


    @SuppressWarnings("unchecked")
    public GoogleCloudDialogflowV2beta1WebhookResponse dispatch(GoogleCloudDialogflowV2beta1WebhookResponse webHookResponse) throws JsonProcessingException {
        final LinkedHashMap<String, Object> queryResult = (LinkedHashMap) webHookResponse.get("queryResult");
        String intentString = (String) ((LinkedHashMap) queryResult.get("intent")).get("displayName");
        Intent intent = Intent.valueOf(intentString);
        switch (intent) {
            case VOTE:
                return voteIntent(webHookResponse);
            case RECOMMEND:
                return recommendIntent(webHookResponse);
        }
        return webHookResponse;
    }

    @SuppressWarnings("unchecked")
    private GoogleCloudDialogflowV2beta1WebhookResponse recommendIntent(GoogleCloudDialogflowV2beta1WebhookResponse webHookResponse) throws JsonProcessingException {
        final LinkedHashMap<String, Object> queryResult = (LinkedHashMap) webHookResponse.get("queryResult");
        final LinkedHashMap<String, Object> parameters = (LinkedHashMap) queryResult.get("parameters");
        FulfilmentHotelRequest request = FulfilmentHotelRequest.builder()
                .city((String) parameters.get("geo-city"))
                .companions((String) parameters.get("companions"))
                .facilities((List) parameters.get("quality"))
                .tripType((String) parameters.get("trip-type")).build();
        webHookResponse.setFulfillmentMessages(List.of(convert(hotelRepository.getHotelsByCity(request.getCity()))));
        webHookResponse.setFulfillmentText("Here are our recommendations");
        return webHookResponse;
    }

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
                .action(VoteHotel.Action.valueOf((String) parameters.get("recommend")))
                .hotelId(UUID.fromString((String) parameters.get("id")))
                .build();
        webHookResponse.setFulfillmentText("Thanks for voting");
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
            carouselSelectItem.setDescription(SerializationUtil.write(hotel));
            carouselSelect.getItems().add(carouselSelectItem);
        }

        newMessage.setCarouselSelect(carouselSelect);
        return newMessage;
    }

    private enum Intent {
        VOTE("vote"),
        RECOMMEND("recommend");

        private String fieldName;

        Intent(String name) {
            fieldName = name;
        }
    }

}

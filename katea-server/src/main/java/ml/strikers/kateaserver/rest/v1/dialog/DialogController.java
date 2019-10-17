package ml.strikers.kateaserver.rest.v1.dialog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2beta1WebhookResponse;
import ml.strikers.kateaserver.entity.dto.FullfilmentHotelRequest;
import ml.strikers.kateaserver.entity.dto.UiResponse;
import ml.strikers.kateaserver.service.HotelRecommendService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/dialog")
public class DialogController {

    private final HotelRecommendService recommendService;
    private static final ObjectMapper json = new ObjectMapper();

    public DialogController(HotelRecommendService recommendService) {
        this.recommendService = recommendService;
    }

    @PostMapping("/webhook")
    public GoogleCloudDialogflowV2beta1WebhookResponse test(@RequestBody String response) throws IOException {
        GoogleCloudDialogflowV2beta1WebhookResponse webhookResponse =
                new ObjectMapper().readValue(response, GoogleCloudDialogflowV2beta1WebhookResponse.class);
        final LinkedHashMap<String, Object> queryResult = (LinkedHashMap) webhookResponse.get("queryResult");
        final LinkedHashMap<String, Object> parameters = (LinkedHashMap<String, Object>) queryResult.get("parameters");

        UiResponse uiResponse = recommendService.getHotels(mapToDto(parameters));
        webhookResponse.setFulfillmentText(json.writeValueAsString(uiResponse));
        return webhookResponse;
    }

    private FullfilmentHotelRequest mapToDto(Map<String, Object> request) {
        return FullfilmentHotelRequest.builder()
                .build();
    }
}



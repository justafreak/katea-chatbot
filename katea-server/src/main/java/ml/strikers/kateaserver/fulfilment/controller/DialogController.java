package ml.strikers.kateaserver.fulfilment.controller;

import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2beta1WebhookResponse;
import lombok.extern.slf4j.Slf4j;
import ml.strikers.kateaserver.fulfilment.entity.FulfilmentHotelRequest;
import ml.strikers.kateaserver.fulfilment.entity.Hotel;
import ml.strikers.kateaserver.fulfilment.repository.HotelRepository;
import ml.strikers.kateaserver.fulfilment.service.FulfillmentDispatchService;
import ml.strikers.kateaserver.fulfilment.service.HotelRecommendService;
import ml.strikers.kateaserver.util.DatabasePopulator;
import ml.strikers.kateaserver.util.SerializationUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/dialog")
@Slf4j
public class DialogController {

    private final HotelRepository hotelRepository;
    private final FulfillmentDispatchService fulfillmentDispatchService;
    private final HotelRecommendService hotelRecommendService;
    private final DatabasePopulator databasePopulator;

    public DialogController(FulfillmentDispatchService fulfillmentDispatchService, HotelRepository hotelRepository, HotelRecommendService hotelRecommendService, DatabasePopulator databasePopulator) {
        this.fulfillmentDispatchService = fulfillmentDispatchService;
        this.hotelRepository = hotelRepository;
        this.hotelRecommendService = hotelRecommendService;
        this.databasePopulator = databasePopulator;
    }


    @GetMapping
    public List<Hotel> getTopThreeHotelsFrom(@RequestParam String city) {
        return hotelRepository.getHotelsByCity(city);
    }

    @PostMapping("/webhook")
    public GoogleCloudDialogflowV2beta1WebhookResponse test(@RequestBody String response) throws IOException {
        return fulfillmentDispatchService.handle(
                SerializationUtil.read(response, GoogleCloudDialogflowV2beta1WebhookResponse.class)
        );
    }

    @GetMapping("/suggestions")
    public List<Hotel> getSuggestions() {
        return hotelRecommendService.getMatchingHotelsML(new FulfilmentHotelRequest());
    }

    @GetMapping("/populator")
    public void databasePopulator() {
        databasePopulator.populate();

    }

}

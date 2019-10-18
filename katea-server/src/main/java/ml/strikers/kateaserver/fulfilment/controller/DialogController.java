package ml.strikers.kateaserver.fulfilment.controller;

import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2beta1WebhookResponse;
import lombok.extern.slf4j.Slf4j;
import ml.strikers.kateaserver.fulfilment.entity.Hotel;
import ml.strikers.kateaserver.fulfilment.repository.HotelRepository;
import ml.strikers.kateaserver.fulfilment.service.FulfillmentDispatchService;
import ml.strikers.kateaserver.util.SerializationUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    private FulfillmentDispatchService fulfillmentDispatchService;

    @GetMapping
    public List<Hotel> trigger(@RequestParam String city) {
        return hotelRepository.getHotelsByCity(city);
    }

    @PostMapping("/webhook")
    public GoogleCloudDialogflowV2beta1WebhookResponse test(@RequestBody String response) throws IOException {
        return fulfillmentDispatchService.dispatch(
                SerializationUtil.read(response, GoogleCloudDialogflowV2beta1WebhookResponse.class)
        );
    }

}

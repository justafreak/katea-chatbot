package ml.strikers.kateaserver.rest.v1.dialog;

import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import ml.strikers.kateaserver.entity.Request;
import ml.strikers.kateaserver.service.DialogFlowIntentExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RestController
@RequestMapping("/ui")
@CrossOrigin(origins = "*", methods = RequestMethod.POST, allowedHeaders = "*", maxAge = 6000L)
public class PassThroughController {


    @Autowired
    private DialogFlowIntentExtractor dialogFlowIntentExtractor;

    private final RestTemplate restTemplate;

    public PassThroughController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/query")
    public DetectIntentResponse query(@RequestBody Request request) throws IOException {
        return dialogFlowIntentExtractor.detectIntentTexts(request);
    }

}

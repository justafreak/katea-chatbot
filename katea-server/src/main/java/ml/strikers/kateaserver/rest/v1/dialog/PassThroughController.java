package ml.strikers.kateaserver.rest.v1.dialog;

import ml.strikers.kateaserver.entity.dto.PassthroughRequest;
import ml.strikers.kateaserver.service.DialogProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/ui")
@CrossOrigin(origins = "*", methods = RequestMethod.POST, allowedHeaders = "*", maxAge = 6000L)
public class PassThroughController {


    @Autowired
    private DialogProvider dialogProvider;

    private final RestTemplate restTemplate;

    public PassThroughController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/query")
    public String query(@RequestBody PassthroughRequest passthroughRequest) throws Exception {
        return dialogProvider.detectIntentTexts(passthroughRequest).toString();
    }

}

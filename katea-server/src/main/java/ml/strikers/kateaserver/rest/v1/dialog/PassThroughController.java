package ml.strikers.kateaserver.rest.v1.dialog;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/ui")
@CrossOrigin(origins = "*", methods = RequestMethod.POST, allowedHeaders = "*", maxAge = 6000L)
public class PassThroughController {

    private final RestTemplate restTemplate;

    private static final String URL = "https://dialogflow.googleapis.com/v2beta1/projects/strikers-chatboot/agent/sessions/1cdf983b-f9d9-b74d-9fdc-b5a5fb3c25b5:detectIntent";

    public PassThroughController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/query")
    public ResponseEntity<String> query(@RequestBody String response) {
        return restTemplate.postForEntity(URL, response, String.class);
    }

}

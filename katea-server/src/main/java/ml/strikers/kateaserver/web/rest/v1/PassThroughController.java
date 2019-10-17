package ml.strikers.kateaserver.web.rest.v1;

import ml.strikers.kateaserver.fulfilment.entity.Request;
import ml.strikers.kateaserver.fulfilment.service.DialogProvider;
import ml.strikers.kateaserver.web.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/ui")
@CrossOrigin(origins = "*", methods = RequestMethod.POST, allowedHeaders = "*", maxAge = 6000L)
public class PassThroughController {


    @Autowired
    private RequestService requestService;

    private final RestTemplate restTemplate;

    public PassThroughController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/query")
    public String query(@RequestBody Request request) throws Exception {
        return requestService.getResponse(request).toString();
    }

}

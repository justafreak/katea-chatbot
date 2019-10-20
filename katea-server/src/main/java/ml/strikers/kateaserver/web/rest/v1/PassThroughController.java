package ml.strikers.kateaserver.web.rest.v1;

import ml.strikers.kateaserver.fulfilment.entity.Request;
import ml.strikers.kateaserver.web.rest.v1.dto.Response;
import ml.strikers.kateaserver.web.service.RequestService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ui")
@CrossOrigin(origins = "*", methods = RequestMethod.POST, allowedHeaders = "*", maxAge = 6000L)
public class PassThroughController {

    private final RequestService requestService;

    public PassThroughController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping("/query")
    public Response detectIntent(@RequestBody Request request) throws Exception {
        return requestService.detectIntent(request);
    }

}

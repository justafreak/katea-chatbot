package ml.strikers.kateaserver.web.rest.v1;

import ml.strikers.kateaserver.fulfilment.entity.Request;
import ml.strikers.kateaserver.web.rest.v1.dto.Response;
import ml.strikers.kateaserver.web.service.RequestService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ui")
@CrossOrigin(origins = "*", methods = RequestMethod.POST, allowedHeaders = "*", maxAge = 6000L)
public class PassThroughController {

    private final RequestService requestService;

    public PassThroughController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping("/query")
    public Response query(@RequestBody Request request) throws Exception {
        return requestService.getResponse(request);
    }

}

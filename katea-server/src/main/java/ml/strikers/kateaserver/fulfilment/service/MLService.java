package ml.strikers.kateaserver.fulfilment.service;

import ml.strikers.kateaserver.fulfilment.entity.FulfilmentHotelRequest;
import ml.strikers.kateaserver.fulfilment.entity.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class MLService {

    @Value("${recommendation.client.url}")
    private String recommendationUrl;

    private RestTemplate restTemplate;

    @Autowired
    public MLService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Hotel> getSuggestions(FulfilmentHotelRequest request) {
        return restTemplate
                .exchange(
                        recommendationUrl,
                        HttpMethod.POST,
                        null,
                        new ParameterizedTypeReference<List<Hotel>>(){})
                .getBody();
    }
}


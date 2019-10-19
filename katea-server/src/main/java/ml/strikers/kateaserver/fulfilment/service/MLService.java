package ml.strikers.kateaserver.fulfilment.service;

import com.google.cloud.dialogflow.v2.QueryResult;
import ml.strikers.kateaserver.fulfilment.entity.FulfilmentHotelRequest;
import ml.strikers.kateaserver.fulfilment.entity.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
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

    @Autowired
    RecommendationMapper recommendationMapper;


    public void preprocessTheRecommendation(QueryResult queryResult) {
        final var parameters = queryResult.getParameters();
        final var quality = parameters.getFieldsMap()
                .get("quality")
                .getListValue()
                .getValues(0);

        final var allFields = quality.getAllFields();

        Recommendation recommendation = new Recommendation();
        HttpEntity<Recommendation> request = new HttpEntity<>(recommendation);
        restTemplate.postForEntity("https://4f96dd0f.ngrok.io/suggestions", request, Recommendation.class);
        allFields.get("string_value");


    }
}


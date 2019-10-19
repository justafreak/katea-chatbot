package ml.strikers.kateaserver.fulfilment.service;

import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2beta1WebhookResponse;
import ml.strikers.kateaserver.fulfilment.convertor.RecommendationConverter;
import ml.strikers.kateaserver.fulfilment.entity.Hotel;
import ml.strikers.kateaserver.fulfilment.entity.Recommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class MLService {


    @Value("${recommendation.client.url}")
    private String recommendationUrl;

    @Value("${train.client.url}")
    private String trainUrl;

    private RestTemplate restTemplate;

    private final AtomicBoolean trainingReady = new AtomicBoolean(false);

    private final ScheduledExecutorService service = Executors.newScheduledThreadPool(1);


    @Autowired
    public MLService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        service.scheduleAtFixedRate(() -> {
            trainingReady.set(true);
        }, 1,3, TimeUnit.MINUTES);

    }

    public List<Hotel> preprocessQualityFacilities(GoogleCloudDialogflowV2beta1WebhookResponse webHookResponse) {
        return getSuggestions(RecommendationConverter.webHookToRecmmendation(webHookResponse));
    }

    public List<Hotel> getSuggestions(Recommendation recommendation) {
        return Arrays.asList(restTemplate.postForObject(recommendationUrl, recommendation, Hotel[].class));
    }

    public void triggerTrain() {
        if (trainingReady.get()) {
            restTemplate.postForObject(trainUrl, null, String.class);
            trainingReady.set(false);
        }
    }

}


package ml.strikers.kateaserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import ml.strikers.kateaserver.entity.Hotel;
import ml.strikers.kateaserver.entity.dto.*;
import ml.strikers.kateaserver.repository.HotelDataStoreAdapter;
import org.springframework.stereotype.Component;

@Component
public class HotelRecommendService {

    private final HotelDataStoreAdapter dataStore;
    private final MLService mlService;

    public HotelRecommendService(HotelDataStoreAdapter dataStore, MLService mlService) {
        this.dataStore = dataStore;
        this.mlService = mlService;
    }

    public UiResponse getHotels(FullfilmentHotelRequest request) {
        Hotel hotel = Hotel.builder().city("London").build();
        UiResponse uiResponse = new UiResponse();
        CarouselResponse message = new CarouselResponse();
        message.setReply(Lists.newArrayList(hotel));
        uiResponse.setMessage(message);
        return uiResponse;
    }
}

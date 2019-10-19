package ml.strikers.kateaserver.fulfilment.service;

import ml.strikers.kateaserver.fulfilment.entity.FulfilmentHotelRequest;
import ml.strikers.kateaserver.fulfilment.entity.Hotel;
import ml.strikers.kateaserver.fulfilment.repository.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelRecommendService {
    private final HotelRepository hotelRepository;
    private final ReviewMatchingService reviewMatchingService;
    private final MLService mlService;

    public HotelRecommendService(HotelRepository hotelRepository, ReviewMatchingService reviewMatchingService, MLService mlService, FuzzyMatchingService fuzzyMatchingService) {
        this.hotelRepository = hotelRepository;
        this.reviewMatchingService = reviewMatchingService;
        this.mlService = mlService;
    }

    public List<Hotel> getMatchingHotels(FulfilmentHotelRequest request) {
        List<Hotel> hotelsByCity = hotelRepository.getHotelsByCity(request.getCity());
        hotelsByCity.sort(Hotel.SCORE_COMPARATOR);
        return hotelsByCity;
    }

    public List<Hotel> getMatchingHotelsML(FulfilmentHotelRequest request) {
//        List<Hotel> hotelsByCity = mlService.getSuggestions(request);
        List<Hotel> hotelsByCity = null;
        hotelsByCity.sort(Hotel.SCORE_COMPARATOR);
        return hotelsByCity;
    }

}

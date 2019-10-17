package ml.strikers.kateaserver.fulfilment.service;

import ml.strikers.kateaserver.fulfilment.entity.FullfilmentHotelRequest;
import ml.strikers.kateaserver.fulfilment.entity.Hotel;
import ml.strikers.kateaserver.fulfilment.repository.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelRecommendService {
    private final HotelRepository hotelRepository;
    private final ReviewMatchingService reviewMatchingService;
    private final MLService mlService;
    private final FuzzyMatchingService fuzzyMatchingService;

    public HotelRecommendService(HotelRepository hotelRepository, ReviewMatchingService reviewMatchingService, MLService mlService, FuzzyMatchingService fuzzyMatchingService) {
        this.hotelRepository = hotelRepository;
        this.reviewMatchingService = reviewMatchingService;
        this.mlService = mlService;
        this.fuzzyMatchingService = fuzzyMatchingService;
    }

    public List<Hotel> getMatchingHotels(FullfilmentHotelRequest request) {
        List<Hotel> hotelsByCity = hotelRepository.getHotelsByCity(request.getCity());
        hotelsByCity.sort(Hotel.SCORE_COMPARATOR);
        return hotelsByCity;
    }


}

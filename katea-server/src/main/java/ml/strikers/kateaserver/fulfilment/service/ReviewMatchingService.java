package ml.strikers.kateaserver.fulfilment.service;

import ml.strikers.kateaserver.fulfilment.entity.FullfilmentHotelRequest;
import ml.strikers.kateaserver.fulfilment.entity.Hotel;
import ml.strikers.kateaserver.fulfilment.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ReviewMatchingService {

    private final ReviewRepository reviewRepository;

    public ReviewMatchingService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    List<Hotel> getMatching(FullfilmentHotelRequest request) {
        return Collections.emptyList();
    }
}

package ml.strikers.kateaserver.util;


import ml.strikers.kateaserver.fulfilment.entity.Hotel;
import ml.strikers.kateaserver.fulfilment.entity.Review;
import ml.strikers.kateaserver.fulfilment.repository.HotelRepository;
import ml.strikers.kateaserver.fulfilment.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabasePopulator {

    private final DataExtractor dataExtractor;

    @Value("classpath:data/hotels.json")
    private Resource hotelsResource;

    @Value("classpath:data/reviews.json")
    private Resource reviewsResource;

    private final HotelRepository hotelRepository;

    private final ReviewRepository reviewRepository;

    public DatabasePopulator(DataExtractor dataExtractor, HotelRepository hotelRepository, ReviewRepository reviewRepository) {
        this.dataExtractor = dataExtractor;
        this.hotelRepository = hotelRepository;
        this.reviewRepository = reviewRepository;
    }


    public void populate() {
        List<Hotel> hotels = dataExtractor.extract(hotelsResource, Hotel[].class);
//        List<Review> reviews = dataExtractor.extract(reviewsResource, Review[].class);
        hotels.forEach(hotelRepository::save);
//        reviews.forEach(reviewRepository::save);
    }

}


package ml.strikers.kateaserver.util;


import ml.strikers.kateaserver.fulfilment.entity.Hotel;
import ml.strikers.kateaserver.fulfilment.entity.Recommendation;
import ml.strikers.kateaserver.fulfilment.repository.HotelRepository;
import ml.strikers.kateaserver.fulfilment.repository.RecommendationRepository;
import ml.strikers.kateaserver.fulfilment.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabasePopulator {

    private final DataExtractor dataExtractor;
    private final HotelRepository hotelRepository;
    private final ReviewRepository reviewRepository;
    private final RecommendationRepository recommendationRepository;
    @Value("classpath:data/hotels.json")
    private Resource hotelsResource;
    @Value("classpath:data/reviews.json")
    private Resource reviewsResource;
    @Value("classpath:data/recommendation.json")
    private Resource recommendationResource;

    public DatabasePopulator(DataExtractor dataExtractor, HotelRepository hotelRepository, ReviewRepository reviewRepository, RecommendationRepository recommendationRepository) {
        this.dataExtractor = dataExtractor;
        this.hotelRepository = hotelRepository;
        this.reviewRepository = reviewRepository;
        this.recommendationRepository = recommendationRepository;
    }


    public void populate() {
        List<Hotel> hotels = dataExtractor.extract(hotelsResource, Hotel[].class);
//        List<Review> reviews = dataExtractor.extract(reviewsResource, Review[].class);
        hotels.forEach(hotelRepository::save);
//        reviews.forEach(reviewRepository::save);
//        List<Recommendation> recommendations = dataExtractor.extract(recommendationResource, Recommendation[].class);
//        recommendations.forEach(recommendationRepository::save);
    }

}


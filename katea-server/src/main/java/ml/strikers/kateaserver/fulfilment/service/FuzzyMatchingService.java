package ml.strikers.kateaserver.fulfilment.service;

import ml.strikers.kateaserver.fulfilment.entity.Hotel;
import org.springframework.stereotype.Service;

/**
 * Matches facilities and venue type from request with what's in db
 */
@Service
public class FuzzyMatchingService {

    public Hotel matchFacilities(Hotel hotel) {
        return hotel;
    }

}


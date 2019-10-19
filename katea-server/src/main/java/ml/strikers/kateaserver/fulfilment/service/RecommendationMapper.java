package ml.strikers.kateaserver.fulfilment.service;

import ml.strikers.kateaserver.fulfilment.entity.QualityFacilities;
import ml.strikers.kateaserver.fulfilment.entity.Recommendation;
import org.springframework.stereotype.Component;

import java.util.List;

public class RecommendationMapper {

    private static final Double PRESENT = 1d;

    public static Recommendation map(List<String> qualities) {
        Recommendation recommendation = new Recommendation();
        qualities.forEach(quality -> QualityFacilities.valueOf(quality)
                .getSetter().accept(recommendation, PRESENT));
        return recommendation;
    }


}

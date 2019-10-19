package ml.strikers.kateaserver.fulfilment.service;

import lombok.extern.slf4j.Slf4j;
import ml.strikers.kateaserver.fulfilment.entity.QualityFacilities;
import ml.strikers.kateaserver.fulfilment.entity.Recommendation;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
public class RecommendationMapper {

    private static final Double PRESENT = 1d;
    private static final List<String> existingQualities = Arrays.stream(QualityFacilities.values())
            .map(Enum::name)
            .collect(Collectors.toList());

    public static Recommendation map(List<String> qualities) {
        Recommendation recommendation = new Recommendation();
        qualities.forEach(quality -> {
            if (existingQualities.contains(quality)) {
                QualityFacilities qualityFacilities = QualityFacilities.valueOf(quality);
                qualityFacilities
                        .getSetter().accept(recommendation, PRESENT);
            } else {
                log.warn("Unrecognized quality {}", quality);
            }
        });
        return recommendation;
    }


}

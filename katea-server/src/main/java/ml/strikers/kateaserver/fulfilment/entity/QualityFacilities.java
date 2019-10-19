package ml.strikers.kateaserver.fulfilment.entity;


import java.util.function.BiConsumer;

public enum QualityFacilities {

    clean(Recommendation::setAccommodationQualityCleanLiness),
    price(Recommendation::setAccommodationQualityPrice),
    location(Recommendation::setAccommodationQualityLocation),
    wifi(Recommendation::setAccommodationQualityWifi),
    staff(Recommendation::setAccommodationQualityStaff),
    quiet(Recommendation::setAccommodationQualityQuiet),
    restaurant(Recommendation::setAccommodationQualityBreakFast);

    private final BiConsumer<Recommendation, Double> setter;

    QualityFacilities(BiConsumer<Recommendation, Double> setter) {
        this.setter = setter;
    }

    public BiConsumer<Recommendation, Double> getSetter() {
        return setter;
    }
}


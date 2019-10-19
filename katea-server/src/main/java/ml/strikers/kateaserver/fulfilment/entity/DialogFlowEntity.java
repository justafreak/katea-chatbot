package ml.strikers.kateaserver.fulfilment.entity;

public enum DialogFlowEntity {

    COMPANIONS("companions"),
    ID("id"),
    QUALITY("quality"),
    RECOMMEND("recommend"),
    REVIEW_SENTIMENT("review-sentiment"),
    VENUE_ACCOMMODATION_TYPE("venue-accomodation-type"),
    MAP_SORT("map-sort");

    private String type;

    DialogFlowEntity(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

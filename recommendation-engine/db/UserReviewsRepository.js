const DB = require("./DB");
/**
 * A user review represents informations parsed from real scraped reviews or from
 * user rating.
 * {
    "session_id": 1,
    "hotel_id": 1,
    "like": 1,
    "travel_type_work": 1,
    "travel_type_honeymoon": 0,
    "travel_type_citybreak":0,
    "travel_type_holiday":0,
    "travel_companion_solo": 0,
    "travel_companion_kids": 1,
    "travel_companion_couple":1,
    "travel_companion_friends": 0,
    "accomodation_quality_cleanliness": 1,
    "accomodation_quality_breakfast": 1,
    "accomodation_quality_quiet": 0,
    "accomodation_quality_price": 0,
    "accomodation_quality_location":1,
    "accomodation_quality_wifi": 0,
    "accomodation_quality_staff": 0
}
 **/
const dummyUserReview = {
  session_id: 1,
  hotel_id: 1,
  like: 1,
  travel_type_work: 0,
  travel_type_honeymoon: 0,
  travel_type_citybreak: 0,
  travel_type_holiday: 0,
  travel_companion_solo: 0,
  travel_companion_kids: 0,
  travel_companion_couple: 0,
  travel_companion_friends: 0,
  accomodation_quality_cleanliness: 0,
  accomodation_quality_breakfast: 0,
  accomodation_quality_quiet: 0,
  accomodation_quality_price: 0,
  accomodation_quality_location: 0,
  accomodation_quality_wifi: 0,
  accomodation_quality_staff: 0
};

class UserReviewsRepository {
  constructor() {
    this.reviews = [];
  }
  async loadHotelsRatedByUser(sessionId) {
    if (!sessionId) {
      return [];
    }
    const db = DB.getConnection();
    const query = db.createQuery(UserReviewsRepository.ENTITY_KIND);

    query.filter("session_id", "=", sessionId);
    const [botRecommendation] = await db.runQuery(query);

    return botRecommendation;
  }
  async loadUserReviews(hotels) {
    if (this.reviews.length > 0) {
      return this.reviews;
    }
    const db = DB.getConnection();
    const query = db.createQuery(UserReviewsRepository.ENTITY_KIND);
    const [reviews] = await db.runQuery(query);
    this.reviews = reviews.filter(r => r.like === 1);

    return this.reviews;
  }
}

UserReviewsRepository.ENTITY_KIND = "Recommendation";

UserReviewsRepository.ALL_USER_FEATURES = [
  "travel_type_work",
  "travel_companion_solo",
  "travel_companion_kids",
  "travel_companion_couple",
  "travel_companion_friends",
  "accomodation_quality_cleanliness",
  "accomodation_quality_breakfast",
  "accomodation_quality_quiet",
  "accomodation_quality_price",
  "accomodation_quality_location",
  "accomodation_quality_wifi",
  "accomodation_quality_staff"
];
module.exports = UserReviewsRepository;

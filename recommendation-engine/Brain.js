const tf = require("@tensorflow/tfjs");
const LogisticRegression = require("./multinomial-logistic-regression/logistic-regression");
const {
  buildLabelsVector,
  labelsToObject,
  toVector,
  objectToVector
} = require("./AccomodationDataProcessor");

const _ = require("lodash");
const TrainingDataSplitter = require("./training/TrainingDataSplitter");
const AccomodationsRepository = require("./db/AccomodationsRepository");
const UserReviewsRepository = require("./db/UserReviewsRepository");

// const plot = require("node-remote-plot");
// const _ = require("lodash");

/**
 * We are running a multi-nomial logistic regression where:
 * The inputs are features specified by the user in natural language(this modules assumes that the features were extracted by the NLP layer in the format expected)
 * The outputs are the characteristics of the ideal hotel - amenenities it should have, the venue type(hotel apartment), the area where it should be in and the price range
 */
class Brain {
  async train() {
    const dataSplitter = new TrainingDataSplitter();
    const {
      trainingData,
      hotelFeaturesVector
    } = await this.getRawTrainingData();

    // Split the data by 80/20 rule.
    let { features, labels, testFeatures, testLabels } = dataSplitter.load(
      trainingData,
      {
        shuffle: true,
        splitTestPercent: 20,
        featureColumns: UserReviewsRepository.ALL_USER_FEATURES,
        labelColumns: hotelFeaturesVector
      }
    );

    this.regression = new LogisticRegression(features, labels, {
      learningRate: 0.5,
      iterations: 100,
      batchSize: 50
    });

    this.regression.train();
    const precision = this.regression.test(testFeatures, testLabels);
    console.log(
      `Finished training the system. Achieved precision is ${precision}`
    );
  }
  async getRawTrainingData() {
    const accomodationsRepo = new AccomodationsRepository();
    const hotels = await accomodationsRepo.loadAccomodations();

    const userReviewsRepo = new UserReviewsRepository();
    const reviews = await userReviewsRepo.loadUserReviews(hotels);

    const hotelFeaturesVector = buildLabelsVector(hotels);

    const trainingData = reviews
      .map(review => {
        const features = {
          ..._.pick(review, UserReviewsRepository.ALL_USER_FEATURES)
        };
        const hotel = hotels.find(h => h.id == review.hotel_id);
        if (!hotel) {
          return null;
        }
        // Transform the hotel into a vector representing it's characteristics
        const labels = labelsToObject(hotel, hotelFeaturesVector);

        return { ...features, ...labels };
      })
      .filter(f => !!f);

    return { trainingData, hotelFeaturesVector };
  }
  async suggest(features, howMany = 5) {
    const idealAccomodationTraitsTensor = this.regression.predict([
      objectToVector(features, UserReviewsRepository.ALL_USER_FEATURES, true)
    ]);

    const accomodationsRepo = new AccomodationsRepository();
    const hotels = await accomodationsRepo.loadAccomodations();
    const hotelFeaturesVector = buildLabelsVector(hotels);
    // Find the most similar hotels to this one
    return hotels
      .map(hotel => {
        const hotelTraitsTensor = tf.tensor([
          toVector(hotel, hotelFeaturesVector)
        ]);
        const distance = hotelTraitsTensor
          .mul(idealAccomodationTraitsTensor)
          .norm();

        return { hotel, distance };
      })
      .sort((h1, h2) => h1.distance - h2.distance)
      .slice(0, howMany)
      .map(r => r.hotel);
  }
  // @todo - Incorporate user feedback in the learning data set
  learnFromUser(hotelId, suggestedFeatures) {}
}

// INPUT: FEATURES_NAMES
// OUTPUT: HOTELS
module.exports = Brain;

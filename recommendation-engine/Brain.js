const tf = require("@tensorflow/tfjs");
const loadCSV = require("./load-csv");
const fs = require("fs");
const LogisticRegression = require("./multinomial-logistic-regression/logistic-regression");
const {
  buildLabelsVector,
  toVector,
  objectToVector
} = require("./AccomodationDataProcessor");
const hotels = JSON.parse(fs.readFileSync("../hotels.json", "utf-8"));
const labelsVector = buildLabelsVector(hotels);
// const plot = require("node-remote-plot");
// const _ = require("lodash");

/**
 * We are running a multi-nomial logistic regression where:
 * The inputs are features specified by the user in natural language(this modules assumes that the features were extracted by the NLP layer in the format expected)
 * The outputs are the characteristics of the ideal hotel - amenenities it should have, the venue type(hotel apartment), the area where it should be in and the price range
 */
class Brain {
  // @todo - Add the full feature list based on which recommendations are made
  constructor(accomodationIds) {
    this.accomodationIds = accomodationIds;
  }
  train() {
    // @todo - This data needs to come from a NOSQL DB
    let { features, labels, testFeatures, testLabels } = loadCSV(
      "./data/hotels.csv",
      {
        shuffle: true,
        splitTest: 50,
        dataColumns: Brain.ALL_FEATURES,
        labelColumns: labelsVector
      }
    );

    this.regression = new LogisticRegression(features, labels, {
      learningRate: 0.5,
      iterations: 100,
      batchSize: 50
    });

    this.regression.train();
  }
  suggest(features, howMany = 5) {
    const idealAccomodationTraitsTensor = this.regression.predict([
      objectToVector(features, Brain.ALL_FEATURES, true)
    ]);

    // Find the most similar hotels to this one
    return hotels
      .map(hotel => {
        const hotelTraitsTensor = tf.tensor([toVector(hotel, labelsVector)]);
        const distance = tf.losses.cosineDistance(
          idealAccomodationTraitsTensor,
          hotelTraitsTensor,
          1
        );

        return { hotel, distance };
      })
      .sort((h1, h2) => h1.distance - h2.distance)
      .slice(0, howMany)
      .map(r => r.hotel);
  }
  // @todo - Incorporate user feedback in the learning data set
  learnFromUser(hotelId, suggestedFeatures) {}
}

Brain.ALL_FEATURES = [
  "travel_type_work",
  "travel_type_honeymoon",
  "travel_type_citybreak",
  "travel_type_holiday",
  "travel_type_backpack",
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

// INPUT: FEATURES_NAMES
// OUTPUT: HOTELS
module.exports = Brain;

const loadCSV = require("./load-csv");
const LogisticRegression = require("./multinomial-logistic-regression/logistic-regression");
// const plot = require("node-remote-plot");
// const _ = require("lodash");

/**
 * We are running a multi-nomial logistic regression where:
 * The inputs are features specified by the user is natural language(this modules assumes that the features were extracted by the NLP layer in the format expected)
 * The outputs are hotel labels, each accomodation unit being a class
 */
class Brain {
  // @todo - Add the full feature list based on which recommendations are made

  train() {
    // @todo - This data needs to come from a NOSQL DB
    let { features, labels, testFeatures, testLabels } = loadCSV(
      "./data/hotels.csv",
      {
        shuffle: true,
        splitTest: 50,
        dataColumns: Brain.FEATURE_NAMES,
        labelColumns: ["idx"]
      }
    );

    this.regression = new LogisticRegression(features, labels, {
      learningRate: 0.5,
      iterations: 100,
      batchSize: 50
    });

    this.regression.train();
  }
  suggest(features) {
    return this.regression.predict([features]);
  }
  // @todo - Incorporate user feedback in the learning data set
  learnFromUser(hotelId, suggestedFeatures) {}
}
Brain.FEATURE_NAMES = [
  "accomodation_quality_wifi",
  "accomodation_quality_staff",
  "accomodation_quality_location",
  "accomodation_quality_price",
  "accomodation_quality_quiet",
  "accomodation_quality_breakfast",
  "accomodation_quality_cleanliness"
];
module.exports = Brain;

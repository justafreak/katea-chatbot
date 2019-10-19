const _ = require("lodash");
const shuffleSeed = require("shuffle-seed");

class TrainingDataSplitter {
  load(
    data,
    options = {
      featureColumns: [],
      labelColumns: [],
      shuffle: true,
      splitTestPercent: 20
    }
  ) {
    const { featureColumns, labelColumns, shuffle, splitTestPercent } = options;
    let features = data.map(d => _.toArray(_.pick(d, featureColumns)));
    let labels = data.map(d => _.toArray(_.pick(d, labelColumns)));

    if (shuffle) {
      features = shuffleSeed.shuffle(features, "seed");
      labels = shuffleSeed.shuffle(labels, "seed");
    }

    if (splitTestPercent) {
      const testRecordsCount = Math.floor(
        features.length * (splitTestPercent / 100)
      );

      return {
        features: features.slice(testRecordsCount),
        labels: labels.slice(testRecordsCount),
        testFeatures: features.slice(0, testRecordsCount),
        testLabels: labels.slice(0, testRecordsCount)
      };
    } else {
      return { features, labels };
    }
  }
}

module.exports = TrainingDataSplitter;

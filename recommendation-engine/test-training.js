const brainjs = require("brain.js");
const _ = require("lodash");

const net = new brainjs.NeuralNetwork({ hiddenLayers: [3] });
const hotels = _.range(0, 700);
const featureTemplate = {
  accomodation_quality_wifi: 0,
  accomodation_quality_staff: 0,
  accomodation_quality_location: 0,
  accomodation_quality_price: 0,
  accomodation_quality_quiet: 0,
  accomodation_quality_breakfast: 0,
  accomodation_quality_cleanliness: 0
};
const features = _.range(0, 700).map(i => {
  if (i < 100) {
    return { ...featureTemplate, accomodation_quality_wifi: 1 };
  }
  if (i >= 100 && i < 200) {
    return { ...featureTemplate, accomodation_quality_staff: 1 };
  }
  if (i >= 200 && i < 300) {
    return { ...featureTemplate, accomodation_quality_location: 1 };
  }
  if (i >= 300 && i < 400) {
    return { ...featureTemplate, accomodation_quality_price: 1 };
  }
  if (i >= 400 && i < 500) {
    return { ...featureTemplate, accomodation_quality_quiet: 1 };
  }
  if (i >= 500 && i < 600) {
    return { ...featureTemplate, accomodation_quality_breakfast: 1 };
  }
  if (i >= 600 && i <= 700) {
    return { ...featureTemplate, accomodation_quality_cleanliness: 1 };
  }
});
const output = _.range(0, 700).map(i => {
  const k = hotels[i];
  return { [k]: 1 };
});
const trainingData = features.map((f, i) => ({
  input: f,
  output: output[i]
}));
net.train(trainingData);

const result = net.run({
  accomodation_quality_wifi: 1
});
console.log(result);
const bestSuggestions = Object.keys(result)
  .sort((k1, k2) => {
    return result[k1] - result[k2];
  })
  .slice(0, 4)
  .reduce((obj, k) => {
    obj[k] = result[k];

    return obj;
  }, {});

console.log(bestSuggestions);

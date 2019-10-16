require("@tensorflow/tfjs-node");
const loadCSV = require("../load-csv");
const LogisticRegression = require("./logistic-regression");
const plot = require("node-remote-plot");
const _ = require("lodash");

let { features, labels, testFeatures, testLabels } = loadCSV(
  "../data/hotels.csv",
  {
    shuffle: true,
    splitTest: 50,
    dataColumns: ["horsepower", "displacement", "weight"],
    labelColumns: ["mpg"],
    converters: {
      mpg: value => {
        const mpg = parseFloat(value);

        if (mpg < 15) {
          return [1, 0, 0];
        } else if (mpg < 30) {
          return [0, 1, 0];
        } else {
          return [0, 0, 1];
        }
      }
    }
  }
);

const regression = new LogisticRegression(features, _.flatMap(labels), {
  learningRate: 0.5,
  iterations: 100,
  batchSize: 50
});

regression.train();
console.log(regression.test(testFeatures, _.flatMap(testLabels)));
// plot({
//   x: regression.costHistory.reverse(),
//   xLabel: "Iteration",
//   yLabel: "Cost"
// });
// const per = regression.test(testFeatures, testLabels);
// console.log(per);

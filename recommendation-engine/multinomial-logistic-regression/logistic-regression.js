const tf = require("@tensorflow/tfjs");
const _ = require("lodash");

class LogisticRegression {
  constructor(features, labels, options) {
    this.features = this.processFeatures(features);
    this.labels = tf.tensor(labels);
    this.costHistory = [];
    this.options = Object.assign(
      {
        learningRate: 0.1,
        iterations: 1000,
        batchSize: 3,
        decisionBoundary: 0.5
      },
      options || {}
    );
    this.weights = tf.zeros([this.features.shape[1], this.labels.shape[1]]);
  }
  gradientDescent(features, labels) {
    const currentGuesses = features.matMul(this.weights).softmax();
    const differences = currentGuesses.sub(labels);
    const slopes = features
      .transpose()
      .matMul(differences)
      .div(features.shape[0]);

    this.weights = this.weights.sub(slopes.mul(this.options.learningRate));
  }
  predict(observations) {
    observations = this.processFeatures(observations);
    return observations.matMul(this.weights).softmax();
  }
  train() {
    const totalBatches = Math.floor(
      this.features.shape[0] / this.options.batchSize
    );
    for (let i = 0; i < this.options.iterations; i++) {
      for (let batch = 0; batch < totalBatches; batch++) {
        this.gradientDescent(
          this.features.slice(
            [batch * this.options.batchSize, 0],
            [this.options.batchSize, -1]
          ),
          this.labels.slice(
            [batch * this.options.batchSize, 0],
            [this.options.batchSize, -1]
          )
        );
      }
      this.recordCost();
      this.updateLearningRate();
    }
  }
  test(testFeatures, testLabels) {
    const predictions = this.predict(testFeatures).argMax(1);
    const labels = tf.tensor(testLabels).argMax(1);

    const incorrect = predictions
      .notEqual(labels)
      .sum()
      .get();

    return (predictions.shape[0] - incorrect) / predictions.shape[0];
  }
  processFeatures(f) {
    let features = tf.tensor(f);
    if (this.mean && this.variance) {
      features = features.sub(this.mean).div(this.variance.pow(0.5));
    } else {
      features = this.standardize(features);
    }
    return tf.ones([features.shape[0], 1]).concat(features, 1);
  }
  standardize(features) {
    const { mean, variance } = tf.moments(features, 0);
    const filler = variance
      .cast("bool")
      .logicalNot()
      .cast("float32");

    this.mean = mean;
    this.variance = variance.add(filler);

    return features.sub(mean).div(this.variance.pow(0.5));
  }

  recordCost() {
    const guesses = this.features.matMul(this.weights).softmax();
    const term1 = this.labels.transpose().matMul(guesses.log());
    const term2 = this.labels
      .mul(-1)
      .add(1)
      .transpose()
      .matMul(
        guesses
          .mul(-1)
          .add(1)
          .log()
      );

    const cost = term1
      .add(term2)
      .div(this.features.shape[0])
      .mul(-1)
      .get(0, 0);
    this.costHistory.unshift(cost);
  }

  updateLearningRate() {
    if (this.costHistory.length < 2) {
      return;
    }

    const last = this.costHistory[0];
    const secondLast = this.costHistory[1];

    if (last > secondLast) {
      this.options.learningRate /= 2;
    } else {
      this.options.learningRate *= 1.05;
    }
  }
}

module.exports = LogisticRegression;

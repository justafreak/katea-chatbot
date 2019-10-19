const fs = require("fs");
const _ = require("lodash");
const jenks = require("./statistics/jenks");
const stringify = require("csv-stringify/lib/sync");

const toConstantWord = f =>
  _.words(f.toLowerCase())
    .join("_")
    .toLowerCase();

const toRangeVector = numericalValues => jenks(numericalValues, 3);

function getAccomodationLabels(h) {
  const hotelLabels = h.facilities.reduce((facilities, f) => {
    const k = toConstantWord(f);
    if (k) {
      facilities[k] = 1;
    }
    return facilities;
  }, {});
  if (h.venueType) {
    hotelLabels[toConstantWord(h.venueType)] = 1;
  }
  if (h.zone) {
    hotelLabels[toConstantWord(h.zone)] = 1;
  }
  return hotelLabels;
}

const createRandomFeatures = featureNames => {
  return featureNames.reduce((featureObj, f) => {
    featureObj[f] = Math.random();
    return featureObj;
  }, {});
};

/**
 * Builds the full label vector from the dataset
 */
const buildLabelsVector = hotels => {
  const labelsObj = hotels.reduce(
    (acc, h) => ({
      ...acc,
      ...getAccomodationLabels(h)
    }),
    {}
  );

  return Object.keys(labelsObj).sort((k1, k2) => k1.localeCompare(k2));
};
const objectToVector = (obj, labelSpace, populateWithValue = false) => {
  const vector = new Array(labelSpace.length).fill(0);
  Object.keys(obj).forEach(l => {
    const idx = labelSpace.findIndex(label => label === l);
    if (idx !== -1) {
      vector[l] = populateWithValue ? obj[l] : 1;
    }
  });

  return vector;
};

/**
 *
 * Gets an hotel as input and transforms it to a label vector
 */
const toVector = (accomodation, labelSpace) => {
  return objectToVector(
    getAccomodationLabels(accomodation, labelSpace),
    labelSpace
  );
};

const labelsToObject = (accomodation, labelSpace) => {
  const labelsObj = labelSpace.reduce((acc, l) => {
    acc[l] = 0;

    return acc;
  }, {});

  return { ...labelsObj, ...getAccomodationLabels(accomodation, labelSpace) };
};

const createTestData = (hotels, featureNames) => {
  const labelsVectorDimensions = buildLabelsVector(hotels);
  const hotelData = hotels.map((hotel, idx) => {
    const features = createRandomFeatures(featureNames);
    const labels = toVector(hotel, labelsVectorDimensions).reduce(
      (acc, val, i) => {
        acc[labelsVectorDimensions[i]] = val;

        return acc;
      },
      {}
    );

    return {
      id: hotel.id,
      ...features,
      ...labels
    };
  });

  const csvContent = stringify(hotelData, {
    header: true,
    columns: Object.keys(hotelData[0])
  });

  fs.writeFileSync("./data/hotels.csv", csvContent, { encoding: "utf-8" });
};

module.exports = {
  createTestData,
  toVector,
  objectToVector,
  labelsToObject,
  buildLabelsVector
};

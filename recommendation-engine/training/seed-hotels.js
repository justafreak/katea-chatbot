const fs = require("fs");
const stringify = require("csv-stringify/lib/sync");

const hotels = JSON.parse(fs.readFileSync("../../hotels.json", "utf-8"));
const features = {
  accomodation_quality_wifi: 0,
  accomodation_quality_staff: 0,
  accomodation_quality_location: 0,
  accomodation_quality_price: 0,
  accomodation_quality_quiet: 0,
  accomodation_quality_breakfast: 0,
  accomodation_quality_cleanliness: 0
};
const createRandomFeatures = features => {
  return Object.keys(features).reduce((featureObj, f) => {
    featureObj[f] = Math.random();
    return featureObj;
  }, {});
};
const hotelData = hotels.map((hotel, idx) => {
  return {
    id: hotel.id,
    idx,
    ...createRandomFeatures(features)
  };
});

const csvContent = stringify(hotelData, {
  header: true,
  columns: Object.keys(hotelData[0])
});

fs.writeFileSync("../data/hotels.csv", csvContent, { encoding: "utf-8" });

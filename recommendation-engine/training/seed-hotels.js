const fs = require("fs");
const stringify = require("csv-stringify/lib/sync");
const Brain = require("../Brain");

const hotels = JSON.parse(fs.readFileSync("../hotels.json", "utf-8"));

const createRandomFeatures = () => {
  return Brain.FEATURE_NAMES.reduce((featureObj, f) => {
    featureObj[f] = Math.random();
    return featureObj;
  }, {});
};
const hotelData = hotels.slice(0, 100).map((hotel, idx) => {
  return {
    id: hotel.id,
    idx,
    ...createRandomFeatures()
  };
});

const csvContent = stringify(hotelData, {
  header: true,
  columns: Object.keys(hotelData[0])
});

fs.writeFileSync("./data/hotels.csv", csvContent, { encoding: "utf-8" });

const fs = require("fs");
const loadCSV = require("../load-csv");
const Brain = require("../Brain");

class Accomodations {
  findByIdx(idx) {
    const hotels = JSON.parse(fs.readFileSync("../hotels.json", "utf-8"));

    let { features } = loadCSV(__dirname + "/../data/hotels.csv", {
      shuffle: false,
      splitTest: 0,
      dataColumns: ["id", "idx"],
      labelColumns: [""]
    });

    const [id] = features.find(f => f[1] === idx);
    if (id) {
      return hotels.find(hotel => hotel.id === id);
    }
  }
}

module.exports = Accomodations;

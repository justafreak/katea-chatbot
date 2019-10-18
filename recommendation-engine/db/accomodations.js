const fs = require("fs");
const path = require("path");
const util = require("util");
const loadCSV = require("../load-csv");
const Brain = require("../Brain");
const { Datastore } = require("@google-cloud/datastore");
const kind = "Hotels";
// Creates a client
console.log(path.join(__dirname, "account.json"));
const datastore = new Datastore({
  keyFilename: path.join(__dirname, "account.json")
});

// (async function() {
//   const data = await datastore.get(
//     datastore.key(["Recommendation", 5479787225153536])
//   );
//   // const [hotels] = await datastore.runQuery(q, {});
//   console.log("Entities", data);
// })();

const hotels = JSON.parse(fs.readFileSync("../hotels.json", "utf-8"));

let { features } = loadCSV(__dirname + "/../data/hotels.csv", {
  shuffle: false,
  splitTest: 0,
  dataColumns: ["id", "idx"],
  labelColumns: [""]
});

class Accomodations {
  findByIdx(idx) {
    const [id] = features.find(f => f[1] === idx);
    if (id) {
      return hotels.find(hotel => hotel.id === id);
    }
  }
  getAccomodationIds() {
    return hotels.map(h => h.id);
  }
}

module.exports = Accomodations;

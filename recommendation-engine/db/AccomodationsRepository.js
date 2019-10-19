const fs = require("fs");
const path = require("path");
const DB = require("./DB");

const kind = "Hotels";

class AccomodationsRepository {
  findByIdx(idx) {
    const [id] = features.find(f => f[1] === idx);
    if (id) {
      return hotels.find(hotel => hotel.id === id);
    }
  }
  async loadAccomodations() {
    const hotels = JSON.parse(
      fs.readFileSync(
        path.join(__dirname, "..", "data", "hotels.json"),
        "utf-8"
      )
    );
    // const db = DB.getConnection();
    // const query = datastore.createQuery('Hotels');
    // const [hotels] = await datastore.runQuery(query);

    return hotels;
  }
}

module.exports = AccomodationsRepository;

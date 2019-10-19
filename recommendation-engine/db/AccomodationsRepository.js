const fs = require("fs");
const path = require("path");
const DB = require("./DB");

class AccomodationsRepository {
  constructor() {
    this.hotels = [];
  }
  async loadAccomodations() {
    if (this.hotels.length > 0) {
      return this.hotels;
    }

    const db = DB.getConnection();
    const query = db.createQuery(AccomodationsRepository.ENTITY_KIND);
    const [hotels] = await db.runQuery(query);

    this.hotels = hotels.map(h => {
      h.facilities = JSON.parse(h.facilities);

      return h;
    });

    return this.hotels;
  }
}
AccomodationsRepository.ENTITY_KIND = "Hotels";

module.exports = AccomodationsRepository;

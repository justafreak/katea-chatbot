const fs = require("fs");
const path = require("path");
const DB = require("./DB");

class AccomodationsRepository {
  async loadAccomodations() {
    const db = DB.getConnection();
    const query = db.createQuery(AccomodationsRepository.ENTITY_KIND);
    const [hotels] = await db.runQuery(query);

    return hotels.map(h => {
      h.facilities = JSON.parse(h.facilities);

      return h;
    });
  }
}
AccomodationsRepository.ENTITY_KIND = "Hotels";

module.exports = AccomodationsRepository;

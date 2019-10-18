const fs = require("fs");
const Brain = require("../Brain");
const { createTestData } = require("../AccomodationDataProcessor");

const hotels = JSON.parse(fs.readFileSync("../hotels.json", "utf-8"));

createTestData(hotels, Brain.ALL_FEATURES);

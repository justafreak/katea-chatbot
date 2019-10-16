require("@tensorflow/tfjs-node");
const express = require("express");
const bodyParser = require("body-parser");
const cors = require("cors");
const Brain = require("./Brain");
const Accomodations = require("./db/accomodations");

const app = express();
const port = 3000;

const recEngine = new Brain();
recEngine.train();
const accomodationsDB = new Accomodations();

app.use(cors());
app.use(bodyParser.json());

app.post("/suggestions", (req, res) => {
  const { features } = req.body;
  // @todo - This prediction point is for testing purposes only
  const defaultPredictionPoint = {
    accomodation_quality_wifi: 0.5,
    accomodation_quality_staff: 0.5,
    accomodation_quality_location: 0.5,
    accomodation_quality_price: 0.5,
    accomodation_quality_quiet: 0.5,
    accomodation_quality_breakfast: 0.5,
    accomodation_quality_cleanliness: 0.5
  };
  const bestHotelMatches = recEngine.suggest(
    features || Object.values(defaultPredictionPoint)
  );
  const bestMatch = bestHotelMatches.get(0);
  const accomodation = accomodationsDB.findByIdx(bestMatch);
  res.json([accomodation]);
});

app.post("/learn", (req, res) => {
  const hotelId = Math.floor(Math.random() * Math.floor(100));
  recEngine.learnFromUser(hotelId, {
    accomodation_quality_wifi: 0.5,
    accomodation_quality_staff: 0.5,
    accomodation_quality_location: 0.5,
    accomodation_quality_price: 0.5,
    accomodation_quality_quiet: 0.5,
    accomodation_quality_breakfast: 0.5,
    accomodation_quality_cleanliness: 0.5
  });
});

app.listen(port, () =>
  console.log(`Recommendation Engine listening on port ${port}!`)
);

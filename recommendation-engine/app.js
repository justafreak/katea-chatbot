require("@tensorflow/tfjs-node");
const express = require("express");
const bodyParser = require("body-parser");
const cors = require("cors");
const morgan = require("morgan");
const Brain = require("./Brain");

const app = express();
const port = 3000;

const recEngine = new Brain();

app.use(cors());
app.use(bodyParser.json());
app.use(morgan("common"));

app.get("/train", (req, res) => {
  recEngine.train();
});

app.post("/suggestions", async (req, res) => {
  console.log(`Processing request with params ${JSON.stringify(req.body)}`);
  const { session_id, sessionId } = req.body;

  const bestHotelMatches = await recEngine.suggest(
    req.body,
    session_id || sessionId
  );

  res.json(bestHotelMatches);
});

app.post("/train", async (req, res) => {
  recEngine.train();

  res.json({ ok: "ok" });
});

// Start the recommendation engine and then the web server
recEngine.train().then(() => {
  app.listen(port, () =>
    console.log(`Recommendation Engine listening on port ${port}!`)
  );
});

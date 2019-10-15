# hoopoe-chatbot project

## Project mission

Enable better, more custom tailored and meaningfull hotel recommendations to delight customers by discovering places and experiences that resonate with the user.

### What problem does it solve ?

Most of the hotel recommendations are done throught written reviews and word of mouth. However, this means that finding the perfect place for the next hangout with friends or romantic get-away requires a lot of manual research, by digging throught reviews and identifying the pros and cons of each.

Instead, companies providing services in the tourism industry could benefit a lot by providing to their customer "human" like guidance in order to quickly surface the best accomodations that match what their customers are looking for.

This process of match-making a customer to accomodations is a lot more than a simple website search for location/price/review score. For it to provide truly meaningfull suggestions it needs to take into account the type of trip being made, with whom, where and are the kind of things the customer values most when looking for a hotel.

### How we're solving the problem: personalizing recommendations with a human touch

The key for most bussines for being succesfull is **personalization** . Designing the product experience and making suggestions not for _a customer_ but for _the customer_.

We're bringing a human touch to accomodation matching process by building a chatbot UI that ask the user a few well calibrated questions and is ready to ingest and make sense of what the user is saying without resorting to clunky, inflexible forms/selection UIs.

These discovered semantics are then fed into a "Recommendation system" employing AI to
make the best suggestions that take into account criterias, fine lines expressed by the user.

The recommendation system is continously learning, developing a deeper understanding of what things mean and how to better match the suggested accomodations to what the user is asking.

It does this by asking the users to rate the recommendations that were received, improving each and every time and being more in line with what the user is looking for.

Given enought time and interaction experiences with humans the system can learn to make really good recommendations and it only gets better at it.

### High level architecture

The system is comprised of 4 modules and it's built on the following stack:

- Svelte compiler (FE)
- DialogFlow (NLP)
- Java (BE - fullfilment service)
- NodeJS (BE - recommender system)
- NoSQL D

![Chatbot image](./docs/Chatbot-HLD.png)

#### Modules

The modules of the system are:

- Chatbot UI - a custom built UI for the Chatbot enabling text dispay, typing indicators and display of image carousels.
- DialogFlow agent - a NLP enabled chat layer, responsible for figuring out what the user is saying, what are the entities being present in the text and how to respond next
- Recommender system - a system backed by AI capable of taking as input the entities detected by the NLP layer and making suggestions for hotels based on them. the Recommender system also employs re-inforcement learning by constantly asking for
  human feedback on the recommandations that it does.
- Database - a NoSQL database holding the knownledge base of hotels, conversation history
  and recommendations done.

#### UI

The UI is responsable for providing 2 types of message integrations with DialogFlow:

- text message - Displays a text message sent from the BE
- carousel message - Displays a carousel of images with clickable links and buttons.

Is build on Svelte because of:

- extremely small footprint - it's actually a compiler instead of a framework and it can be deployed and integrated into bussines websites without adding a big overhead
- extremely fast - One of the fastest frameworks out there.
- reactive - everything is reactive and it just updates = fewer bugs

#### Fullfillment service

The fullfilment service is responsable for taking the NLP parsed entities and:

- storing the conversion
- figuring out how to respond to the incoming data, eg by querying the Recommender system
- maintain chat session context state

It's implemented in Java.

#### Recommender service

It operates in 2 modes:

1. Suggestions - Takes parsed NLP entities as input and tries to match them to hotels
2. Learning - Gets user feedback on suggestion and makes adjustments to it's algorithm for improved recommendations

It maintains a Deep Neural Net capable of constantly learning and it's serialized and persisted in the NOSQL DB.

It's written in NodeJS.

## Code structure

All of the UI source code lives in `ui` folder and it's built using Rollup.

### Installing and launching the UI

`console cd ui npm i npm run dev`

A development server will start on http://localhost:5000 , along with live
reload and all the usual dev goodies.
![Chatbot image](./docs/chatbot.png)

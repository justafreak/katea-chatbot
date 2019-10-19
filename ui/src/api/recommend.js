import { detectIntent } from './intent.js';

const generateRequestBody = requestData => {
  const { id, userFeedback } = requestData;
  return `I want to ${userFeedback} hotel with id ${id}`;
};

export const makeRecommendation = async requestData => {
  const msg = generateRequestBody(requestData);
  detectIntent(msg);
};

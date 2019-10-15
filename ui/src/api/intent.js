const ACCESS_TOKEN =
  'ya29.c.Kl6bB5ES0tibjHyo81dL - AvYioEk - yappDuXCezYaM1ijGRvAm - gk9azCu_NZFLpSC - I20rlMxX44GuP0JmHZwy9VVxAiHRUfm7_za5fQnlJgh3g6tgdhsHTrf299C9l';

const url =
  'https://dialogflow.googleapis.com/v2beta1/projects/movie-vscsqu/agent/sessions/32d27739-8402-c8bc-7575-b65e240f8755:detectIntent';

export const detectIntent = async requestData => {
  const { text } = requestData;
  const body = {
    queryInput: {
      text: {
        text,
        languageCode: 'en'
      }
    },
    queryParams: {
      timeZone: 'Europe/Bucharest'
    }
  };

  const response = await fetch(url, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      charset: 'utf-8',
      Authorization: `Bearer ${ACCESS_TOKEN}`
    },
    body: JSON.stringify(body)
  });

  return await response.json();
};

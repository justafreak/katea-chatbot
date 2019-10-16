const ACCESS_TOKEN =
  'ya29.c.Kl6bB2AiMM16d4vE75HusgFrWD2E3I18_oYNGplWi3FnugeFIMNATWk6wBNyj7YPMDdMts7zxYD7aPREiAyTDOWY3Y0Lur67E-BejJM4FRPQpYIn-QC8DnrtB-cnkWhf';

const url =
  'https://dialogflow.googleapis.com/v2beta1/projects/strikers-chatboot/agent/sessions/1582ff86-0054-af5f-0a31-b85fdb8b71e8:detectIntent';

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
    method: 'POST',
    mode: 'cors',
    credentials: 'include',
    headers: {
      'Content-Type': 'application/json',
      charset: 'utf-8',
      Authorization: `Bearer ${ACCESS_TOKEN}`
    },
    body: JSON.stringify(body)
  });

  return await response.json();
};

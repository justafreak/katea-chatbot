const ACCESS_TOKEN =
    'ya29.c.Kl6bBzlQhRvTKn7ToAGUujabi2X2hpVXr_YuGhkMR_kdWU1XbOOHIROcwOc1zsj-nFISDhkI6aQmoyfU_76xek1ZD0rpnQh9oRc1Q2ine257hPmu_seozwY16BJvZrVn';

const url =
    'https://dialogflow.googleapis.com/v2beta1/projects/strikers-chatboot/agent/sessions/1582ff86-0054-af5f-0a31-b85fdb8b71e8:detectIntent';

export const detectIntent = async requestData => {
    const body = {
        queryInput: {
            text: {
                text: requestData,
                languageCode: 'en'
            }
        },
        queryParams: {
            timeZone: 'Europe/Bucharest'
        }
    };

    const headers = new Headers({
        "Content-Type": "application/json",
        charset: "utf-8",
        Authorization: `Bearer ${ACCESS_TOKEN}`
    })

    const response = await fetch(url, {
        method: 'POST',
        headers,
        body: JSON.stringify(body)
    });

    return await response.json();
};

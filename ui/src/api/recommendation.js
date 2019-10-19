import { get } from 'svelte/store';
import { sessionId } from '../stores/session.js';
import { RECOMMENDATION_PATH } from '../constants/paths';
import { entities } from '../stores/recommendation';
import { storeBotMsg } from '../stores/messages.js';
import { MSG_TYPE_TEXT } from '../constants/msgType.js';
import { switchTypingIndicator } from '../stores/chat';
import { waitUntil } from '../utils/utils';

const generateRequestBody = requestData => {
  const { id, userFeedback } = requestData;
  return { sessionId: get(sessionId), id, vote: userFeedback, parameters: get(entities) };
};

export const makeRecommendation = async requestData => {
  const body = generateRequestBody(requestData);

  const headers = new Headers({
    'Content-Type': 'application/json',
    charset: 'utf-8'
  });

  try {
    switchTypingIndicator(true);
    await waitUntil(1500);
    const response = await fetch(RECOMMENDATION_PATH, {
      method: 'POST',
      headers,
      body: JSON.stringify(body)
    });

    switchTypingIndicator(false);

    const resp = await response.json();
    const msgs = [].concat(resp.message);

    msgs.forEach(msg => {
      const msgType = msg.type;
      const type = msgType ? msgType.toUpperCase() : MSG_TYPE_TEXT;
      storeBotMsg(type, msg.reply);
    });

    if (resp.parameters) setEntities(resp.parameters);

    storeSessionId(resp.sessionId);
  } catch (e) {
    storeBotMsg(MSG_TYPE_TEXT, 'Woops');
  }
};

import { get } from 'svelte/store';
import { sessionId } from '../stores/session.js';
import { RECOMMENDATION_PATH } from '../constants/paths';
import { entities } from '../stores/recommendation';
import { storeBotMsg } from '../stores/messages.js';
import { MSG_TYPE_TEXT } from '../constants/msgType.js';
import { switchTypingIndicator } from '../stores/chat';
import { handleSuccess } from './apiClient';

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

    const response = fetch(RECOMMENDATION_PATH, {
      method: 'POST',
      headers,
      body: JSON.stringify(body)
    });

    await handleSuccess(response);
  } catch (e) {
    storeBotMsg(MSG_TYPE_TEXT, 'Woops');
  }
};

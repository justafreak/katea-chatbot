import { storeBotMsg } from '../stores/messages.js';
import { get } from 'svelte/store';
import { sessionId } from '../stores/session.js';
import { MSG_TYPE_TEXT } from '../constants/msgType.js';
import { INTENT_PATH } from '../constants/paths.js';
import { switchTypingIndicator } from '../stores/chat';
import { handleSuccess } from './apiClient';

export const detectIntent = async requestData => {
  const body = { message: requestData, sessionId: get(sessionId) };

  const headers = new Headers({
    'Content-Type': 'application/json',
    charset: 'utf-8'
  });

  try {
    switchTypingIndicator(true);

    const response = fetch(INTENT_PATH, {
      method: 'POST',
      headers,
      body: JSON.stringify(body)
    });

    await handleSuccess(response);
  } catch (e) {
    storeBotMsg(MSG_TYPE_TEXT, "Sorry I didn't get that last part. Can you please repeat it?");
  }
};

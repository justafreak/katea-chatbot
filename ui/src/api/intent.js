import { storeBotMsg } from '../stores/messages.js';
import { get } from 'svelte/store';
import { storeSessionId, sessionId } from '../stores/session.js';
import { MSG_TYPE_TEXT } from '../constants/msgType.js';
import { INTENT_PATH } from '../constants/paths.js';
import { switchTypingIndicator } from '../stores/chat';
import { generateId } from '../utils/utils';
const waitUntil = ms => new Promise(resolve => setTimeout(resolve, ms));

export const detectIntent = async requestData => {
  const body = { message: requestData, sessionId: get(sessionId), messageId: generateId() };

  const headers = new Headers({
    'Content-Type': 'application/json',
    charset: 'utf-8'
  });

  try {
    switchTypingIndicator(true);
    await waitUntil(1500);
    const response = await fetch(INTENT_PATH, {
      method: 'POST',
      headers,
      body: JSON.stringify(body)
    });

    const resp = await response.json();
    const msgs = resp.message;
    const msgType = msgs.type;
    const type = msgType ? msgType.toUpperCase() : MSG_TYPE_TEXT;
    switchTypingIndicator(false);

    if (Array.isArray(msgs)) {
      msgs.forEach(msg => storeBotMsg(type, msgs.reply));
    } else {
      storeBotMsg(type, msgs.reply);
    }

    storeSessionId(resp.sessionId);
  } catch (e) {
    storeBotMsg(MSG_TYPE_TEXT, 'Woops');
  }
};

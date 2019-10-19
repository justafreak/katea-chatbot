import { storeBotMsg } from '../stores/messages.js';
import { waitUntil } from '../utils/utils';
import { setEntities } from '../stores/recommendation';
import { storeSessionId } from '../stores/session.js';
import { switchTypingIndicator } from '../stores/chat';

export const handleSuccess = async response => {
  const promiseResp = await Promise.all([response, waitUntil(1500)]);
  const resp = await promiseResp[0].json();
  const msgs = [].concat(resp.message);

  switchTypingIndicator(false);

  msgs.forEach(msg => {
    const msgType = msg.type;
    const type = msgType ? msgType.toUpperCase() : MSG_TYPE_TEXT;
    storeBotMsg(type, msg.reply);
  });

  if (resp.parameters) setEntities(resp.parameters);

  storeSessionId(resp.sessionId);
};

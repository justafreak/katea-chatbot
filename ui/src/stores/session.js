import { writable } from 'svelte/store';

export const sessionId = writable(null);

export const storeSessionId = sessionId => sessionid.set(sessionId);

import { writable } from 'svelte/store';

export const sessionId = writable(null);

export const storeSessionId = id => sessionId.set(id);

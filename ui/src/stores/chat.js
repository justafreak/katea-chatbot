import { writable } from 'svelte/store';

export const showTypingIndicator = writable(false);

export const switchTypingIndicator = value => showTypingIndicator.set(value);

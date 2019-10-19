import { writable } from 'svelte/store';

export const entities = writable({});

export const setEntities = values => entities.set(values);

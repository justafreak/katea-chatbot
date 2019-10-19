export const generateId = () =>
  Math.random()
    .toString(36)
    .substr(2, 9);

export const waitUntil = ms => new Promise(resolve => setTimeout(resolve, ms));

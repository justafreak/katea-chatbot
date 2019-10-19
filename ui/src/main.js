import App from './App.svelte';

const app = new App({
  target: document.body,
  props: {
    botName: 'Katea Chatbot'
  }
});

export default app;

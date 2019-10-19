<script>
  import Header from './Header.svelte';
  import Chat from './Chat.svelte';
  import { messages } from '../stores/messages.js';

  export let botName;
</script>

<style>
  .app-container {
    background-image: linear-gradient(-225deg, #b4cccc 35%, #ffffff);
    height: 100%;
  }
</style>

<div class="app-container">
  <Header {botName} />
  <Chat messages={$messages} />
</div>

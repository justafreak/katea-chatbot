<script>
  import Header from './Header.svelte';
  import Chat from './Chat.svelte';
  import { messages } from '../stores/messages.js';
  // Props
  export let name = '';
</script>

<style>
  .app-container {
    background-image: linear-gradient(-225deg, #b4cccc 35%, #ffffff);
    height: 100%;
  }
</style>

<div class="app-container">
  <Header teamName="Katea" imageUrl="" />
  <Chat {name} messages={$messages} />
</div>

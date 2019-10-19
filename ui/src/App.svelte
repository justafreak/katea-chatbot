<script>
  import Launcher from './components/Launcher.svelte';
  import { messages } from './stores/messages.js';

  export let botName;
</script>

<style>

</style>

<Launcher {botName} />

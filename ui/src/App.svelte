<script>
  import Launcher from './components/Launcher.svelte';
  import { messages } from './stores/messages.js';
  export let name;
</script>

<style>

</style>

<Launcher {name} />

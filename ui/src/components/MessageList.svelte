<script>
  import { afterUpdate } from 'svelte';
  import Message from './Message.svelte';

  export let messages = [];
  export let onDelete = () => {};
  export let onSubmit = () => {};

  let msgListElement;
  afterUpdate(() => {
    msgListElement.scroll({
      top: msgListElement.scrollHeight,
      behavior: 'smooth'
    });
  });
</script>

<style>
  .sc-message-list {
    height: 80%;
    overflow-y: auto;
    background-color: transparent;
    background-size: 100%;
    padding: 40px 30%;
  }

  ::-webkit-scrollbar {
    width: 12px;
    background-color: #f5f5f5;
  }
  ::-webkit-scrollbar-thumb {
    border-radius: 10px;
    -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
    background-color: #555;
  }
  ::-webkit-scrollbar-track {
    -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
    border-radius: 10px;
    background-color: #f5f5f5;
  }
</style>

<div class="sc-message-list" bind:this={msgListElement}>
  {#each messages as message}
    <Message {message} {onDelete} {onSubmit} />
  {/each}
</div>

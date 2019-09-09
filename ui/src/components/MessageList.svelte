<script>
  import Message from "./Message.svelte";
  export let messages = [];
  export let onDelete = () => {};
  export let onSubmit = () => {};
</script>

<style>
  .sc-message-list {
    height: 80%;
    overflow-y: auto;
    background-color: transparent;
    background-size: 100%;
    padding: 40px 0px;
  }
</style>

<div class="sc-message-list">
  {#each messages as message}
    <Message {message} {onDelete} {onSubmit} />
  {/each}
</div>

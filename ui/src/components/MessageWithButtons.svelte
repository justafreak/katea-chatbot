<script>
  export let btnData = [];
  export let direction = 'column';
  export let id = '';
</script>

<style>
  .message__Btns {
    display: flex;
    flex-direction: column;
  }

  .message__Btn {
    font-size: 12px;
    background-color: #a1a3a7;
    color: #f1f3f2;
    border: none;
    margin: 0;
    cursor: pointer;
    font-weight: bold;
  }

  .message__Btn:hover {
    background-color: #585a56;
  }

  .message {
    box-shadow: 0 1px 10px 0 rgba(90, 60, 122, 0.22);
    background-color: #c2d2da;
    color: #585a56;
    margin: 0 10px;
  }
</style>

<div class="message">
  <slot />
  <div class="message__Btns">
    {#each btnData as btn}
      <button class="message__Btn" name={btn.name} on:click={btn.onClick} value={id}>
        {btn.text}
      </button>
    {/each}
  </div>
</div>

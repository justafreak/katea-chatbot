<script>
  // your script goes here
  export let count = 0;
  export let open = false;
</script>

<style>
  .sc-new-messsages-count {
    position: absolute;
    top: -3px;
    left: 41px;
    display: flex;
    justify-content: center;
    flex-direction: column;
    border-radius: 50%;
    width: 22px;
    height: 22px;
    background: #ff4646;
    color: white;
    text-align: center;
    margin: auto;
    font-size: 12px;
    font-weight: 500;
  }
</style>

{#if count !== 0 && !open}
  <div class="sc-new-messsages-count">{count}</div>
  ;
{/if}

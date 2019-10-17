<script>
  import { onMount, beforeUpdate } from 'svelte';

  export let value = 3;
  export let name = 'rate';
  export let length = 5;
  export let ratedesc = [];
  export let disabled = false;

  let arr = [];
  let rate = 0;
  let over = 0;
  $: if (value) {
    rate = convertValue(value);
    over = convertValue(value);
  }

  const convertValue = val => {
    if (val >= length) {
      val = length;
    } else if (val < 0) {
      val = 0;
    }
    return val;
  };
  const isFilled = index => {
    return index <= over;
  };
  const isEmpty = index => {
    return index > over || (!value && !over);
  };

  const createArray = () => {
    for (let i = 1; i <= length; i++) {
      arr.push(i);
    }
  };
  beforeUpdate(() => {
    if (arr.length === 0) {
      createArray();
    }
  });
  onMount(() => {
    value = convertValue(value);
    rate = convertValue(value);
    over = convertValue(value);
  });
</script>

<style>
  .icon {
    display: inline-block;
    width: 15px;
    height: 15px;
    stroke-width: 0;
    stroke: currentColor;
    fill: currentColor;
    vertical-align: middle;
    top: -2px;
    position: relative;
    margin: 0 5px;
  }
  .Rate {
    cursor: default;
  }
  .Rate__star {
    color: #89a8a5;
    display: inline-block;
    padding: 0 7px;
    text-decoration: none;
    cursor: pointer;
    background: transparent none;
    border: 0;
  }
  .Rate__star .icon {
    top: 0;
    vertical-align: middle;
  }
  .Rate__star.hover {
    color: #555;
  }
  .Rate__star.filled {
    color: #555;
  }
  .Rate__star:hover,
  .Rate__star:focus {
    text-decoration: none;
  }
  .Rate__view .desc {
    display: inline-block;
    vertical-align: middle;
    padding: 7px;
    font-size: 12px;
  }
</style>

{#if length > 0}
  <div class="Rate">
    <svg
      style="position: absolute; width: 0; height: 0;"
      width="0"
      height="0"
      version="1.1"
      xmlns="http://www.w3.org/2000/svg"
      xmlns:xlink="http://www.w3.org/1999/xlink">
      <defs>
        <symbol id="icon-star-full" viewBox="0 0 32 32">
          <title>star-full</title>
          <path
            d="M32 12.408l-11.056-1.607-4.944-10.018-4.944 10.018-11.056 1.607 8 7.798-1.889 11.011
            9.889-5.199 9.889 5.199-1.889-11.011 8-7.798z" />
        </symbol>
      </defs>
    </svg>
    {#each arr as n}
      <button
        type="button"
        key={n}
        class:hover={n <= over}
        class:filled={n <= rate || isFilled(n)}
        class={'Rate__star'}>
        <svg class="icon">
          <use xlink:href="#icon-star-full" />
        </svg>
      </button>
    {/each}
    <div class="Rate__view">
      {#if ratedesc.length > 0 && over > 0}
        <span class="desc">{ratedesc[over - 1]}</span>
      {/if}
    </div>
  </div>
{/if}

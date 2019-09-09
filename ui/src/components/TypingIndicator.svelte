<script>
  export let visible = false;
</script>

<style>
  .typing-spinner {
    display: inline-flex;
    align-items: center;
    vertical-align: bottom;
    opacity: 0;
    height: 1.35em;
    transition: opacity 0.1s;
    pointer-events: none;
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
  }

  .typing-spinner .ball {
    display: inline-block;
    width: 9px;
    height: 9px;
    margin-right: 5px;
    border-radius: 50%;
    background-color: rgb(238, 0, 118);
  }

  .typing-spinner .ball:nth-child(1) {
    opacity: 0.9;
    animation: typing-ball 0.9s linear infinite;
  }

  .typing-spinner .ball:nth-child(2) {
    opacity: 0.3;
    animation: typing-ball 0.9s 0.225s linear infinite;
  }

  .typing-spinner .ball:nth-child(3) {
    opacity: 0.6;
    animation: typing-ball 0.9s 0.45s linear infinite;
  }
  .visible {
    opacity: 1;
  }

  @keyframes typing-ball {
    0% {
      transform: translateY(0);
    }
    25% {
      transform: translateY(5px);
    }
    50% {
      transform: translateY(0);
    }
    75% {
      transform: translateY(-5px);
    }
    100% {
      transform: translateY(0);
    }
  }
</style>

<div class="typing-spinner" class:visible>
  <div class="ball" />
  <div class="ball" />
  <div class="ball" />
</div>

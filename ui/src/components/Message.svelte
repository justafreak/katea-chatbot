<script>
  import TextMessage from './TextMessage.svelte';
  import QuickReply from './QuickReply.svelte';
  import Carousel from './Carousel.svelte';
  import { MSG_TYPE_TEXT, MSG_TYPE_QUICK_REPLY, MSG_TYPE_CAROUSEL } from '../constants/msgType';

  export let message = {
    type: MSG_TYPE_TEXT
  };
  $: messageSenderType = message.author === 'me' ? 'sent' : 'received';
</script>

<style>
  .sc-message {
    width: 100%;
    margin: auto;
    padding-bottom: 10px;
    display: flex;
    box-sizing: border-box;
  }

  @keyframes bubble-fade-in {
    0% {
      transform: translateX(-20px);
      opacity: 0;
    }
    50% {
      transform: translateX(-20px);
      opacity: 0;
    }
    100% {
      transform: translateX(0);
      opacity: 1;
    }
  }

  @media (max-width: 450px) {
    .sc-message {
      width: 80%;
    }
  }
</style>

<div class="sc-message">

  {#if message.type === MSG_TYPE_TEXT}
    <TextMessage {message} isTyping={false} />
  {:else if message.type === MSG_TYPE_QUICK_REPLY}
    <QuickReply {message} />
  {:else if message.type === MSG_TYPE_CAROUSEL}
    <Carousel {message} />
  {:else}Other content{/if}
</div>

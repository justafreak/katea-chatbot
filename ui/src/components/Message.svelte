<script>
  import TextMessage from "./TextMessage.svelte";
  import QuickReply from "./QuickReply.svelte";

  export let message = {
    type: "text"
  };
  $: messageSenderType = message.author === "me" ? "sent" : "received";
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

  {#if message.type === 'text'}
    <TextMessage text={message.text} messageSenderType isTyping={false} />
  {:else if message.type === 'quickreply'}
    <QuickReply {message} />
  {:else}Other content{/if}
</div>

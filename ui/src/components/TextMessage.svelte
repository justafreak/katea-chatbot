<script>
  import TypingIndicator from "./TypingIndicator.svelte";
  import chatIconUrl from "../assets/icons/chat-icon.svg";
  export let text = "";
  export let messageSenderType = "";
  export let isTyping = false;
</script>

<style>
  .sc-message--content {
    display: flex;
    padding: 15px 20px;
    min-width: 42px;
    box-shadow: 0 1px 28px 0 rgba(90, 60, 122, 0.22);
    border-radius: 21px;
  }

  .sc-message--content.sent {
    justify-content: flex-end;
  }

  .sc-message--content.sent .sc-message--avatar {
    display: none;
  }

  .sc-message--avatar {
    background-repeat: no-repeat;
    background-size: 100%;
    background-position: center;
    min-width: 30px;
    min-height: 30px;
    border-radius: 50%;
    align-self: center;
    margin-right: 15px;
  }

  .sc-message--text {
    padding: 17px 20px;
    border-radius: 6px;
    font-weight: 300;
    font-size: 14px;
    line-height: 1.4;
    white-space: pre-wrap;
    -webkit-font-smoothing: subpixel-antialiased;
    position: relative;
  }

  .sc-message--text.sent {
    color: white;
    background-color: #4e8cff;
    max-width: calc(100% - 120px);
    word-wrap: break-word;
  }

  .sc-message--text.received {
    color: #263238;
    background-color: #f4f7f9;
    margin-right: 40px;
  }
</style>

<div class="sc-message--content">
  <div
    class="sc-message--avatar"
    style="background-image: url({chatIconUrl})" />
  <div class={`sc-message--text ${messageSenderType}`}>
    <TypingIndicator visible={isTyping} />
    {#if !isTyping}
      <span>
        {@html text}
      </span>
    {/if}
  </div>
</div>

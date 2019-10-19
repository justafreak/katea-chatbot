<script>
  import MessageList from './MessageList.svelte';
  import UserInput from './UserInput.svelte';

  export let messages = [];
</script>

<style>
  .sc-chat-window {
    height: calc(100% - 120px);
    max-height: 590px;
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    transition: 0.3s ease-in-out;
    border-radius: 10px;
    font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
  }

  @media (max-width: 450px) {
    .sc-chat-window {
      width: 100%;
      height: 100%;
      max-height: 100%;
      right: 0px;
      bottom: 0px;
      border-radius: 0px;
    }
    .sc-chat-window {
      transition: 0.1s ease-in-out;
    }
  }
</style>

<div class="sc-chat-window">
  <MessageList {messages} />
  <UserInput />
</div>

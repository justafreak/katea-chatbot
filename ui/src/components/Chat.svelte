<script>
  import Header from './Header.svelte';
  import MessageList from './MessageList.svelte';
  import UserInput from './UserInput.svelte';

  export let open = false;
  export let messages = [];
</script>

<style>
  .sc-chat-window {
    width: 370px;
    height: calc(100% - 120px);
    max-height: 590px;
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    transition: 0.3s ease-in-out;
    border-radius: 10px;
    font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
    position: absolute;
    bottom: 0;
    right: 0;
    background: #eff8f8;
  }
  .sc-chat-window.close {
    opacity: 0;
    visibility: hidden;
    bottom: 90px;
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
    .sc-chat-window.close {
      bottom: 0px;
    }
  }
</style>

<div class="sc-chat-window" class:close={!open}>
  <Header teamName="Katea" imageUrl="" />
  <MessageList {messages} />
  <UserInput {messages} />
</div>

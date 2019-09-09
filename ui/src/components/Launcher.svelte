<script>
  import Chat from "./Chat.svelte";
  import MessageCount from "./MessageCount.svelte";
  import { messages } from "../stores/messages.js";
  import { open } from "../stores/chat.js";
  import launcherIcon from "../assets/icons/logo-no-bg.svg";
  import launcherIconActive from "./../assets/icons/close.svg";
  // Props
  export let name = "";
  export let newMessagesCount = 0;
</script>

<style>
  .sc-launcher {
    width: 60px;
    height: 60px;
    background-color: #4e8cff;
    background-position: center;
    background-repeat: no-repeat;
    position: fixed;
    right: 25px;
    bottom: 25px;
    border-radius: 50%;
    box-shadow: none;
    transition: box-shadow 0.2s ease-in-out;
  }

  .sc-launcher:before {
    content: "";
    position: relative;
    display: block;
    width: 60px;
    height: 60px;
    border-radius: 50%;
    transition: box-shadow 0.2s ease-in-out;
  }

  .sc-launcher .sc-open-icon,
  .sc-launcher .sc-closed-icon {
    width: 60px;
    height: 60px;
    position: fixed;
    right: 25px;
    bottom: 25px;
    transition: opacity 100ms ease-in-out, transform 100ms ease-in-out;
  }

  .sc-launcher .sc-closed-icon {
    transition: opacity 100ms ease-in-out, transform 100ms ease-in-out;
    width: 60px;
    height: 60px;
  }

  .sc-launcher .sc-open-icon {
    padding: 20px;
    box-sizing: border-box;
    opacity: 0;
  }

  .sc-launcher.open .sc-open-icon {
    transform: rotate(-90deg);
    opacity: 1;
  }

  .sc-launcher.open .sc-closed-icon {
    transform: rotate(-90deg);
    opacity: 0;
  }

  .sc-launcher.open:before {
    box-shadow: 0px 0px 400px 250px rgba(148, 149, 150, 0.2);
  }

  .sc-launcher:hover {
    box-shadow: 0 0px 27px 1.5px rgba(0, 0, 0, 0.2);
  }
</style>

<div>
  <div
    class="sc-launcher"
    class:open={$open}
    on:click={() => ($open = $open ? false : true)}>
    <MessageCount count={newMessagesCount} open={$open} />
    <img
      class="sc-open-icon"
      src={launcherIconActive}
      alt="Launcher icon active" />
    <img class="sc-closed-icon" src={launcherIcon} alt="Launcher icon" />
  </div>
  <Chat {name} messages={$messages} open={$open} />
</div>

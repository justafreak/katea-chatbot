<script>
  import SendIcon from './SendIcon.svelte';
  import { messages, storeHumanMsg } from '../stores/messages.js';
  import { detectIntent } from '../api/intent';
  import { HUMAN } from '../constants/author';
  import { MSG_TYPE_TEXT } from '../constants/msgType';

  let active = false;
  let reply = '';
  export let placeholder = 'Free speech here';

  const submitText = ev => {
    ev.preventDefault();

    if (reply) {
      storeHumanMsg(MSG_TYPE_TEXT, reply);

      detectIntent(reply);
    }

    reply = '';
  };
  const handleKey = event => {
    if (event.keyCode === 13 && !event.shiftKey) {
      submitText(event);
    }
  };
</script>

<style>
  .sc-user-input {
    margin: 0px;
    position: relative;
    bottom: 0;
    display: flex;
    background-color: #f4f7f9;
    border-radius: 10px;
    transition: background-color 0.2s ease, box-shadow 0.2s ease;
    cursor: text;
    border: 2px solid #89a8a5;
  }

  .sc-user-input--text {
    width: 90%;
    resize: none;
    border: none;
    outline: none;
    border-bottom-left-radius: 10px;
    box-sizing: border-box;
    padding: 18px;
    font-size: 15px;
    font-weight: 400;
    line-height: 1.33;
    white-space: pre-wrap;
    word-wrap: break-word;
    color: #565867;
    -webkit-font-smoothing: antialiased;
    max-height: 200px;
    overflow: scroll;
    bottom: 0;
    overflow-x: hidden;
    overflow-y: auto;
  }

  .sc-user-input--text:empty:before {
    content: attr(placeholder);
    display: block; /* For Firefox */
    color: rgba(86, 88, 103, 0.3);
    outline: none;
  }

  .sc-user-input--buttons {
    width: 50px;
    position: absolute;
    right: 30px;
    height: 100%;
    display: flex;
  }

  .sc-user-input--button:first-of-type {
    width: 40px;
  }

  .sc-user-input--button {
    width: 30px;
    height: 60px;
    padding-left: 3px;
    padding-right: 3px;
    display: flex;
    flex-direction: column;
    justify-content: center;
  }

  .sc-user-input.active {
    box-shadow: none;
    background-color: white;
    box-shadow: 0px -5px 20px 0px #89a8a5;
  }

  .sc-user-input-container {
    padding: 20px 28%;
  }

  @media (max-width: 450px) {
    .sc-user-input-container {
      padding: 40px 0;
    }
  }
</style>

<div class="sc-user-input-container">
  <form class="sc-user-input" class:active>
    <div
      role="button"
      tabIndex="0"
      on:focus={e => {
        active = true;
      }}
      on:blur={e => {
        active = false;
      }}
      bind:textContent={reply}
      on:keydown={handleKey}
      contenteditable="true"
      {placeholder}
      class="sc-user-input--text" />
    <div class="sc-user-input--buttons">
      <div class="sc-user-input--button">
        <SendIcon onClick={submitText} isActive={active} />
      </div>
    </div>
  </form>
</div>

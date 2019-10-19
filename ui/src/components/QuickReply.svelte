<script>
  import { messages, storeHumanMsg } from '../stores/messages.js';
  import { HUMAN } from '../constants/author';
  import { MSG_TYPE_TEXT, MSG_TYPE_QUICK_REPLY } from '../constants/msgType';

  export let message = {
    type: MSG_TYPE_QUICK_REPLY,
    reply: []
  };
  const onSubmit = text => {
    storeHumanMsg(MSG_TYPE_TEXT, text);
  };
</script>

<style>
  .sc-message--quick a:visited,
  .sc-message--quick a:hover {
    background-color: #a1a3a7;
    color: #f1f3f2;
  }

  .sc-message--quick .sc-message__reply {
    display: inline-block;
    background-color: #a1a3a7;
    color: #f1f3f2;
    margin: 10px;
    border-radius: 16px;
    box-shadow: 0 1px 28px 0 rgba(90, 60, 122, 0.22);
    border-radius: 21px;
  }

  .quickreply {
    text-decoration: none;
    font-size: 12px;
    font-weight: bold;
    padding: 0 15px;
    line-height: 48px;
    min-width: 100px;
    cursor: pointer;
    height: auto;
    text-align: center;
    cursor: pointer;
  }
</style>

<ul class="sc-message--quick">
  {#each message.reply as reply}
    <li
      class="sc-message__reply"
      on:click={ev => {
        ev.preventDefault();
        onSubmit(reply);
      }}>
      <a class="quickreply" href="void(0)">{reply}</a>
    </li>
  {/each}
</ul>

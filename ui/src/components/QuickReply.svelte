<script>
  import { messages } from '../stores/messages.js';
  import { HUMAN } from '../constants/author';
  import { MSG_TYPE_TEXT, MSG_TYPE_QUICK_REPLY } from '../constants/msgType';

  export let message = {
    type: MSG_TYPE_QUICK_REPLY,
    replies: []
  };
  const onSubmit = text => {
    messages.set([
      ...$messages,
      {
        type: MSG_TYPE_TEXT,
        author: HUMAN,
        text
      }
    ]);
  };
</script>

<style>
  .sc-message--quick {
    color: white;
  }

  .sc-message--quick .sc-message__reply {
    display: inline-block;
    background-color: #ffffff;
    border: 1px solid #333333;
    margin: 10px;
    border-radius: 16px;
  }

  .quickreply {
    text-decoration: none;
    color: #333333;
    font-size: 12px;
    font-weight: bold;
    padding: 0 15px;
    line-height: 48px;
    min-width: 100px;
    cursor: pointer;
    height: auto;
    text-align: center;
  }

  .sc-message--quick.sent {
    color: white;
    background-color: #ffffff;
    border: 1px solid grey;
    max-width: calc(100% - 120px);
    word-wrap: break-word;
  }
</style>

<ul class="sc-message--quick">
  {#each message.replies as reply}
    <li class="sc-message__reply">
      <a
        class="quickreply"
        href="void(0)"
        on:click={ev => {
          ev.preventDefault();
          onSubmit(reply);
        }}>
        {reply}
      </a>
    </li>
  {/each}
</ul>

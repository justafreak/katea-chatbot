<script>
  import Carousel from '@beyonk/svelte-carousel';
  import CarouselItem from './CarouselItem.svelte';
  import MessageWithButtons from './MessageWithButtons.svelte';
  import { MSG_TYPE_CAROUSEL } from '../constants/msgType';
  import { USER_FEEDBACK_LIKE, USER_FEEDBACK_DISLIKE } from '../constants/userFeedbackType';
  import { makeRecommendation } from '../api/recommendation';

  export let message = {
    type: MSG_TYPE_QUICK_REPLY,
    reply: []
  };

  const handleUserFeedback = (userFeedback, event) => {
    const id = event.target.value;
    makeRecommendation({ userFeedback, id });
  };

  const btnData = [
    {
      text: 'Thumbs up',
      name: 'likeBtn',
      onClick: event => handleUserFeedback(USER_FEEDBACK_LIKE, event)
    },
    {
      text: 'Thumbs down',
      name: 'dislikeBtn',
      onClick: event => handleUserFeedback(USER_FEEDBACK_DISLIKE, event)
    }
  ];
</script>

<style>
  .kcb-carousel-container {
    display: flex;
    width: 100%;
  }
  .kcb-carousel-container :global(.carousel ul) {
    margin-top: 0;
  }
  .kcb-carousel-container :global(.carousel ul li) {
    background-color: #a1a3a7;
    cursor: pointer;
  }
  .kcb-carousel-container :global(.carousel ul li):hover {
    background-color: #585a56;
  }
  .kcb-carousel-container :global(button.right),
  .kcb-carousel-container :global(button.left) {
    display: none;
  }
</style>

<div class="kcb-carousel-container">
  <Carousel perPage={{ 1: 1, 451: 3 }} autoplay={10000}>
    {#each message.reply as carouselItem}
      <MessageWithButtons {btnData} direction={'column'} id={carouselItem.id}>
        <CarouselItem {carouselItem} />
      </MessageWithButtons>
    {/each}
  </Carousel>
</div>

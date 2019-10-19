<script>
  import Carousel from '@beyonk/svelte-carousel';
  import CarouselItem from './CarouselItem.svelte';
  import MessageWithButtons from './MessageWithButtons.svelte';
  import { MSG_TYPE_CAROUSEL } from '../constants/msgType';
  import { USER_FEEDBACK_LIKE, USER_FEEDBACK_DISLIKE } from '../constants/userFeedbackType';
  import { makeRecommendation } from '../api/recommend';

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
      text: 'YEAH',
      name: 'likeBtn',
      onClick: event => handleUserFeedback(USER_FEEDBACK_LIKE, event)
    },
    {
      text: 'MEEH',
      name: 'dislikeBtn',
      onClick: event => handleUserFeedback(USER_FEEDBACK_DISLIKE, event)
    }
  ];
</script>

<Carousel perPage={{ 1: 1, 451: 3 }} autoplay={3000}>
  {#each message.reply as carouselItem}
    <MessageWithButtons {btnData} direction={'column'} id={carouselItem.id}>
      <CarouselItem {carouselItem} />
    </MessageWithButtons>
  {/each}
</Carousel>

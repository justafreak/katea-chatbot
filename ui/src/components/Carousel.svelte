<script>
  import Carousel from '@beyonk/svelte-carousel';
  import { ChevronLeftIcon, ChevronRightIcon } from 'svelte-feather-icons';
  import CarouselItem from './CarouselItem.svelte';
  import MessageWithButtons from './MessageWithButtons.svelte';
  import { MSG_TYPE_CAROUSEL } from '../constants/msgType';

  export let message = {
    type: MSG_TYPE_QUICK_REPLY,
    replies: []
  };

  const btnData = [
    {
      text: 'Like',
      name: 'likeBtn',
      onClick: () => {}
    },
    {
      text: 'Dislike',
      name: 'dislikeBtn',
      onClick: () => {}
    }
  ];
</script>

<Carousel perPage={2} autoplay={1500}>
  {#each message.replies as carouselItem}
    <MessageWithButtons {btnData} direction={'column'}>
      <CarouselItem class="slide-content" {...carouselItem} />
    </MessageWithButtons>
  {/each}
</Carousel>

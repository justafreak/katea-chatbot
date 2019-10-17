<script>
  import Carousel from '@beyonk/svelte-carousel';
  import { ChevronLeftIcon, ChevronRightIcon } from 'svelte-feather-icons';
  import CarouselItem from './CarouselItem.svelte';
  import MessageWithButtons from './MessageWithButtons.svelte';
  import { MSG_TYPE_CAROUSEL } from '../constants/msgType';

  export let message = {
    type: MSG_TYPE_QUICK_REPLY,
    reply: []
  };

  const btnData = [
    {
      text: 'YEAH',
      name: 'likeBtn',
      onClick: () => {}
    },
    {
      text: 'MEEH',
      name: 'dislikeBtn',
      onClick: () => {}
    }
  ];
</script>

<Carousel perPage={3} autoplay={2000}>
  {#each message.reply as carouselItem}
    <MessageWithButtons {btnData} direction={'column'}>
      <CarouselItem class="slide-content" {...carouselItem} />
    </MessageWithButtons>
  {/each}
</Carousel>

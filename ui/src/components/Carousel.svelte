<script>
    import Carousel from '@beyonk/svelte-carousel';
    import { ChevronLeftIcon, ChevronRightIcon } from 'svelte-feather-icons';
    import CarouselItem from './CarouselItem.svelte';
    import MessageWithButtons from './MessageWithButtons.svelte';

    const carouselItems = [
        {
            src:
                'https://www.stlmag.com/downloads/291284/download/0219_Elmwood_0016.jpg?cb=05f56521ae049e15a8f3d244cafb3822&w=640',
            title: 'Slide 1',
            description:
                'Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.'
        },
        {
            src: 'https://media.timeout.com/images/105239239/image.jpg',
            title: 'Slide 2',
            description:
                'Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.'
        },
        {
            src: 'http://cdn.cnn.com/cnnnext/dam/assets/190710135245-12-waterfront-restaurants.jpg',
            title: 'Slide 3',
            description:
                'Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.'
        },
        {
            src:
                'https://static.standard.co.uk/s3fs-public/thumbnails/image/2019/03/21/14/sexy-fish-2103-pwf.jpg?width=1000&height=614&fit=bounds&format=pjpg&auto=webp&quality=70&crop=16:9,offset-y0.5',
            title: 'Slide 4',
            description:
                'Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.'
        }
    ];

    const btnData = [
        {
            text: 'Reserve',
            name: 'reserveBtn',
            onClick: () => {}
        },
        {
            text: 'Call',
            name: 'callBtn',
            onClick: () => {}
        },
        {
            text: 'Home Page',
            name: 'homePageBtn',
            onClick: () => {}
        }
    ];
</script>

<Carousel perPage={2} autoplay={1500}>
    {#each carouselItems as carouselItem}
        <MessageWithButtons {btnData} direction={'column'}>
            <CarouselItem class="slide-content" {...carouselItem} />
        </MessageWithButtons>
    {/each}
</Carousel>

<script>
  import Rate from './Rate.svelte';
  export let carouselItem = '';

  const formattedPrice = `${carouselItem.price.value} ${carouselItem.price.currency}`;

  const onViewRecommendation = () => {
    window.open(carouselItem.url);
  };
</script>

<style>
  .carouselItem__img {
    width: 100%;
    cursor: pointer;
  }

  .carouselItem__title {
    font-size: 14px;
    margin: 0 8px;
    text-align: center;
  }

  .carouselItem__price {
    font-size: 12px;
    margin: 8px;
  }

  .carouselItem .left,
  .carouselItem .right {
    display: none;
  }
</style>

<div class="carouselItem">
  <img
    src={carouselItem.imageUrl}
    alt=""
    class="carouselItem__img"
    on:click={ev => {
      ev.preventDefault();
      onViewRecommendation();
    }} />
  <h3 class="carouselItem__title">{carouselItem.name}</h3>
  <p class="carouselItem__price">{formattedPrice}</p>
  <Rate
    value={Math.round(carouselItem.rating / 2)}
    length={5}
    ratedesc={['Very bad', 'Bad', 'Normal', 'Good', 'Very good']}
    showCount={false} />
</div>

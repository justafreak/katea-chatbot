<script>
  import Rate from './Rate.svelte';
  export let carouselItem = '';

  const formattedPrice = `${carouselItem.price.value} ${carouselItem.price.currency}`;

  const onViewRecommendation = () => {
    window.open(carouselItem.url);
  };
</script>

<style>
  .kcb-carouselItem-img {
    width: 100%;
    cursor: pointer;
  }

  .kcb-carouselItem-title {
    font-size: 14px;
    margin: 0 8px;
    text-align: center;
    height: 34px;
  }

  .kcb-carouselItem-price {
    font-size: 12px;
    margin: 8px;
  }
</style>

<div class="carouselItem">
  <img
    src={carouselItem.imageUrl}
    alt=""
    class="kcb-carouselItem-img"
    on:click={ev => {
      ev.preventDefault();
      onViewRecommendation();
    }} />
  <h3 class="kcb-carouselItem-title">{carouselItem.name}</h3>
  <p class="kcb-carouselItem-price">{formattedPrice}</p>
  <Rate value={Math.round(carouselItem.rating / 2)} length={5} />
  <p class="kcb-carouselItem-price">Area: {carouselItem.zone}</p>
</div>

<script>
    export let src = '';
    export let title = '';
    export let description = '';
</script>

<style>
    .carouselItem {
        margin: 0 5px;
    }

    .carouselItem__img {
        width: 100%;
        height: 90px;
    }

    .carouselItem__title {
        font-size: 14px;
        margin: 0 8px;
    }

    .carouselItem__description {
        font-size: 12px;
        margin: 8px;
    }
</style>

<div class="carouselItem">
    <img {src} alt="" class="carouselItem__img" />
    <h3 class="carouselItem__title">{title}</h3>
    <p class="carouselItem__description">{description}</p>
</div>

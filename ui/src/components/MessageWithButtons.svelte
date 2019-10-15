<script>
    export let btnData = [];
    export let direction = 'column';
</script>

<style>
    .message__Btns {
        display: flex;
        flex-direction: column;
    }

    .message__Btn {
        background-color: rgb(255, 255, 255);
        font-size: 12px;
        color: rgb(23, 116, 202);
        border: none;
        margin: 0 5px;
    }

    .message {
        box-shadow: 0 1px 10px 0 rgba(90, 60, 122, 0.22);
    }
</style>

<div class="message">
    <slot />
    <div class="message__Btns">
        {#each btnData as btn}
            <button class="message__Btn" name={btn.name} on:click={btn.onClick}>{btn.text}</button>
        {/each}
    </div>
</div>

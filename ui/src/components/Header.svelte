<script>
  import AvatarIcon from './AvatarIcon.svelte';

  export let botName = '';
</script>

<style>
  .sc-header {
    min-height: 75px;
    border-top-left-radius: 9px;
    border-top-right-radius: 9px;
    color: white;
    padding: 10px;
    box-shadow: 0 1px 4px rgba(0, 0, 0, 0.2);
    position: relative;
    box-sizing: border-box;
    display: flex;
    font-size: 26px;
    font-weight: bold;
    color: #585a56;
    background-color: #c2d2da;
  }

  .sc-header--team-name {
    align-self: center;
    padding: 10px;
    flex: 1;
    user-select: none;
    cursor: pointer;
    border-radius: 5px;
  }

  @media (max-width: 450px) {
    .sc-header {
      border-radius: 0px;
    }
  }
</style>

<div class="sc-header">
  <AvatarIcon />
  <div class="sc-header--team-name">{botName}</div>
</div>

<script>
  import chatIconUrl from '../assets/icons/chat-icon.svg';

  export let teamName = '';
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
    font-size: 20px;
    color: #6b7e81;
  }

  .sc-header--team-name {
    align-self: center;
    padding: 10px;
    flex: 1;
    user-select: none;
    cursor: pointer;
    border-radius: 5px;
  }
  .sc-message--avatar {
    background-repeat: no-repeat;
    background-size: 100%;
    background-position: center;
    min-width: 40px;
    min-height: 40px;
    border-radius: 50%;
    align-self: center;
    margin-right: 15px;
  }

  @media (max-width: 450px) {
    .sc-header {
      border-radius: 0px;
    }
  }
</style>

<div class="sc-header">
  <div class="sc-message--avatar" style="background-image: url({chatIconUrl})" />
  <div class="sc-header--team-name">{teamName}</div>
</div>

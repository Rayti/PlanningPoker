<template>
  <div class="container">
    <div class="row justify-content-start" >
  <button type="button" class="btn btn-outline-secondary invite-btn col-8 align-start"
          data-bs-toggle="popover" title="Copy invite link" data-bs-placement="bottom"
          data-content-id="popover-27">
    Invite to room</button>
    </div>
  <div style="display: none;" id="popover-27">
    <div class="input-group mb-3">
      <span class="input-group-text" id="basic-addon1">Link</span>
      <input type="text" class="form-control"  aria-describedby="basic-addon1">
    </div>
  </div>
  <div class="room-table">
    <game-table ref="gameTableRef" :mainPlayerCard="this.mainPlayerCard" :isSelectionConfirmed="this.isSelectionConfirmed"
    />
    <main-player-space ref="mainPlayerSpaceRef" @clear="clearTableHandler" @selection="getSelection"/>
  </div>
    </div>
</template>

<script>
import MainPlayerSpace from "@/components/MainPlayerSpace";
import GameTable from "@/components/GameTable";
import $ from 'jquery';
import { Popover } from 'bootstrap/dist/js/bootstrap.esm.min.js'
import {WebService} from "@/services/WebService";

export default {
  name: "GameView",
  webService: new WebService(),
  components: {
    MainPlayerSpace,
    GameTable
  },
  data() {
    return {
      mainPlayerCard: '',
      isSelectionConfirmed: false
    }
  },
  mounted() {
    let popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));
    popoverTriggerList.forEach(popoverTriggerEl=> {
        const popoverId = popoverTriggerEl.attributes['data-content-id'];
        const contentEl = $(`#${popoverId.value}`).html();
      let opts = {
        content: contentEl,
        html: true,
        container: 'body',
        sanitize  : false,
        trigger: 'focus'
              }
        new Popover(popoverTriggerEl, opts);

    })
  },
  methods:{
    getSelection($event){
      this.mainPlayerCard = $event.selectedCard;
      this.isSelectionConfirmed = $event.isSelectionConfirmed;
    },

    clearTableHandler($event){
      this.$refs.gameTableRef.clearResult();
      this.mainPlayerCard = '';
      this.isSelectionConfirmed = false;

    }
  }
}
</script>

<style scoped>

.invite-btn{
  width: 10rem;
  margin-left: -8%;

}
.room {
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.room-table {
  align-items: center;
  display: flex;
  justify-content: center;
  max-width: 100vw;
}
</style>
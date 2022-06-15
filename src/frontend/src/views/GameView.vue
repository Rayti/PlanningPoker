<template>
  <div v-if="checkUser()" class="container">
    <div class="row justify-content-start" >
      <h2 v-if="!webSocketService.stompClient || !webSocketService.stompClient.connected">
      <!-- bardzo wazne to, inaczej trza zrobic -->
        WebSocket connection lost
      </h2>
      <!-- <button type="button" class="btn btn-outline-secondary invite-btn col-8 align-start"
              data-bs-toggle="popover" title="Copy invite link" data-bs-placement="bottom"
              data-content-id="popover-27">
        Invite to room
      </button> -->
    </div>
    <!-- <div style="display: none;" id="popover-27">
      <div class="input-group mb-3">
        <span class="input-group-text" id="basic-addon1">Room name:</span>
        <input type="text" class="form-control" aria-describedby="basic-addon1">
      </div>
    </div> -->
    <div class="room-table">
      <game-table ref="gameTableRef" :mainPlayerCard="this.mainPlayerCard" :isSelectionConfirmed="this.isSelectionConfirmed"/>
      <main-player-space ref="mainPlayerSpaceRef" @clear="clearTableHandler"/>
    </div>
  </div>
</template>

<script>
import MainPlayerSpace from "@/components/MainPlayerSpace";
import GameTable from "@/components/GameTable";
import { mapState } from 'vuex';

export default {
  name: "GameView",
  inject: ["webHttpService", "webSocketService"],
  components: {
    MainPlayerSpace,
    GameTable
  },
  data() {
    return {
      mainPlayerCard: "",
      isSelectionConfirmed: false
    }
  },
  computed: mapState([
      "mySelectedCard",
      "isSelectedCardConfirmed",
  ]),
  // mounted() {
  //   let popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));
  //   popoverTriggerList.forEach(popoverTriggerEl=> {
  //       const popoverId = popoverTriggerEl.attributes['data-content-id'];
  //       const contentEl = $(`#${popoverId.value}`).html();
  //     let opts = {
  //       content: contentEl,
  //       html: true,
  //       container: 'body',
  //       sanitize  : false,
  //       trigger: 'focus'
  //             }
  //       new Popover(popoverTriggerEl, opts);

  //   })
  // },
  methods: {
    getSelection($event) {
      // this.mainPlayerCard = $event.selectedCard;
      // this.isSelectionConfirmed = $event.isSelectionConfirmed;
      // if (this.isSelectionConfirmed && this.mainPlayerCard !== "") {
        
      // }
    },
    checkUser() {  
      return this.$route.params.roomName === this.$store.state.roomName; 
    },
    clearTableHandler() {
      // this.$refs.gameTableRef.clearResult(); // TO POPRAWIĆ!!!!
      // this.mainPlayerCard = "";
      
      // this.isSelectionConfirmed = false;
      this.$store.dispatch("setCardSelection", { selectedCard: "", confirmation: false });
    }
  }
}
</script>

<style scoped>

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
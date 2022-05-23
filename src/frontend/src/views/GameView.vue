<template>
  <div class="room-table">
    <game-table ref="gameTableRef" :mainPlayerCard="this.mainPlayerCard" :isSelectionConfirmed="this.isSelectionConfirmed"
    />
    <main-player-space ref="mainPlayerSpaceRef" @clear="clearTableHandler" @selection="getSelection"/>
  </div>

</template>

<script>
// import HeaderBar from "@/components/header-bar";
import MainPlayerSpace from "@/components/MainPlayerSpace";
import GameTable from "@/components/GameTable";
import {WebService} from "@/services/WebService";

export default {
  name: "GameView",
  webService: new WebService(),
  components: {
    // HeaderBar,
    MainPlayerSpace,
    GameTable
  },
  data() {
    return {
      mainPlayerCard: '',
      isSelectionConfirmed: false
    }
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
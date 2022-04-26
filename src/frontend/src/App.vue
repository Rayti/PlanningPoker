<template>
  <div class="room">
    <div id="nav">
        <header-bar></header-bar>
    </div>
    <!-- <router-view></router-view> -->
    <!-- <h1>Welcome to Planning Poker!</h1> -->
    <div class="room-table">
      <game-table :mainPlayerCard="this.mainPlayerCard" :isSelectionConfirmed="this.isSelectionConfirmed"/>
      <main-player-space @selection="getSelection"/>
    </div>
  </div>
</template>

<script>
import HeaderBar from "@/components/header-bar";
import MainPlayerSpace from './components/MainPlayerSpace.vue'
import GameTable from './components/GameTable.vue'

export default {
  name: 'App',
  components: {
    HeaderBar,
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
      }
  }
}
</script>

<style>
body, html {
  padding: 0;
  margin: 0;
}
body {
  background-color: rgb(255, 179, 179);
  width: 100%;
  height: 100%;
}
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
#nav {
  padding: 30px;
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

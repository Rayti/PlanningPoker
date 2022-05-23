<template>
  <div class="room">
    <div id="nav">
        <HeaderBar></HeaderBar>
    </div>
     <router-view></router-view>
  </div>
</template>

<script>
import HeaderBar from "@/components/HeaderBar";

import jQuery from "jquery";
import {WebService} from "@/services/WebService";
window.$ = jQuery;

export default {
  name: 'App',
  components: {
    HeaderBar,
  },
  data() {
        return {
            mainPlayerCard: '',
            isSelectionConfirmed: false,
            webService: new WebService()
        }
    },
  provide() {
    return {
      webService: this.webService
    };
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

<template>
  <div id="nav">
      <header-bar></header-bar>
  </div>
  <router-view></router-view>
  <h1>Welcome to Planning Poker!</h1>
  <DeckOfCards/>
</template>

<script>
import HeaderBar from "@/components/header-bar";
import DeckOfCards from './components/DeckOfCards.vue'

export default {
  name: 'App',
  components: {
    "header-bar": HeaderBar,
    DeckOfCards
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
</style>

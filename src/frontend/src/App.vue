<template>
  <div id="app">

    <div id="nav">
      <header-bar></header-bar>
    </div>
    <router-view></router-view>

  </div>


</template>

<script>
import HeaderBar from "@/components/header-bar";

export default {
  name: 'App',
  components: {
    "header-bar": HeaderBar
  }

}
</script>

<style >
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

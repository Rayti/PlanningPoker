<template>
  <div>
  <img class ="logo" src="../assets/planningpoker.png">
    <div class="btn-toolbar mb-3 input-toolbar" role="toolbar" aria-label="Toolbar with button groups">
      <div class="input-group">
        <div class="input-group-text" id="btnGroupAddon">Name</div>
        <input type="text" v-model="nickInput" class="form-control" placeholder="Enter your nick" aria-label="Input group example" aria-describedby="btnGroupAddon">
        <button type="button" v-on:click="goClick" class="btn btn-outline-success">Let's start</button>
      </div>

    </div>
  </div>
</template>

<script>

import {WebService} from "@/services/WebService";

const webService = new WebService();

export default {
  name: "HomeView",
  data() {
    return{
    nickInput: ""
  }
}
,
  methods: {
    goClick() {

      webService.addUser(this.nickInput).then((data) => {

        alert(data);
      });
    }
  }
};
</script>

<style scoped>
.input-toolbar{
  justify-content: center !important;
  z-index: 500;
  margin-top: 2.5%;
}
</style>
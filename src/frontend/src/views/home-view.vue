<template>
  <div>
  <img class ="logo" src="../assets/planningpoker.png">
  <div>
    <b-button-toolbar class ="input-toolbar" aria-label="Toolbar with button groups and input groups">
      <b-input-group prepend="Username" class="mt-3" >
        <b-form-input type="text" placeholder="Your Name" class="text-right" v-model="nickInput"></b-form-input>

        <b-input-group-append>
          <b-button v-on:click="goClick" variant="outline-success">Let's start</b-button>
        </b-input-group-append>
      </b-input-group>
    </b-button-toolbar>
  </div></div>
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

</style>
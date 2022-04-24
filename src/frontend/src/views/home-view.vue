<template>
  <div>
  <img class ="logo" src="../assets/planningpoker.png">
  <div>
    <b-button-toolbar class ="input-toolbar" aria-label="Toolbar with button groups and input groups">
      <b-input-group prepend="Username" class="mt-3" >
        <b-form-input type="text" placeholder="Your Name" class="text-right"></b-form-input>

        <b-input-group-append>
          <b-button variant="outline-success">Let's start</b-button>
        </b-input-group-append>
      </b-input-group>
    </b-button-toolbar>
  </div></div>
</template>

<script>
export default {
  name: "HomeView"
}
</script>

<style scoped>

</style>
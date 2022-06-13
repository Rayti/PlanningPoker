<template>
  <div>
    <nav class="navbar header">
      <div class="container-fluid">
        <a class="navbar-brand custom-brand" @click="navigate" >
<!--          <img src="/docs/5.1/assets/brand/bootstrap-logo.svg" alt="" width="30" height="24" class="d-inline-block align-text-top">-->
          Planning Poker
        </a>
        <button type="button" v-if="this.$store.state.sessionId !== ''" class="btn btn-outline-light mx-4" @click="logOut">Log out</button>
      </div>
    </nav>
<!--    <b-navbar class="header" toggleable="lg" type="dark" variant="info">-->
<!--      <b-navbar-brand href="#">Planning Poker</b-navbar-brand>-->
<!--      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>-->
<!--      <b-collapse id="nav-collapse" is-nav>-->
<!--&lt;!&ndash;        <b-navbar-nav>&ndash;&gt;-->
<!--&lt;!&ndash;          <b-nav-item href="#">Link</b-nav-item>&ndash;&gt;-->
<!--&lt;!&ndash;          <b-nav-item href="#" disabled>Disabled</b-nav-item>&ndash;&gt;-->
<!--&lt;!&ndash;        </b-navbar-nav>&ndash;&gt;-->
<!--      </b-collapse>-->
<!--    </b-navbar>-->
  </div>
</template>

<script>
export default {
  inject: ['webService'],
  methods: {
    navigate() {
      this.webService.disconnect();
      this.$router.push('/');
    },
    async logOut(){
      this.webService.logOut()
          .then(result => {
            if(result?.data?.success === true){
              alert("Successfully logged out!");
              this.$store.dispatch("cleanStore");
              this.$router.push('/');
            }
          })
    }
  }

}
</script>

<style scoped>
.header {
  position:fixed; /* fixing the position takes it out of html flow - knows
                   nothing about where to locate itself except by browser
                   coordinates */
  left:0;           /* top left corner should start at leftmost spot */
  top:0;            /* top left corner should start at topmost spot */
  width:100vw;      /* take up the full browser width */
  z-index:200;  /* high z index so other content scrolls underneath */
  height:70px;     /* define height for content */
  padding-left: 5%;
  background-color: #6B5343 !important;
  color: beige;
}
.custom-brand{
  color: aliceblue;
  font-size: x-large;
  cursor: pointer;
}
</style>
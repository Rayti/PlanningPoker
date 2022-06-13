<template>
  <div class="modal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Login panel</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" @click="closeModal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <p class="text-start">Nickname:</p>
          <div class="form-floating mb-3">
            <input  class="form-control" id="loginNickname" v-model="loginNicknameInput" aria-label="Floating label select example" placeholder="Enter your nick">
            <label for="loginNickname">Your nickname</label>
          </div>
          <p class="text-start">Password:</p>
          <div class="form-floating mb-3">
            <input type="password"  class="form-control" id="password" v-model="loginPasswordInput" aria-label="Floating label select example" placeholder="Enter your password">
            <label for="password">Your password</label>
          </div>
          <div>
            <p class="text-start text-center text-white p-3 rounded" :class="requestResponseClass()" v-if="requestResponseMsg">{{requestResponseMsg}}</p>
          </div>
        </div>
        <div  class="modal-footer" v-if="!loggedIn">
          <button type="button" class="btn btn-primary" @click="logIn">Log in</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "LoginModal",
  inject: ['webHttpService','webSocketService'],
  data(){
    return{
      loginNicknameInput: "",
      loginPasswordInput: "",
      requestResponseMsg: false,
      loggedIn: false
    }
  },
  methods: {
    closeModal(){
      this.$emit('close-modal-event');
    },
    async logIn(){
      this.webHttpService.logIn(this.loginNicknameInput, this.loginPasswordInput)
          .then(result => {
            let sessionId = result.data.sessionId;
            console.log(`SessionId: ${sessionId}`);
            if(sessionId !== ''){
              this.$store.dispatch("setSessionId", sessionId);
              this.$store.dispatch("setUserName", this.loginNicknameInput);
              this.requestResponseMsg = "Logged in successfully";
              this.loggedIn = true;
            }else{
              this.requestResponseMsg = "Could not log in";
              this.loggedIn = false;
            }
          })
    },
    requestResponseClass(){
      return this.loggedIn ? "bg-success" : "bg-warning";
    }
  }
}
</script>

<style scoped>
.modal {
  display: block;
}
</style>
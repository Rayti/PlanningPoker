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
            <p class="text-start text-center text-white p-3 rounded bg-warning" v-if="requestResponseMsg">{{requestResponseMsg}}</p>
          </div>
        </div>
        <div  class="modal-footer">
          <button type="button" class="btn btn-primary" @click="login">Log in</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "LoginModal",
  inject: ['webService'],
  data(){
    return{
      loginNicknameInput: "",
      loginPasswordInput: "",
      requestResponseMsg: false
    }
  },
  methods: {
    closeModal(){
      this.$emit('close-modal-event');
    },
    async login(){
      //TODO MIKA
    }
  }
}
</script>

<style scoped>
.modal {
  display: block;
}
</style>
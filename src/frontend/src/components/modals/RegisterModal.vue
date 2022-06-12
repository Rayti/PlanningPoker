<template>
  <div class="modal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Registration panel</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" @click="closeModal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <p class="text-start">Nickname:</p>
          <div class="form-floating mb-3">
            <input  class="form-control" id="registerNickname" v-model="registerNicknameInput" aria-label="Floating label select example" placeholder="Enter your nick">
            <label for="registerNickname">Your nickname</label>
          </div>
          <p class="text-start">Password:</p>
          <div class="form-floating mb-3">
            <input  class="form-control" id="password" v-model="passwordInput" aria-label="Floating label select example" placeholder="Enter your password">
            <label for="password">Your password</label>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" @click="registerNewAccount">Create new account</button>
        </div>
      </div>
    </div>
  </div>

</template>

<script>

export default {
  name: "RegisterModal",
  inject: ['webService'],
  data(){
    return {
      registerNicknameInput: "",
      passwordInput: ""
    }
  },
  methods: {
    closeModal() {
      this.$emit('close-modal-event');
    },
    async registerNewAccount(){
    //  TODO

    }
  }
}
</script>

<style scoped>
  .modal {
    display: block;
  }
</style>
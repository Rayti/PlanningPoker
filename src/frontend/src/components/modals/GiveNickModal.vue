<template>
  <div class="modal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Firstly, please enter your nick</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" @click="closeModal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <div class="form-floating mb-3">
            <input  class="form-control" id="nickInput" v-model="nickInput" placeholder="Enter your nick">
            <label for="nickInput">Your nick</label>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" @click="goToRoom" data-bs-dismiss="modal">Go</button>
        </div>

      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "GiveNickModal",
  data() {
    return {
      nickInput:""
    }
  },
  methods: {
    closeModal() {
      this.$emit('close-modal-event');
    },
    goToRoom(){
      const nick = this.nickInput;
    }
  }
}
</script>

<style scoped>
.modal {
  display: block;
}
</style>
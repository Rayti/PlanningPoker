<template>
  <div class="modal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Create new planning poker room</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" @click="closeModal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <p class="text-start">Firstly, please enter your nick:</p>
          <div class="form-floating mb-3">
            <input  class="form-control" id="floatingNick" v-model="nickInput" aria-label="Floating label select example" placeholder="Enter your nick">
            <label for="floatingNick">Your nick</label>
          </div>
          <p class="text-start">and then name your room:</p>
          <div class="form-floating mb-3">
            <input  class="form-control" id="floatingInput" v-model="roomInput" placeholder="Enter room name">
            <label for="floatingInput">Game room name</label>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" @click="createNewRoom" data-bs-dismiss="modal">Go</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "CreateRoomModal",
  data() {
    return {
      roomInput: "",
      nickInput:""
    }
  },
  methods: {
    closeModal() {
      this.$emit('close-modal-event');
    },

    createNewRoom(){
      const room = this.roomInput;
      const nick = this.nickInput;
      this.$router.push('game');
    }
  }
}
</script>

<style scoped>
.modal {
  display: block;
}


</style>
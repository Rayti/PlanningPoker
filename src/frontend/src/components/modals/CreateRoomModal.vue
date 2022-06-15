<template>
  <div class="modal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Create new planning poker room</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" @click="closeModal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <p class="text-start">Hello, {{ userName }}!</p>
          <p class="text-start">Please enter the name of your room:</p>
          <div class="form-floating mb-3">
            <input  class="form-control" id="floatingInput" v-model="roomInput" placeholder="Enter room name">
            <label for="floatingInput">Game room name</label>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" @click="createNewRoom" data-bs-dismiss="modal">Go</button>
        </div>
<!--RSPONSE MSG IF ROOM CAN NOT BE CREATED-->
        <div v-if="createRoomError">
          Room cannot be created (server error/room already exists).
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex';

export default {
  name: "CreateRoomModal",
  inject: ['webHttpService','webSocketService'],
  data() {
    return {
      roomInput: "",
      createRoomError: false,
    }
  },
  computed: mapState([
      "userName",
  ]),
  methods: {
    closeModal() {
      this.$emit('close-modal-event');
    },
    async createNewRoom() {
      const roomName = this.roomInput;
      const result = await this.webHttpService.createRoomRequest(roomName, this.userName);

      if (result && result.success) { // jeżeli nie to powinien tost jakiś się pojawić
        if (this.createRoomError) {
          this.createRoomError = false
        }

        this.webSocketService.createWebSocketConnection(roomName);
        this.$store.dispatch("setBasicInformation", { roomName: roomName, isHost: true });
        this.$router.push({ name: "game", params: { roomName: roomName }});
      } else { // obsługa błędów
        if (!this.createRoomError) {
          this.createRoomError = true
        }
      }
    }
  }
}
</script>

<style scoped>
.modal {
  display: block;
}
</style>
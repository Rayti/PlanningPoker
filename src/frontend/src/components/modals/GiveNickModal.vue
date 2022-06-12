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
          <button type="button" class="btn btn-primary" @click="joinRoom" data-bs-dismiss="modal">Go</button>
        </div>
        <div v-if="joinRoomError">
          You cannot join the room (server error/room doesn't exist).
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "GiveNickModal",
  inject: ['webHttpService','webSocketService'],
  props: {
    roomName: String,
  },
  data() {
    return {
      nickInput: "",
      joinRoomError: false,
    }
  },
  methods: {
    closeModal() {
      this.$emit('close-modal-event');
    },
    async joinRoom() {
      if (this.$store.state.isHost) {
        return;
      }
      const roomName = this.roomName;
      const userName = this.nickInput;
      const result = await this.webHttpService.joinExistingRoomRequest(roomName, userName);
      if (result && result.success) {
        if (this.joinRoomError) {
          this.joinRoomError = false
        }
        const gameData = this.webHttpService.getIntoCurrentGame(roomName,userName);

        this.webSocketService.createWebSocketConnection(roomName);
        this.$store.dispatch("setBasicInformation", { roomName: roomName, userName: userName, isHost: false });
        this.$router.push({ name: "game", params: { roomName: roomName }});
      } else { // obsługa błędów
        if (!this.joinRoomError) {
          this.joinRoomError = true
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
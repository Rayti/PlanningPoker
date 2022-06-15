<template>
  <div>
    <div :class="{'blur-content': displayModal}">
      <div class="container">
        <div class="row"/>
        <div class="row">
          <div class="col-12">
            <div class="container">
              <img class="row text-margin" src="../assets/planningpoker.png">
              <div v-if="this.$store.state.sessionId !== ''">
                <h3 class="row text-left text-margin mt-5 mb-4">To start planning</h3>
                <div class="row justify-content-center">
                  <button col-4 type="button" class="btn btn-outline-primary btn-lg w-25 mb-4" @click="showModalRoom">
                    CREATE A NEW ROOM
                  </button>
                  <!--      <div class=" btn-toolbar  input-toolbar" role="toolbar" aria-label="Toolbar with button groups">-->
                  <!--        <div class="input-group">-->
                  <!--&lt;!&ndash;          <div class="input-group-text" id="btnGroupAddon">Name</div>&ndash;&gt;-->
                  <!--          <input type="text" v-model="nickInput" class="form-control" placeholder="Enter your nick"-->
                  <!--                 aria-label="Input group example" aria-describedby="btnGroupAddon">-->
                  <!--          <button type="button" v-on:click="goClick" class="btn btn-outline-success">Let's start</button>-->
                  <!--        </div>-->
                  <!--      </div>-->
                </div>
                <br>
                <h3 class="row text-center text-margin mb-4 ">...or join existing one</h3>
                <div class="row justify-content-center">
                  <div class=" btn-toolbar btn-lg mb-3 input-toolbar w-25" role="toolbar" aria-label="Toolbar with button groups">
                    <div class="input-group invite">
                      <input type="text" v-model="joinRoomInput" class="form-control " placeholder="Paste room name"
                            aria-label="Input group example" aria-describedby="btnGroupAddon">
                      <button type="button" @click="joinRoom" class="btn btn-outline-primary">Join</button>
                    </div>
                  </div>
                </div>
              </div>
              <div v-if="joinRoomError" style="color:red;">
                You cannot join the room (server error/room doesn't exist).
              </div>
            </div>
          </div>
          <div class="col-12" v-if="this.$store.state.sessionId === ''">
            <div class="btn-group-vertical btn-group-lg mt-5">
              <button type="button" class="btn btn-outline-primary btn-lg px-5 py-3" @click="showRegisterModal"> Register</button>
              <button type="button" class="btn btn-outline-primary btn-lg px-5 py-3" @click="showLoginModal"> Log in</button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <create-room-modal v-if="createRoomModal" @close-modal-event="hideModal"></create-room-modal>
    <register-modal v-if="registerModal" @close-modal-event="hideModal"></register-modal>
    <login-modal v-if="loginModal" @close-modal-event="hideModal"></login-modal>
  </div>
</template>

<script>
import CreateRoomModal from "@/components/modals/CreateRoomModal";
import RegisterModal from "@/components/modals/RegisterModal";
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min';
import LoginModal from "@/components/modals/LoginModal";
import { mapState } from 'vuex';


export default {
  name: "HomeView",
  components: {
    LoginModal,
    CreateRoomModal,
    RegisterModal
  },
  inject: ['webHttpService', 'webSocketService'],
  data() {
    return {
      joinRoomInput: "",
      displayModal: false,
      createRoomModal: false,
      registerModal: false,
      loginModal: false,
      showError: false,
      joinRoomError: false,
    }
  },
  computed: mapState([
      "userName"
  ]),
  methods: {
    showModalRoom() {
      this.displayModal = true;
      this.createRoomModal = true;
    },
    hideModal() {
      this.displayModal = false;
      this.joinRoomModal = false;
      this.createRoomModal = false;
      this.registerModal = false;
      this.loginModal = false;
    },
    connectingDelay(delayInMs) {
      return new Promise(resolve => {
        setTimeout(() => {
          resolve(2);
        }, delayInMs);
      });
    },
    async joinRoom(){
        const roomName = this.joinRoomInput;
        console.log(roomName);
          console.log(this.userName);
        const result = await this.webHttpService.canJoinRoomRequest(roomName, this.userName);
        
        if (result && result.success) {
          if (this.joinRoomError) {
            this.joinRoomError = false
          }
          
          this.webSocketService.createWebSocketConnection(roomName);
          await this.connectingDelay(3000);
          console.log("coś");
         
          this.webSocketService.joinExistingRoom(roomName, this.userName);
          await this.connectingDelay(3000);
          const result2 = await this.webHttpService.getIntoCurrentGame(roomName, this.userName);
          console.log(result2);
          this.$store.dispatch("setBasicInformation", { roomName: roomName, isHost: false });
          this.$router.push({ name: "game", params: { roomName: roomName }});
        } else { // obsługa błędów
          if (!this.joinRoomError) {
            this.joinRoomError = true
          }
      }
    },
    showRegisterModal() {
      this.displayModal = true;
      this.registerModal = true;
    },
    showLoginModal(){
      this.displayModal = true;
      this.loginModal = true;
    }
  }
};
</script>

<style scoped>
.input-toolbar{
  justify-content: center !important;
  z-index: 500;
  /*margin-left: 20.0rem*/
}

.text-margin{
  margin-left: 23.0rem !important;
}
.invite{
  min-width: 22rem;
}
.blur-content{
  filter: blur(5px);
}
</style>
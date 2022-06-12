<template>


  <div :class="{'blur-content': displayModal && (createRoomModal || joinRoomModal)}">
    <div class="container">
      <div class="row">
        <div class="col-10">
          <img class="row text-margin" src="../assets/planningpoker.png">
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
        <div class="col-2">
          <div class="btn-group-vertical">
            <button type="button" class="btn btn-outline-primary btn-lg"> Register</button>
            <button type="button" class="btn btn-outline-primary btn-lg"> Log in</button>
          </div>
        </div>
      </div>
    </div>
  </div>
  <create-room-modal v-if="createRoomModal" @close-modal-event="hideModal"></create-room-modal>
  <give-nick-modal v-if="joinRoomModal" :room-name="joinRoomInput" @close-modal-event="hideModal"></give-nick-modal>
</template>

<script>
import {WebService} from "@/services/WebService";
import GiveNickModal from "@/components/modals/GiveNickModal";
import CreateRoomModal from "@/components/modals/CreateRoomModal";
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min';

const webService = new WebService();

export default {
  name: "HomeView",
  components: {
    GiveNickModal,
    CreateRoomModal,
  },
  data() {
    return {
      joinRoomInput: "",
      displayModal: false,
      createRoomModal: false,
      joinRoomModal: false,
    }
  },
  methods: {
    goClick() {

      webService.addUser(this.nickInput).then((data) => {
        this.joinRoomModal=true;

        this.$router.push('game')
      });
    },
    showModalRoom() {
      this.displayModal = true;
      this.createRoomModal = true;
    },
    hideModal() {
      this.displayModal = false;
      this.joinRoomModal = false;
      this.createRoomModal = false;

    },
    joinRoom(){
      this.displayModal = true;
      this.joinRoomModal = true;
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
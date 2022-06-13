<template>
  <div :class="{'blur-content': displayUserStoryModal||displayTaskModal}">

  <div class="game-table">
    <div class="inside-table">
      <div class="many-users row">

        <div class="grid-table">
          <div class="top">
          <div class="user-story">
          <div class="card mb-2 ">
            <div class="card-header" v-bind="this.$store.state.currentStory" v-if="this.$store.state.currentStory!=undefined">
              {{this.$store.state.currentStory.description}}
            </div>
            <div class="card-body tasks">
              <ul class="list-group" v-if="this.$store.state.currentStory!=undefined">
                <li v-for="(task, index) in this.$store.state.currentStory.tasks" :key="`task${index}`" class="list-group-item">
                  {{ task.description }}
                </li>
              </ul>
            </div>
          </div>
          </div>
          <div></div>

            <div class="manage">
              <button class="btn btn-outline-primary" @click="openUserStoryPanel">Manage user stories</button>
              <button class="btn btn-outline-primary" :disabled="this.$store.state.currentStory==undefined" @click="openTaskPanel">Manage tasks</button>
            </div>
          </div>
          <div></div>
         <div class="left">
               <div class="row justify-content-start" >
             <button type="button" class="btn btn-outline-secondary invite-btn col-8 align-start"
                     data-bs-toggle="popover" title="Copy invite link " data-bs-placement="bottom"
                     data-content-id="popover-27">
               Invite to room
             </button>
               </div>
             <div style="display: none;" id="popover-27">
               <div class="input-group mb-3">
                 <span class="input-group-text" id="basic-addon1">Link</span>
                 <input type="text" class="form-control"  aria-describedby="basic-addon1">
               </div>
             </div>
          </div>

          <div class="players-space">
            <div class="players-content">
              <div>
                <div class="names">{{ this.$store.state.userName }}</div>
                <planning-poker-card :v-if="isSelectionConfirmed" :estimation="mainPlayerCard"/>
              </div>
              <div :v-if="showResults" v-for="(player, index) in otherPlayersCards" :key="`player-${index + 2}`">
                <div class="names">{{ player.userName }}</div>
                <planning-poker-card :estimation="formatValue(player.value)"/>
              </div>
            </div>
          </div>
          <div class="right">
            <button class="btn btn-outline-primary btn-lg" 
                    :disabled="checkResults()" @click="getResults">
              Get results
            </button>
            <!-- <button class="btn btn-outline-primary btn-lg" 
                    :disabled="checkResults()" @click="getResults">
              Clear results
            </button> -->
          </div>
          <div></div>
          <!-- <div class="bot"> -->
          <!-- <div class="bot-player"><div class="bot-player2"><div class="card-bot"> -->
          <!-- </div></div></div> -->
          <!-- </div> -->
          <div></div>
        </div>
      </div>
    </div>
  </div>
  </div>
      <UserStoryModal v-if="displayUserStoryModal" :storyID ="this.story.id" @closeUserStoryModal="hideModal" @clearStoryTable = "clearStoryTable" @chooseStory="chooseStory"></UserStoryModal>
    <TasksModal v-if="displayTaskModal" :storyID ="this.story.id" @closeTaskModal="hideTaskModal"></TasksModal>
<!--  <EditStoryModal v-if="displayEditStoryModal" @close-modal-event="hideModal"></EditStoryModal>-->
</template>

<script>
import { mapState } from 'vuex';
import PlanningPokerCard from './PlanningPokerCard.vue';
import UserStoryModal from "@/components/modals/UserStoryModal";
import $ from "jquery";
import { Popover } from 'bootstrap/dist/js/bootstrap.esm.min.js'
import TasksModal from "@/components/modals/TasksModal";

export default {
  name: 'GameTable',
  inject: ['webHttpService', 'webSocketService'],
  components: {
    TasksModal,
    UserStoryModal,
    PlanningPokerCard
  },
  props: {
    mainPlayerCard: String,
    isSelectionConfirmed: Boolean
  },
  data() {
    return {
      showResults: false,
      clearResults: false,
      displayUserStoryModal: false,
      displayTaskModal:false,
      story:{},
      players: [],
      tasks: (this.story)? this.$store.getters.getStoryTasks(this.story.id) : [],

    }
  },
mounted() {
  let popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));
  popoverTriggerList.forEach(popoverTriggerEl=> {
    const popoverId = popoverTriggerEl.attributes['data-content-id'];
    const contentEl = $(`#${popoverId.value}`).html();
    let opts = {
      content: contentEl,
      html: true,
      container: 'body',
      sanitize  : false,
      // trigger: 'focus'
    }
    new Popover(popoverTriggerEl, opts);

  })
},
  computed: mapState([
      "otherPlayersCards",
      "currentStory"
  ]),
  methods: {
    checkResults() {
      return !this.$store.state.isHost || !this.isSelectionConfirmed || this.mainPlayerCard === "";
    },
    getResults() {
      this.webSocketService.finishGame(this.$store.state.roomName, this.$store.state.userName);
      this.showResults = true;
      // if (this.isSelectionConfirmed && this.mainPlayerCard !== "") {
      //   let numberOfParticipants = Math.floor(Math.random() * (8) + 1);
      //   const participants = [];
      //   while (numberOfParticipants >= 0) {
      //     console.log("here");
      //     participants.push(Math.floor(Math.random() * (12)));
      //     numberOfParticipants--;
      //   }
      //   this.players = participants;
      //   console.log(numberOfParticipants);
      //   console.log(participants);
      //   this.showResults = true;
      // }
    },
    formatValue(value) {
      return value.split(".")[0];
    },
    clearResult() {
      if (this.$store.state.otherPlayersCards.length !== 0) {
        this.$store.dispatch("removeResults");
      } 
      this.showResults = false;
    },
    openUserStoryPanel() {
      this.displayUserStoryModal = true;
    },
    openTaskPanel() {
      this.displayTaskModal = true;
    },
    hideModal() {
      this.displayUserStoryModal = false;
    },
    hideTaskModal() {
      this.displayTaskModal = false;
    },
    chooseStory($event){
      this.webSocketService.chooseUserStory(this.$store.state.roomName, this.$store.state.userName, $event.id);
    },

    addStoryHandler(){
      this.$emit('select')
    },
    clearStoryTable(){
      this.story={};
      this.tasks=[];
    },

    onClick() {
      //this.webService.selectCard("siema", "eniu");
    }
  }
}
</script>


<style>
.game-table {
  display: flex;
  align-items: center;
  justify-content: center;
  max-width: 100vw;
}

.inside-table {
  display: flex;
  align-items: center;
  justify-content: center;
  margin: auto;
  width: 100%;
  padding-bottom: 0;
}

.grid-table {
  grid-gap: .8rem;
  grid-template-areas: "left top right" "left table right" "left bottom right";
  grid-template-columns: 10.4rem 1fr 10.4rem;
  grid-template-rows: auto 1fr auto;
  display: inline-grid;
  margin: 0 auto;
  width: auto;
  min-height: 200px;
}

/* .user-lonely {
    grid-template-rows: 8rem 1fr 13.4rem;
} */

.top {
  z-index: 2;
  grid-area: top;
  display: flex;
  flex-direction: column;
  align-items: stretch;
  justify-content: center;
  padding-bottom: .2rem;
  /* align-items: flex-end; */
  /* align-items: center; */
}

.user-story {
  position: relative;
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  justify-content: center;
  margin: 0 auto;
  width: 100%;
  height: auto;
  min-height: 3.5rem;
  max-width: 50.8rem;
  min-width: 30.8rem;
  /*background: rgb(214, 10, 81);*/
  /* padding: 0 1.6rem; */
}

.user-story h5 {
  color: white;
}

.tasks {
  overflow: hidden;
  overflow-y: scroll;
  width: 100%;
  height: 4.7rem;
  min-width: 30.8rem;
  padding: 0% !important;
  /*max-width: 50.8rem;*/
  /*background: rgb(187, 178, 181);*/
}

.tasks ul {
  list-style-type: none;
}

.left {
  grid-area: left;
  z-index: 3;
  display: flex;
  flex-direction: column-reverse;
  align-items: center;
  justify-content: center;
  margin-top: 8rem;
  padding: 8rem 0;
}

.players-space {
  grid-area: table;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: .6rem auto;
  border-radius: 6.0rem;
  width: 100%;
  height: auto;
  min-width: 50.8rem;
  min-height: 12.1rem;
  padding: 0 1.6rem;
  background-image: url("../assets/wood-texture.png");
}

.players-content {
  position: absolute;
  top: 2.4rem;
  bottom: 2.4rem;
  left: 0.4rem;
  right: 0.4rem;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 16px;
}

.right {
  grid-area: right;
  z-index: 3;
  display: flex;
  /* flex-direction: column-reverse; */
  align-items: center;
  justify-content: center;
  margin-top: 8rem;
  padding: 8rem 0;
}

/* .bot {
    align-items: stretch;
    grid-area: bottom;
    z-index: 2;
    padding: 1.2rem 0 .4rem;
    display: flex;
    justify-content: center;
}
.bot-player {
    max-width: 10.4rem;
    width: 10.4rem;
}
.bot-player-2 {
    align-items: center;
    display: flex;
    flex-direction: column;
    height: 100%;
    justify-content: space-evenly;
    max-width: 100%;
    perspective: 10rem;
    perspective-origin: bottom;
}

.card-bot {
    border-radius: .8rem;
    height: 7rem;
    width: 4rem;
    flex-shrink: 0;
} */

.names {
  color: white;
  text-shadow: -1px 1px 0 #000,
  1px 1px 0 #000,
  1px -1px 0 #000,
  -1px -1px 0 #000;
}

.manage {
  flex-direction: row;
  row-gap: 2.2rem;
}

.manage button {
  margin-left: .5rem;
  margin-right: .5rem;
  width: 12.2rem;
}
.invite-btn{
  width: 10rem;
  margin-left: -8%;

}
.blur-content{
  /*filter: blur(5px);*/
  opacity:0.5 !important;
  /*!*background-color: black;*!*/
  /*background: rgba(0, 0, 0, 0.5);*/
  /*position: fixed;*/

}
</style>
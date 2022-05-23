<template>
    <div class="game-table">
        <div class="inside-table">
            <div class="many-users row">

                <div class="row justify-content-center">
<!--                <div class="top">-->

                  <button class=" col-4 btn btn-outline-primary btn-lg " :disabled="!isSelectionConfirmed" @click="getResults">Get results</button>
<!--                </div>-->
<!--              <div class="top">-->
                <button class=" col-4 btn btn-outline-primary btn-lg ms-2"  @click="openUserStoryPanel">User story</button>
              </div>
<!--             /   </div>-->
              <div></div>
            <div class="grid-table">
                <div></div>
                <div class="top">
                    <!-- <div class="user-story-part"> -->
                    <div class="user-story">
                        <h5>I can Create/Update/Retrieve/Delete a user story so that we can estimate the items we need to do</h5>
                    </div>
                    <nav class="tasks">
                        <ul>
                            <li v-for="(task, index) in exampleTasks" :key="`task${index}`">
                                {{ task }}
                            </li>
                        </ul>
                    </nav>
                    <!-- </div> -->
                    <div class="manage">
                        <button class="btn btn-outline-primary">Manage user stories</button>
                        <button class="btn btn-outline-primary">Manage tasks</button>
                    </div>
                </div>
                <div></div>
                <!-- <div class="left">
                    <button class="btn btn-outline-primary btn-lg">Get invitation link</button>  
                </div> -->
                <div class="players-space">
                    <div class="players-content">
                        <div>
                            <div class="names">me</div>
                            <planning-poker-card :v-if="isSelectionConfirmed" :estimation="mainPlayerCard"/>
                        </div>
                        <div :v-if="showResults" v-for="(player, index) in players" :key="`player-${index + 2}`">
                            <div class="names">player{{ index + 2 }}</div>
                            <planning-poker-card :estimation="fibonacci[player]"/>
                        </div>
                    </div>
                </div>
                <div class="right">
                    <button class="btn btn-outline-primary btn-lg" :disabled="!isSelectionConfirmed" @click="getResults">Get results</button>    
                </div>
                <div></div>
                <!-- <div class="bot"> -->
                    <!-- <div class="bot-player"><div class="bot-player2"><div class="card-bot"> -->
                    <!-- </div></div></div> -->
                <!-- </div> -->
                <div></div>
            </div>
        </div>
      <UserStoryModal v-if="displayUserStoryModal"  @closeUserStoryModal="hideModal"></UserStoryModal>
    </div>
</template>

<script>
import PlanningPokerCard from './PlanningPokerCard.vue';
import UserStoryModal from "@/components/modals/UserStoryModal";

export default {
    name: 'GameTable',
    components: {
      UserStoryModal,
        PlanningPokerCard
    },
    props: {
        mainPlayerCard: String,
        isSelectionConfirmed: Boolean
    },
    data() {
        return {
            fibonacci: ['0', '1', '2', '3', '5', '8', '13', '21', '34', '55', '89', '?'],
            showResults: false,
            clearResults: false,
            players: []
            displayUserStoryModal: false,
            players: [],
            exampleTasks: ["PP-23: Create planning poker Create planning poke Create planning pokeCreate planning poke Create planning poke Create planning poke Create planning poke Create planning poke", "PP-23: Create planning poker", "PP-23: Create planning poker", "PP-23: Create planning poker", "PP-23: Create planning poker", "PP-23: Create planning poker"]
        }
    },
    methods: {
      getResults() {
        if (this.isSelectionConfirmed && this.mainPlayerCard !== "") {
          let numberOfParticipants = Math.floor(Math.random() * (8) + 1);
          const participants = [];
          while (numberOfParticipants >= 0) {
            console.log("here");
            participants.push(Math.floor(Math.random() * (12)));
            numberOfParticipants--;
          }
          this.players = participants;
          console.log(numberOfParticipants);
          console.log(participants);
          this.showResults = true;
        }
      },

      clearResult() {
        this.players=[];
      },
      openUserStoryPanel(){
          this.displayUserStoryModal = true;
      },
      hideModal() {
        this.displayUserStoryModal = false;
      },
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
    min-height: 3.1rem;
    max-width: 50.8rem;
    background: rgb(214, 10, 81);
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
    /* min-width: 30.8rem; */
    max-width: 50.8rem;
    background: rgb(187, 178, 181);
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
</style>